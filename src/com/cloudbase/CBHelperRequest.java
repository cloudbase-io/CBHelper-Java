/*
Copyright (c) 2013 Cloudbase.io Limited

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except 
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License 
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express 
or implied. See the License for the specific language governing permissions and limitations under 
the License.
*/
package com.cloudbase;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A Runnable object used by the CBHelper class to execute HTTP calls asynchronously. This should not be called
 * directly.
 * @author Stefano Buliani
 *
 */
public class CBHelperRequest implements Runnable {

	//private String url;
	//private String function;
	//private String fileId;
	//private Hashtable<String, String> postData;
	//private ArrayList<File> files;
	private CBQueuedRequest request;
	private String temporaryFilePath;
	private String queueFileName;

	private CBHelperResponder responder;
	private CBHelperResponse resp;
	private CBHelper helperObject;

	public CBHelperRequest(CBQueuedRequest req, CBHelper helper) {
		this.request = req;
		this.helperObject = helper;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		//HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		HttpPost httppost = new HttpPost(this.request.getUrl());

		try {
			// Add your data
			Enumeration<String> params = this.request.getParameters().keys();

			// prepare the request adding all of the parameters.
			CBMultipartEntity entity = new CBMultipartEntity();

			while (params.hasMoreElements())
			{
				String curKey = params.nextElement();
				entity.addPart(new CBStringPart(curKey, this.request.getParameters().get(curKey), HTTP.UTF_8));
			}

			// if we have file attachments then add each file to the multipart request
			if (this.request.getFiles() != null && this.request.getFiles().size() > 0) {
				int fileCounter = 0;
				for (File curFile : this.request.getFiles()) {
					String name = curFile.getName();
					int pos = name.lastIndexOf('.');
					String ext = name.substring(pos+1);
					entity.addPart(new CBFilePart("file" + fileCounter, curFile, null, 
							(pos > -1 ? MimeTypes.getMimeType(ext) : null)));
					fileCounter++;
				}
			}
			// add the multipart request to the http connection
			httppost.setEntity(entity);

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			// if we have a responder then parse the response data into the global CBHelperResponse object
			if (this.responder != null) {
				resp = new CBHelperResponse();
				resp.setFunction(this.request.getCloudbaseFunction());
				resp.setHttpStatus(response.getStatusLine().getStatusCode());
				// if it's a download then we need to save the file content into a temporary file in
				// application cache folder. Then return that file to the responder
				if (this.request.getCloudbaseFunction().equals("download")) {
					InputStream input = response.getEntity().getContent();

					File outputFile = File.createTempFile(this.request.getFileId(), null, new File(this.getTemporaryFilePath()));
					OutputStream fos = new BufferedOutputStream(new FileOutputStream(outputFile));
					try {

						byte[] buffer = new byte[(int) 4096];
						int readBytes;
						while (((readBytes = input.read(buffer, 0, buffer.length)) != -1)) {
							fos.write(buffer, 0, readBytes);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
						}
					}

					if (input != null) {
						try {
							input.close();
						} catch (IOException e) {
						}
					}
					resp.setDownloadedFile(outputFile);
				} else {

					// if it's not a download parse the JSON response and set all 
					// the variables in the CBHelperResponse object
					String responseString = EntityUtils.toString(response.getEntity());

					resp.setResponseDataString(responseString);
					//if (this.helperObject.isDebugMode())
						//Log.d("test", resp.getResponseDataString());
					
					// Use the cloudbase.io deserializer to get the data in a Map<String, Object>
					// format.
					GsonBuilder gsonBuilder = new GsonBuilder();
					gsonBuilder.registerTypeAdapter(Object.class, new CBNaturalDeserializer());
					Gson gson = gsonBuilder.create();
					Map<String, Object> responseData = gson.fromJson(responseString,Map.class);
					if (responseData == null) {
						resp.setErrorMessage("Empty response data");
						resp.setSuccess(false);
					} else {
						Map<String, Object> outputData = (Map<String, Object>)responseData.get(this.request.getCloudbaseFunction());
						resp.setData(outputData.get("message"));
						resp.setErrorMessage((String)outputData.get("error"));
						resp.setSuccess(((String)outputData.get("status")).equals("OK"));
					}
				}

				// now that the response object is ready use the Handler we have been handed from the
				// CBHelper class on the main thread to call the responder object. This way the data
				// is available to the UI thread
				responder.handleResponse(request, resp);
			}
			
			if ((resp == null || resp.getHttpStatus() == 200) && this.queueFileName != null) {
				this.helperObject.removeQueuedRequest(this.queueFileName);
			}

		} catch (Exception e) {
			//Log.e("REQUEST", "Error " + e.getMessage(), e);
			if (this.responder != null) {
				responder.handleResponse(request, resp);
			}
		}
	}

	public CBHelperResponder getResponder() {
		return responder;
	}
	public void setResponder(CBHelperResponder responder) {
		this.responder = responder;
	}

	public String getTemporaryFilePath() {
		return temporaryFilePath;
	}

	public void setTemporaryFilePath(String temporaryFilePath) {
		this.temporaryFilePath = temporaryFilePath;
	}

	public String getQueueFileName() {
		return queueFileName;
	}

	public void setQueueFileName(String queueFileName) {
		this.queueFileName = queueFileName;
	}
}

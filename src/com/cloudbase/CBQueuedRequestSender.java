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

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * This object is used internally to send the request queued on disk
 * asynchronously. 
 *
 */
public class CBQueuedRequestSender implements Runnable {

	private ArrayList<String> requests;
	private CBHelper helper;
	
	/**
	 * Creates a new instance of the request sender.
	 * @param req A list of String containing the path to a queued request file
	 * @param helperObject An initialized CBHelper object to use for settings and callbacks
	 */
	public CBQueuedRequestSender(ArrayList<String> req, CBHelper helperObject) {
		this.requests = req;
		this.helper = helperObject;
	}
	
	/**
	 * Starts sending the requests. This method sends request synchronously therefore it should
	 * always be called from inside a thread to avoid blocking the application
	 */
	@Override
	public void run() {
		for (String curRequest : this.requests) {
			try {
				FileInputStream fis = new FileInputStream(curRequest);
				ObjectInputStream is = new ObjectInputStream(fis);
				CBQueuedRequest requestObject = (CBQueuedRequest) is.readObject();
				is.close();
				
				CBHelperRequest req = new CBHelperRequest(requestObject, this.helper);
				if (this.helper.getDefaultQueueResponder() != null) {
					req.setResponder(this.helper.getDefaultQueueResponder());
				}
				
				req.setQueueFileName(curRequest);
				
				req.setTemporaryFilePath(this.helper.getTemporaryFilesPath());
				
				req.run();
				
				if (this.helper.isDebugMode()) {
					//Log.i(CBHelper.logTag, "Sent queued request: " + curRequest);
				}
			
			} catch (Exception e) {
				//Log.e(CBHelper.logTag, "Error while opening queued request " + curRequest, e);
			}
		}
		
		this.helper.removeQueueLock();
	}

}

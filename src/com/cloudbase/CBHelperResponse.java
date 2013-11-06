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

import java.io.File;

/**
 * The object representing a parsed response from the cloudbase.io APIs. This object will contain all of the data
 * returned and will be passed to the <strong>CBHelperResponder</strong> object once a call is completed.
 * @author Stefano Buliani
 *
 */
public class CBHelperResponse {
	private String errorMessage;
	private String function;
	private String responseDataString;
	private File downloadedFile;
	private Object data;
	private boolean success;
	private int httpStatus;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public File getDownloadedFile() {
		return downloadedFile;
	}
	public void setDownloadedFile(File downloadedFile) {
		this.downloadedFile = downloadedFile;
	}
	
	/**
	 * Whether the API call was successful. If an error occurred then the full error message is stored in the <strong>errorMessage</strong>
	 * field
	 * @return Whether the call was successful
	 */
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * The String representation of the response. All of the raw data (unparsed) returned from cloudbase.io is stored in this
	 * variable
	 * @return The string response
	 */
	public String getResponseDataString() {
		return responseDataString;
	}
	public void setResponseDataString(String responseDataString) {
		this.responseDataString = responseDataString;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
}

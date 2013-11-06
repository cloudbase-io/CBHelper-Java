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

import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * This object represents a cloudbase.io API request. It is used by the helper class
 * to serialize the data of the call to a file for request queueing.
 *
 */
public class CBQueuedRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The API url
	 */
	private String url;
	/**
	 * The cloudbase function to be called log, data etc
	 */
	private String cloudbaseFunction;
	/**
	 * The id of the file to be downloaded if the request was for a file 
	 * download
	 */
	private String fileId;
	/**
	 * All of the parameters for the HTTP post
	 */
	private Hashtable<String, String> parameters;
	/**
	 * The file attachments
	 */
	private ArrayList<File> files;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCloudbaseFunction() {
		return cloudbaseFunction;
	}
	public void setCloudbaseFunction(String cloudbaseFunction) {
		this.cloudbaseFunction = cloudbaseFunction;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Hashtable<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Hashtable<String, String> parameters) {
		this.parameters = parameters;
	}
	public ArrayList<File> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

}

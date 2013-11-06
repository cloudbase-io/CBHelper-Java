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

import java.util.TimerTask;

import com.cloudbase.datacommands.CBSearchCondition;

/**
 * A task fetching the data from cloudbase.io called by the CBGeoDataStream
 * object using a Timer
 */
public class CBGeoDataStreamTask extends TimerTask {

	private CBGeoDataStream streamObject;
	
	/**
	 * Creates a new instance of the TimerTask receiving the
	 * CBGeoDataStream object running the timer
	 * 
	 * @param father The CBGeoDataStream object running the task 
	 */
	public CBGeoDataStreamTask(CBGeoDataStream father) {
		this.setStreamObject(father);
	}
	
	@Override
	public void run() {
		//Looper.prepare();
		
				CBLocation currentLocation = streamObject.getResponder().getLatestPosition(streamObject.streamName);
				
				if (streamObject.previousPosition != null) {
					double distance = currentLocation.distanceTo(streamObject.previousPosition);
					
					if (distance < streamObject.queryRadius / CBGeoDataStream.refreshRadiusRatio) {
						if (isDebugMode()) {
							//Log.d(CBHelper.logTag, "Not enough distance between the two points. returning without fetching data");
						}
						return;
					}
					
					double speed = distance / CBGeoDataStream.queryInterval;
					double ratio = 1.0;
					
					if (isDebugMode()) {
						//Log.d(CBHelper.logTag, "Computed speed " + speed + " meters per second");
					}
					
					if (streamObject.previousSpeed != 0.0) {
						ratio = speed / streamObject.previousSpeed;
					}
					
					if (streamObject.queryRadius * ratio < streamObject.getSearchRadius()) {
						streamObject.queryRadius = streamObject.getSearchRadius();
					} else {
						streamObject.queryRadius = streamObject.queryRadius * ratio;
					}
					
					streamObject.previousSpeed = speed;
				}
				streamObject.previousPosition = currentLocation;
				
				CBSearchCondition condition = new CBSearchCondition(currentLocation, streamObject.queryRadius);
				
				streamObject.helper.searchDocument(streamObject.getCollection(), condition, streamObject);
				
			
	}
	
	private boolean isDebugMode() {
		return this.streamObject.helper.isDebugMode();
	}

	public CBGeoDataStream getStreamObject() {
		return streamObject;
	}

	public void setStreamObject(CBGeoDataStream streamObject) {
		this.streamObject = streamObject;
	}
}

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Opens a persistent connection to a particular geo-coded connection on a
 * cloudbase.io Cloud Database and retrieves geo located data for the application.
 *
 * Data is handed back to the application using the protocol.
 *
 * This is meant to be used for augment reality applications.
 */
public class CBGeoDataStream implements CBHelperResponder {
	
	protected static final long queryInterval = 		2000; // 2 seconds
	protected static final long refreshRadiusRatio = 	4;
	
	protected CBHelper helper;
	protected CBLocation previousPosition;
	protected double previousSpeed;
	protected double queryRadius;
	protected Map<String, CBGeoLocatedObject> foundObjects;
	protected String streamName;
	private Timer queryTimer;
	
	/**
	 * The object implementing the CBGeoDataStreamDelegate interface
	 */
	private CBGeoDataStreamResponder responder;
	/**
	 * The radius for the next search in meters from the point returned by the 
	 * getLatestPosition method
	 */
	private double searchRadius;
	/**
	 * The collection on which to run the search
	 */
	private String collection;
	
	/**
	 * Initializes a new CBGeoDataStream object and uses the given CBHelper
	 * object to retrieve data from the cloudbase.io APIS.
	 *
	 * @param name A unique identifier for this stream object. It's always passed to the responder
	 * @param helper An initialized CBHelper object
	 * @param collection The name of the collection to search
	 */
	public CBGeoDataStream(String name, CBHelper helper, String collection) {
		this.helper = helper;
		this.streamName = name;
		//helper.
		this.setCollection(collection);
		this.previousSpeed = 0.0;
		this.queryRadius = 50; // by default we use 50 meters
		
		this.foundObjects = new HashMap<String, CBGeoLocatedObject>();
	}
	
	/**
	 * Begins querying the cloudbase.io APIs and returning data periodically.
	 * 
	 * @param resp The responder object to receive data
	 */
	public void startStream(CBGeoDataStreamResponder resp) {
		this.responder = resp;
		this.queryTimer = new Timer();
		CBGeoDataStreamTask task = new CBGeoDataStreamTask(this);
		
		this.queryTimer.scheduleAtFixedRate(task, CBGeoDataStream.queryInterval, CBGeoDataStream.queryInterval);
	}
	
	/**
	 * Stops the data stream
	 */
	public void stopStream() {
		this.queryTimer.cancel();
		this.queryTimer.purge();
	}
	
	public CBGeoDataStreamResponder getResponder() {
		return responder;
	}
	public void setResponder(CBGeoDataStreamResponder responder) {
		this.responder = responder;
	}
	
	public double getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(double searchRadius) {
		this.searchRadius = searchRadius;
		this.queryRadius = searchRadius;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handleResponse(CBQueuedRequest req, CBHelperResponse res) {
		if (res.isSuccess()) {
			
			List<Map<String, Object>> output = (List<Map<String, Object>>) res.getData();
			
			if (output.size() > 0) {
				for (Map<String, Object> curItem : output) {
					CBGeoLocatedObject obj = new CBGeoLocatedObject();
					CBLocation loc = new CBLocation();
					Map<String, Float> locationData = (Map<String, Float>) curItem.get("cb_location");
					loc.setLat(Float.valueOf(locationData.get("lat")));
					loc.setLng(Float.valueOf(locationData.get("lng")));
					if (curItem.containsKey("cb_location_altitude")) {
						loc.setAlt(Float.valueOf((Float) curItem.get("cb_location_altitude")));
						obj.setAltitude(Double.valueOf((Double) curItem.get("cb_location_altitude")));
					}
					obj.setCoordinate(loc);
					
					
					curItem.remove("cb_location");
					curItem.remove("cb_location_altitude");
					
					obj.setObjectData(curItem);
					
					this.foundObjects.put(String.valueOf(obj.hash()), obj);
					this.responder.receivedPoint(this.streamName, obj);
				}
			}
		} else {
			if (this.helper.isDebugMode()) {
				//Log.d(CBHelper.logTag, "Error while calling the cloudbase.io APIs");
			}
		}
		
		List<String> itemsToRemove = new ArrayList<String>();
		
		Iterator<String> it = this.foundObjects.keySet().iterator();
		
		while (it.hasNext()) {
			String key = it.next();
			CBGeoLocatedObject curObj = this.foundObjects.get(key);
			if (curObj.getCoordinate().distanceTo(this.previousPosition) > this.searchRadius) {
				this.responder.removingPoint(this.streamName, curObj);
				itemsToRemove.add(key);
			}
		}
		
		for (String curKey : itemsToRemove) {
			this.foundObjects.remove(curKey);
		}
	}

}

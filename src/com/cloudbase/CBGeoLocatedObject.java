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

import java.util.Map;

/**
 * Represents an object returned by a CBGeoDataStream with its 
 * coordinates, altitude and additional information stored in the 
 * cloud database collection
 */
public class CBGeoLocatedObject {
	/**
	 * The coordinate position of the object
	 */
	private CBLocation coordinate;
	public CBLocation getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(CBLocation coordinate) {
		this.coordinate = coordinate;
	}
	/**
	 * All of the other data stored in the cloud database for the 
	 * document
	 */
	private Map<String, Object> objectData;
	public Map<String, Object> getObjectData() {
		return objectData;
	}
	public void setObjectData(Map<String, Object> objectData) {
		this.objectData = objectData;
	}
	/**
	 * The altitude of the object if the cb_location_altitude field
	 * exists in the document
	 */
	private double altitude;
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	
	/**
	 * Generates a unique code identifying this object
	 * @return an int unique to this object
	 */
	public int hash() {
		int prime = 31;
	    int result = 1;
	    
	    result = prime * result + this.coordinate.hashCode();
	    result = prime * result + (int)this.altitude;
	    result = prime * result + this.objectData.hashCode();
	    
	    return result;

	}
	
}

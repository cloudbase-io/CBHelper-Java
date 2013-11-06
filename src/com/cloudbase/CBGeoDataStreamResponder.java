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


/**
 * Objects implementing this class will interact with a CBGeoDataStream
 * object and will instruct it how to download the data as well as receive the 
 * data stream
 */

public interface CBGeoDataStreamResponder {
	/**
	 * Returns the latest known position to the CBGeoDataStream object.
	 * This is used to retrieve the data and compute the movement speed to
	 * increase or decrease the speed of refresh
	 *
	 * @param streamName the unique identifier of the stream asking for the value
	 * @return A valid Location object
	 */
	CBLocation getLatestPosition(String streamName);
	
	/**
	 * receives a new point to be visualized
	 *
	 * @param streamName the unique identifier of the stream passing the value
	 * @param CBGeoLocatedObject An object representing a new point on the map
	 */
	void receivedPoint(String streamName, CBGeoLocatedObject point);
	
	/**
	 * Informs the application that the CBGeoDataStream is removing a point from its cache
	 *
	 * @param streamName the unique identifier of the stream removing the point
	 * @param CBGeoLocatedObject The point being removed
	 */
	void removingPoint(String streamName, CBGeoLocatedObject point);
}

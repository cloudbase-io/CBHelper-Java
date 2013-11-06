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

public class CBLocation {
	private float lat;
	private float lng;
	private float alt;
	
	public double distanceTo(CBLocation loc) { 
		return CBLocation.distance(this.getLat(), this.getLng(), loc.getLat(), loc.getLng());
	}
	
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		//if ( unit == "K" ) {
			dist = dist * 1.609344;
		//} else if ( unit == "N" ) {
		//	dist = dist * 0.8684;
		//}
		return dist * 1000;
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
		
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public float getAlt() {
		return alt;
	}
	public void setAlt(float alt) {
		this.alt = alt;
	}
}

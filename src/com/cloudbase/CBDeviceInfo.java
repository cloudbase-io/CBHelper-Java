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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * This object describes the device the application is running on. The information contained in the
 * CBDeviceInfo class are used to register the device with cloudbase.io and to generate the analytics
 * @author Stefano Buliani
 *
 */
public class CBDeviceInfo {
	// Device information used for the analytics
	private String deviceUniqueIdentifier;
	private String deviceName;
	private String deviceModel;
	private String language;
	private String country;
	private String temporaryFilesPath;
	
	/**
	 * Creates a new CBDeviceInfo object and sets the language and country
	 * variables to their default values.
	 */
	public CBDeviceInfo() {
		this.setTemporaryFilesPath(".");
		Locale currentLocale = Locale.getDefault();
		this.setCountry(currentLocale.getCountry());
		this.setLanguage(currentLocale.getLanguage());
		this.setDeviceUniqueIdentifier(CBDeviceInfo.getMacAddress());
	}
	
	/**
	 * Gets the unique identifier set for the device. By default this is set to the MAC Address of the device
	 * @return An alphanumeric unique identifier for the device. 
	 */
	public String getDeviceUniqueIdentifier() {
		return deviceUniqueIdentifier;
	}
	/**
	 * Sets the unique identifier for the device. By default this is set to the MAC Address of the device
	 * @param deviceUniqueIdentifier An alphanumeric unique identifier
	 */
	public void setDeviceUniqueIdentifier(String deviceUniqueIdentifier) {
		this.deviceUniqueIdentifier = deviceUniqueIdentifier;
	}
	/**
	 * Returns he name of the device type (iPhone, iPad, Raspberry Pi etc)
	 * @return A string identifying the device type
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * Sets the device type (iPhone, iPad, Raspberry Pi etc)
	 * @param deviceName A string identifying the device type
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * Returns the device model to go along with the device name. For example if the device name
	 * was iPhone the device model could be 4S 
	 * @return A string identifying the device model
	 */
	public String getDeviceModel() {
		return deviceModel;
	}
	/**
	 * Sets the device model identifying. This will be used alongside the device name in the 
	 * analytics. For example if the device name was iPhone the device model could be 4S 
	 * @param deviceModel A string identifying the device model
	 */
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	/**
	 * Returns the two letter code of the language of the device. By default this is set to the current locale
	 * @return The 2 letter code of the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * Sets the language of the device. By default this is set to the current locale
	 * @param language The 2 letter code of the language used on the device
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * Returns the 2 letter country code for the device. By default this is set using the current locale
	 * @return The 2 letter country code for the device.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * Sets the 2 letter country code for the device. By default this is set using the current locale
	 * @param country The 2 letter country code for the device.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Returns the temporary path used by the client library to store queued requests. By default this is set to "."
	 * @return The full path to the temporary file storate location
	 */
	public String getTemporaryFilesPath() {
		return temporaryFilesPath;
	}
	/**
	 * Sets the temporary path used by the client library to store queued requests. By default this is set to "."
	 * @param temporaryFilesPath The full path to the temporary file storate location
	 */
	public void setTemporaryFilesPath(String temporaryFilesPath) {
		this.temporaryFilesPath = temporaryFilesPath;
	}
	
	/**
	 * Returns the MAC address for the LocalHost network interface. This is used to generate the default unique
	 * identifier for the device.
	 * @return A string representation of the network interface MAC address.
	 */
	public static String getMacAddress() {
		InetAddress ip;
			
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
		 
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
			}
			return sb.toString();
		 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e){
			e.printStackTrace();
		}
		
		return null;
	}
}

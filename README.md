Copyright (c) 2013 Cloudbase.io Limited

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except 
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License 
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express 
or implied. See the License for the specific language governing permissions and limitations under 
the License.

CBHelper-Java
================

The cloudbase.io Java helper class.

This repository contains the CBHelper-Java project which compiles to a jar file containing the cloudbase.io client library.

To start using the library create an account at https://manage.cloudbase.io/register, declare an application
and use the unique codes to initialize the connection.
	CBDeviceInfo currentDevice = new CBDeviceInfo();
	currentDevice.setDeviceModel("Java-Server");
	currentDevice.setDeviceName("Server1");
	CBHelper helper = new CBHelper("app-code", "app-unique", currentDevice);
	helper.setPassword("app-password");
	
This class is a fork on the Android client library and, except for the initialization shown above, all of the Android 
documentation for cloudbase.io is valid for this  library too http://cloudbase.io/documentation/android/get-started

The cloudbase.io client library depends on
- Gson 2.2.1
- Apache http 4.3.1

Both libraries are included in the project under the Libs folder.
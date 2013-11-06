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
package com.cloudbase.datacommands;

/**
 * This abstract class should be implemented by any class to send 
 * Data Aggregation commands to cloudbase.io
 *
 * The serializeAggregateConditions should resturn a Map
 * exactly in the format needed by the CBHelper class to be added
 * to the list of parmeters, serliazed and sent to cloudbase.io
 */
public abstract class CBDataAggregationCommand {

	private CBDataAggregationCommandType commandType; 

	public CBDataAggregationCommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CBDataAggregationCommandType commandType) {
		this.commandType = commandType;
	}
	
	/**
	 * Serializes the Command object to its JSON representation
	 *
	 * @return A NSDictionary representation of the Command object. This
	 *  method should be implemented in each subclass
	 */
	public abstract Object serializeAggregateConditions();

}

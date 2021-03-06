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
 * Unwinds an array from a field inside a document by multiplying
 * the document in the result set. For example consider the document:
 * {
 *   "firstName" : "Cloud",
 *   "lastName"  : "Base",
 *   "books"     : [ "100 Years of Solitude", "Love in the Time of Cholera", "Memoirs of My Melancholy Whores" ]
 * }
 * calling the Unwind command on the books field would result in three separate
 * documents being returned by the search:
 * { "firstName" : "Cloud", "lastName" : "Base", "books" : "100 Years of Solitude" }
 * { "firstName" : "Cloud", "lastName" : "Base", "books" : "Love in the Time of Cholera" }
 * ...
 */
public class CBDataAggregationCommandUnwind extends CBDataAggregationCommand {

	private String fieldName;
	
	public CBDataAggregationCommandUnwind() {
		this.setCommandType(CBDataAggregationCommandType.CBDataAggregationUnwind);
	}
	
	@Override
	public Object serializeAggregateConditions() {
		return "$" + this.getFieldName();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}

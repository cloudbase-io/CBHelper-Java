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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The project aggregation command filters the number of fields selected
 * from a document.
 * You can either populate the <strong>includeFields</strong> property
 * to exclude all fields and only include the ones selected or use
 * the <strong>excludeFields</strong> to set up an exclusion list.
 */
public class CBDataAggregationCommandProject extends CBDataAggregationCommand {

	private List<String> includeFields;
	private List<String> excludeFields;
	
	public CBDataAggregationCommandProject() {
		this.includeFields = new ArrayList<String>();
		this.excludeFields = new ArrayList<String>();
		
		this.setCommandType(CBDataAggregationCommandType.CBDataAggregationProject);
	}
	
	@Override
	public Object serializeAggregateConditions() {
		Map<String, Integer> fieldList = new HashMap<String, Integer>();
		
		for (String field : this.getIncludeFields()) {
			fieldList.put(field, Integer.valueOf(1));
		}
		
		for (String field : this.getExcludeFields()) {
			fieldList.put(field, Integer.valueOf(0));
		}
		
		return fieldList;
	}

	public List<String> getIncludeFields() {
		return includeFields;
	}

	public void setIncludeFields(List<String> includeFields) {
		this.includeFields = includeFields;
	}

	public List<String> getExcludeFields() {
		return excludeFields;
	}

	public void setExcludeFields(List<String> excludeFields) {
		this.excludeFields = excludeFields;
	}

}

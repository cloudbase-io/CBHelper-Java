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
 * The group aggregation command. This works exaclty in the same way a GROUP BY
 * command would work in SQL.
 * The outputField array contains the number of fields for the output to be
 * "grouped by".
 * There's a number of operators to apply to the grouped field defined as
 * CBDataAggregationGroupOperator
 */
public class CBDataAggregationCommandGroup extends CBDataAggregationCommand {

	protected List<String> idFields;
	protected Map<String, Map<String, String>> groupFields;
	
	public CBDataAggregationCommandGroup() {
		this.idFields = new ArrayList<String>();
		this.groupFields = new HashMap<String, Map<String, String>>();
		
		this.setCommandType(CBDataAggregationCommandType.CBDataAggregationGroup);
	}
	
	/**
	 * Adds a field to the list of fields the output should be
	 * grouped by
	 * @param An NSString with the name of the field
	 */
	public void addOutputField(String fieldName) {
		this.idFields.add("$" + fieldName);
	}
	
	/**
	 * Adds a calculated field to the output of this group clause using the value of another field
	 * @param outputFieldName The name of the output field
	 * @param operator The operator to apply to the selected variable field
	 * @param fieldName The name of the variable field to be used with the operator
	 */
	public void addGroupFormulaForField(String outputFieldName, CBDataAggregationGroupOperator operator, String fieldName) {
		this.addGroupFormulaForValue(outputFieldName, operator, "$" + fieldName);
	}
	/**
	 * Adds a calculated field to the output of this group clause using a static value
	 * @param outputFieldName The name of the output field
	 * @param operator The operator to apply to the selected variable field
	 * @param value A value to be used with the operator
	 */
	public void addGroupFormulaForValue(String outputFieldName, CBDataAggregationGroupOperator operator, String value) {
		HashMap<String, String> newOperator = new HashMap<String, String>();
		newOperator.put(operator.toString(), value);
		this.groupFields.put(outputFieldName, newOperator);
	}
	
	@Override
	public Object serializeAggregateConditions() {
		Map<String, Object> finalSet = new HashMap<String, Object>();
		
		if (this.idFields.size() > 1) {
			finalSet.put("_id", this.idFields);
		} else {
			finalSet.put("_id", this.idFields.get(0));
		}
		
		finalSet.putAll(this.groupFields);
		
		return finalSet;
	}

}

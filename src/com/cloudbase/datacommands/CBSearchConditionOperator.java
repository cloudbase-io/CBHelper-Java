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
 * The operators used by <strong>CBSearchCondition</strong> objects when executed.
 * @author Stefano Buliani
 *
 */
public enum CBSearchConditionOperator {
	CBOperatorEqual(""),
    CBOperatorLess("$lt"),
    CBOperatorLessOrEqual("$te"),
    CBOperatorBigger("$gt"),
    CBOperatorBiggerOrEqual("$gte"),
    CBOperatorAll("$all"),
    CBOperatorExists("$exists"),
    CBOperatorMod("$mod"),
    CBOperatorNe("$ne"),
    CBOperatorIn("$in"),
    CBOperatorNin("$nin"),
    CBOperatorSize("$size"),
    CBOperatorType("$type"),
    CBOperatorWithin("$within"),
    CBOperatorNear("$near")
    ;	
	
	private CBSearchConditionOperator(final String text) {
        this.text = text;
    }

    private final String text;

    public String toString() {
        // TODO Auto-generated method stub
        return text;
    }
}

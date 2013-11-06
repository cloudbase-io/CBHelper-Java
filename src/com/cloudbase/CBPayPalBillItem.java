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
/***
 * this object represents a single item within a CBPayPalBill object.
 */
public class CBPayPalBillItem {
	/***
	 * The name of the item for the transaction
	 */
	private String name;
	/***
	 * An extended description of the item. This should also contain the amount as
	 * PayPal does not always display it.
	 */
	private String description;
	/***
	 * The amount of the transaction
	 */
	private double amount;
	/***
	 * additional taxes to be added to the amount
	 */
	private double tax;
	/***
	 * a quantity representing the number of items involved in the transaction.
	 * for example 100 poker chips
	 */
	private int quantity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

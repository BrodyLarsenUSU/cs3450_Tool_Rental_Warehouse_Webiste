package com.softwareEngineering.Spring.Models;
import org.springframework.data.annotation.Id;

public class Tool{
	@Id
	public String id;

	String name;
	String category;
	int value;
	int quantity;
	int onHand;
	int checkedOut;

	public Tool(){

	}

	public Tool(String cat, String n, int v, int q, int oh, int co){
		this.name = n;
		this.category = cat;
		this.value = v;
		this.quantity = q;
		this.onHand = oh;
		this.checkedOut = co;
	}

	public String getName() {
		return this.name;
	}

	public String getCategory(){
		return this.category;
	}

	public int getValue() {
		return this.value;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public int getOnHand() {
		return this.onHand;
	}

	public int getCheckedOut() {
		return this.checkedOut;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setOnHand(int onHand) {
		this.onHand = onHand;
	}

	public void setCheckedOut(int checkedOut) {
		this.checkedOut = checkedOut;
	}
}

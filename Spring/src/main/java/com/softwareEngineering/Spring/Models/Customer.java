package com.softwareEngineering.Spring.Models;

import org.springframework.data.annotation.Id;

public class Customer{
		@Id 
		public String id;
		public String name;

		Customer(){

		}

		Customer(String n){
			name = n;
		}

}

package com.softwareEngineering.Spring.Models;
import org.springframework.data.annotation.Id;

public class Tool{
		@Id
		public String id;

		public String name;

		Tool(){

		}

		Tool(String n){
			name = n;
		}
}

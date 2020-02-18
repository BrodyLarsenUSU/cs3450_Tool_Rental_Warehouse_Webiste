package com.softwareEngineering.Spring.Models;

import org.springframework.data.annotation.Id;

public class Customer{
		@Id 
		public String id;

		private String firstName;
		private String lastName;
		private int age;
		private boolean active;
		private boolean employee;
		private String gender;


		Customer(){

		}

		Customer(String fn, String ln, int ag, boolean ac, boolean e, String g){
			firstName = fn;
			lastName = ln;
			age = ag;
			active = ac;
			employee = e;
			gender = g;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public boolean isEmployee() {
			return employee;
		}

		public void setEmployee(boolean employee) {
			this.employee = employee;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		

}

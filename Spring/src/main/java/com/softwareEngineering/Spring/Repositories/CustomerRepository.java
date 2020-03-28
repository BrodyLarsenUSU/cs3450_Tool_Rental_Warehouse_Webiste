package com.softwareEngineering.Spring.Repositories;

import com.softwareEngineering.Spring.Models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	public Customer findCustomerByFirstName(String firstName);
	public Customer findCustomerById(String string);
	public Customer findCustomerByUsername(String username);
}

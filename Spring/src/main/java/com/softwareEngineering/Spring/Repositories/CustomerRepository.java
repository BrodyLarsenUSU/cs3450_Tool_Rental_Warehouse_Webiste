package com.softwareEngineering.Spring.Repositories;

import java.util.List;
import com.softwareEngineering.Spring.Models.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	public Customer findCustomerByName(String name);
	public Customer findCustomerById(int id);
}

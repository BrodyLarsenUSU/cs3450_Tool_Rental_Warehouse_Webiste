package com.softwareEngineering.Spring.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import com.softwareEngineering.Spring.Models.*;

public interface ToolRepository extends MongoRepository<Tool, String>{
	public Tool findToolByName(String name);
	public List<Tool> findAll();
	public Tool findToolById(String s);
}

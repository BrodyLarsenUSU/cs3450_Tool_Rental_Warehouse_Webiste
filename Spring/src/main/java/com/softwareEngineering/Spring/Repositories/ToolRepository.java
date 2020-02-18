package com.softwareEngineering.Spring.Repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.softwareEngineering.Spring.Models.*;

public interface ToolRepository extends MongoRepository<Tool, String>{
	public Tool findToolByName(String name);
	public Tool findToolById(int id);
}

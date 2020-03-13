package com.softwareEngineering.Spring.Controllers;

import java.util.List;

import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ToolController{
	@Autowired
	private ToolRepository toolRepo;

	@RequestMapping(value="/accesDBTool", method = RequestMethod.GET, produces="application/json")
	public List<Tool> getToolDB(Model model){
		return toolRepo.findAll();	
	}

	@PostMapping("/accessDBTool")
	public void postToolDB(@RequestBody Tool tool){
		toolRepo.save(tool);
	}
}
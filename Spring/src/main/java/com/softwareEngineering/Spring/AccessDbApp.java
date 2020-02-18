package com.softwareEngineering.Spring;

import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class AccessDbApp{
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ToolRepository toolRepo;

  @GetMapping("/accessDBCustomer")
  public String getDB(){
		// findCustomerByName();
		return "accessDBCustomer";
  }

  @GetMapping("/accesDBTool")
  public String getToolDB(){
		return "accesDBTool";	
  }

  @PostMapping("/accessDBTool")
  public void postToolDB(Tool tool){
		toolRepo.save(tool);
  }

  @PostMapping("/accessDBCustomer")
  public void postCustomerDB(Customer customer){
		customerRepository.save(customer);	
  }
}

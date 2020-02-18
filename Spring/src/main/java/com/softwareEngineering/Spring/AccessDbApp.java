package com.softwareEngineering.Spring;

import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
  public void postToolDB(@RequestBody Tool tool){
		toolRepo.save(tool);
  }

  @PostMapping("/accessDBCustomer")
  public void postCustomerDB(@RequestBody Customer customer){
      customerRepository.save(customer);
    	
  }
}

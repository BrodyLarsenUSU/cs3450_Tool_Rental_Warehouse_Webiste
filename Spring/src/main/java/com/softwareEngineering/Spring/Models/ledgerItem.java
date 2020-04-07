package com.softwareEngineering.Spring.Models;

import com.softwareEngineering.Spring.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;


public class ledgerItem {

    @Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ToolRepository toolRepo;
	// @Autowired
	// private LedgerRepository ledgeRepo;

	@Id 
	public String id;

    public Customer user;
    public Tool tool;

    public ledgerItem(){

    }
    
    public ledgerItem(Customer userid, Tool toolid){
        user = userid;
        tool = toolid;
    }

	public String getUserId() {
		return this.user.id;
	}

	public void setUser(String userId) {
        user = customerRepository.findCustomerById(userId);
	}

	public String getToolId() {
		return this.tool.id;
	}

	public void setTool(String toolId) {
        tool = toolRepo.findToolById(toolId);
	}

}
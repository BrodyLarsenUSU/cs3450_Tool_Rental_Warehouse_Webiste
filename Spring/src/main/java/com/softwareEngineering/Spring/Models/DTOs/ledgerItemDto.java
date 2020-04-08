package com.softwareEngineering.Spring.Models.DTOs;

import com.softwareEngineering.Spring.Models.Customer;
import com.softwareEngineering.Spring.Models.Tool;

public class ledgerItemDto {

    private String id;
    private Customer user;
    private Tool tool;

    public ledgerItemDto(){}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getUser() {
        return this.user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public Tool getTool() {
        return this.tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
    
}
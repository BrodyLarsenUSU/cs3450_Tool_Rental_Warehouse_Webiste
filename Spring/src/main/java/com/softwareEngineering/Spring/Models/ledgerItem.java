package com.softwareEngineering.Spring.Models;

import org.springframework.data.annotation.Id;

public class ledgerItem {

	@Id 
	public String id;

    private String userId;
    private String toolId;

    public ledgerItem(){

	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToolId() {
		return this.toolId;
	}

	public void setToolId(String toolId) {
		this.toolId = toolId;
	}

}
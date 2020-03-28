package com.softwareEngineering.Spring.Models.DTOs;

public class removeToolDto{

    private String userId;
    private String toolId;

    public removeToolDto(){}

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
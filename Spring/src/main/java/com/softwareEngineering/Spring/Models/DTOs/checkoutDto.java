package com.softwareEngineering.Spring.Models.DTOs;

import java.util.ArrayList;

import com.softwareEngineering.Spring.Models.Tool;

public class checkoutDto{

    private ArrayList<Tool> list;

    public checkoutDto(){

    }

    public ArrayList<Tool> getList() {
        return this.list;
    }

    public void setList(ArrayList<Tool> list) {
        this.list = list;
    }
}
package com.softwareEngineering.Spring.Models;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class ToolContainer {

    private Tool tool;
    private LocalDate checkoutDate;
    private LocalDate returnDate;


    public ToolContainer(){}

    public ToolContainer(Tool id){
        checkoutDate = LocalDate.now();
        returnDate = checkoutDate.plusDays(7);
        tool = id;
    }

    
    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getToolId(){
        return tool.id;
    }

    public Tool getTool() {
        return this.tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public LocalDate getCheckoutDate() {
        return this.checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }


}
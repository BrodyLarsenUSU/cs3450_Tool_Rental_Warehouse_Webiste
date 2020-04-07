package com.softwareEngineering.Spring.Controllers;

import com.softwareEngineering.Spring.Application;
import com.softwareEngineering.Spring.Repositories.*;
import com.softwareEngineering.Spring.Repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@EnableAutoConfiguration
@Controller
public class LedgerController extends Application {

    @Autowired
    private CustomerRepository customerRepository;
    
	@Autowired
    private ToolRepository toolRepo;

    @Autowired
    private LedgerRepository ledgeRepo;

    @PostMapping("/ledger/add")
    public String addToLedger(){
        return "";
    }


}
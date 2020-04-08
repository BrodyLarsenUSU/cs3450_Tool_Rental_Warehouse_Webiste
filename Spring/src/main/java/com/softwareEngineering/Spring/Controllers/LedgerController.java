package com.softwareEngineering.Spring.Controllers;

import com.softwareEngineering.Spring.Application;
import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Models.DTOs.*;
import com.softwareEngineering.Spring.Repositories.*;
import com.softwareEngineering.Spring.Repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @PostMapping("/ledger/checkout")
    public String checkoutLedger(@ModelAttribute("ledgerDto") ledgerDto ledgerDto, Model model){
        ledgeRepo.deleteById(ledgerDto.getId());
        return "redirect:/ledger";
    }

    @PostMapping("/ledger/remove")
    public String removeLedger(@ModelAttribute("ledgerItem") ledgerItemDto ledgerItemDto, Model model){
        ledgeRepo.deleteById(ledgerItemDto.getId());
        Customer cust = ledgerItemDto.getUser();
        cust.removeToolCheckout(ledgerItemDto.getTool().id);
        Tool temp = toolRepo.findToolById(ledgerItemDto.getTool().id);
		temp.setCheckedOut(temp.getCheckedOut() - 1);
        temp.setOnHand(temp.getOnHand() + 1);
		toolRepo.save(temp);
		customerRepository.save(cust);
        return "redirect:/ledger";
    }


}
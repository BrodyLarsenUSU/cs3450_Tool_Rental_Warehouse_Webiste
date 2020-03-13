package com.softwareEngineering.Spring.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;


@Controller
public class CustomerController{
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/customer")
	public String getDB(Model model){
		return "index";
	}

	@PostMapping("/customer")
	public String saveCustomer(@ModelAttribute("customer") @Valid customerDto customerDto, Model model){
		Customer registered = new Customer();
		registered.setFirstName(customerDto.getFirstName());
		registered.setLastName(customerDto.getLastName());
		registered.setAge(customerDto.getAge());
		registered.setGender(customerDto.getGender());
		registered.setPassword(customerDto.getPassword());
		customerRepository.save(registered);
		return "redirect:/index-add-success";
	}

	@PostMapping("/customer/signin")
	public String loginCustomer(){
		return "redirect:/index-login-success";
	}
}
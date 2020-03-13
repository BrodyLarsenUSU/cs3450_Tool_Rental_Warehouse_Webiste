package com.softwareEngineering.Spring.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;

import com.softwareEngineering.Spring.Application;
import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Models.DTOs.loginDto;
import com.softwareEngineering.Spring.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@Controller
public class CustomerController extends Application{
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
		registered.setUsername(customerDto.getUsername());
		customerRepository.save(registered);
		activeUser = registered;
		return "redirect:/index-add-success";
	}

	@PostMapping("/customer/signin")
	public String loginCustomer(@ModelAttribute("loginUser") loginDto loginDto, Model model){
		Customer temp = customerRepository.findCustomerByUsername(loginDto.getUsername());
		if(temp != null){
			if(loginDto.getPassword().compareTo(temp.getPassword()) == 0){
				activeUser = temp;
				return "redirect:/index-login-success";
			}
		}
		return "redirect:/signin-error";
	}

	@RequestMapping("/customer/signout")
	public String signoutCustomer(){
		activeUser = null;
		return "redirect:/index-logout-success";
	}
}
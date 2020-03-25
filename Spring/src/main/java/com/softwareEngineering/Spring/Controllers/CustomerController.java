package com.softwareEngineering.Spring.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;

import com.softwareEngineering.Spring.Application;
import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Models.DTOs.loginDto;
import com.softwareEngineering.Spring.Models.DTOs.toolDto;
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
public class CustomerController extends Application {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ToolRepository toolRepo;

	@GetMapping("/customer")
	public String getDB(Model model) {
		return "index";
	}

	@PostMapping("/customer")
	public String saveCustomer(@ModelAttribute("customer") @Valid customerDto customerDto, HttpServletRequest request, Model model){
		Customer registered = new Customer();
		registered.setFirstName(customerDto.getFirstName());
		registered.setLastName(customerDto.getLastName());
		registered.setAge(customerDto.getAge());
		registered.setGender(customerDto.getGender());
		registered.setPassword(customerDto.getPassword());
		registered.setUsername(customerDto.getUsername());
		customerRepository.save(registered);
		HttpSession session = request.getSession();
		session.setAttribute("activeUser", registered);
		return "redirect:/index-add-success";
	}

	@PostMapping("/customer/signin")
	public String loginCustomer(@ModelAttribute("loginUser") loginDto loginDto, HttpServletRequest request, Model model){
		HttpSession session = request.getSession();
		Customer temp = customerRepository.findCustomerByUsername(loginDto.getUsername());
		if(temp != null){
			if(loginDto.getPassword().compareTo(temp.getPassword()) == 0){
				session.setAttribute("activeUser", temp);
				return "redirect:/index-login-success";
			}
		}
		return "redirect:/signin-error";
	}

	@RequestMapping("/customer/signout")
	public String signoutCustomer(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("activeUser", null);
		return "redirect:/index-logout-success";
	}

	@PostMapping("/customer/reserveTool")
	public String reserveTool(@ModelAttribute("toolDto") toolDto toolDto, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Customer temp = (Customer)session.getAttribute("activeUser");
		temp.addToolToReserve(toolDto.getId());
		session.setAttribute("activeUser", temp);
		customerRepository.save(temp);
		return "redirect:/tools-success";
	}

	@PostMapping("/customer/removeReservation")
	public String removeReservation(@ModelAttribute("toolDto") toolDto toolDto, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Customer temp = (Customer)session.getAttribute("activeUser");
		temp.removeReservation(toolDto.getId());
		session.setAttribute("activeUser", temp);
		customerRepository.save(temp);
		return "redirect:/checkout";
	}
}
package com.softwareEngineering.Spring.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;

import com.softwareEngineering.Spring.Application;
import com.softwareEngineering.Spring.Models.*;
import com.softwareEngineering.Spring.Models.DTOs.checkoutDto;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Models.DTOs.loginDto;
import com.softwareEngineering.Spring.Models.DTOs.lookupDto;
import com.softwareEngineering.Spring.Models.DTOs.removeToolDto;
import com.softwareEngineering.Spring.Models.DTOs.toolDto;
import com.softwareEngineering.Spring.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
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
	@Autowired
	private LedgerRepository ledgeRepo;

	@GetMapping("/customer")
	public String getDB(Model model) {
		return "index";
	}

	@PostMapping("/customer")
	public String saveCustomer(@ModelAttribute("customer") @Valid customerDto customerDto, HttpServletRequest request, Model model){
		Customer registered = new Customer();
		HttpSession session = request.getSession();
		if(customerDto.getAge() >= 18){
			registered.setFirstName(customerDto.getFirstName());
			registered.setLastName(customerDto.getLastName());
			registered.setAge(customerDto.getAge());
			registered.setGender(customerDto.getGender());
			registered.setPassword(customerDto.getPassword());
			registered.setUsername(customerDto.getUsername());
			customerRepository.save(registered);
			session.setAttribute("activeUser", registered);
			return "redirect:/index-add-success";
		}
		else{
			return "redirect:/index-add-fail";
		}
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
	public String reserveTool(@RequestParam("id") String id, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Customer temp = (Customer)session.getAttribute("activeUser");
		temp.addToolToReserve(id);
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

	@PostMapping("/customer/checkoutTools")
	public String checkoutTools(Model model, HttpServletRequest request) {
		HttpSession session =  request.getSession();
		Customer cust = (Customer)session.getAttribute("activeUser");
		ArrayList<String> toolList = cust.getReservedTools();
		for(String t : toolList){
			Tool temp = toolRepo.findToolById(t);
			temp.setCheckedOut(temp.getCheckedOut() + 1);
			temp.setOnHand(temp.getOnHand() - 1);
			toolRepo.save(temp);
			ledgeRepo.save(new ledgerItem(cust, toolRepo.findToolById(t)));
		}
		cust.addToolsToCheckedOut(toolList);
		session.setAttribute("activeUser", cust);
		customerRepository.save(cust);
		return "redirect:/checkout-success";
	}

	@PostMapping("/customer/lookup")
	public String lookupCustomer(@ModelAttribute("lookupDto") lookupDto lookupDto, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Customer temp = customerRepository.findCustomerById(lookupDto.getId());
		if(temp == null){
			return "redirect:/employeePortal-search-error";
		}
		session.setAttribute("lookupDto", temp);
		return "redirect:/employeePortal-customer-search";
	}

	@PostMapping("/customer/removeCheckout")
	public String removeCheckout(@ModelAttribute("removeToolDto") removeToolDto removeToolDto, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
		Customer cust = customerRepository.findCustomerById(removeToolDto.getUserId());
		cust.removeToolCheckout(removeToolDto.getToolId());
		Tool temp = toolRepo.findToolById(removeToolDto.getToolId());
		temp.setCheckedOut(temp.getCheckedOut() - 1);
		temp.setOnHand(temp.getOnHand() + 1);
		toolRepo.save(temp);
		customerRepository.save(cust);
		session.setAttribute("lookupDto", cust);
		return "redirect:/employeePortal-customer-search";
	}
}
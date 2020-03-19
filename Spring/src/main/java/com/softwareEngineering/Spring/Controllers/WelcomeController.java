package com.softwareEngineering.Spring.Controllers;

import com.softwareEngineering.Spring.Repositories.ToolRepository;
import com.softwareEngineering.Spring.Repositories.CustomerRepository;
import com.softwareEngineering.Spring.Models.DTOs.ContactUsDto;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Models.DTOs.loginDto;
import com.softwareEngineering.Spring.Models.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import com.softwareEngineering.Spring.Application;
import com.softwareEngineering.Spring.Models.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Properties;

@Controller
public class WelcomeController extends Application {

    @Autowired
    private ToolRepository toolRepo;
	@Autowired
	private CustomerRepository customerRepo;

	@RequestMapping("/")
    public String getStarterIndex(){
        return "index";
    }

    @RequestMapping("/index")
    public String getIndex(){
        // model.addAttribute("addedSuccess", false);
        return "index";
    }

    @RequestMapping("/index-add-success")
    public String getIndexAddSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        model.addAttribute("addedSuccess", true);
        model.addAttribute("alert_message", "Successfully Created Account");
        model.addAttribute("name", ((Customer) session.getAttribute("activeUser")).getFirstName() + " " + ((Customer) session.getAttribute("activeUser")).getLastName());
        return "index";
    }

    @RequestMapping("/index-login-success")
    public String getIndexLoginSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        model.addAttribute("addedSuccess", true);
        model.addAttribute("alert_message", "Successfully Logged In");
        model.addAttribute("name", ((Customer) session.getAttribute("activeUser")).getFirstName() + " " + ((Customer) session.getAttribute("activeUser")).getLastName());
        return "index";
    }

    @RequestMapping("/index-logout-success")
    public String getIndexLogoutSuccess(Model model){
        model.addAttribute("addedSuccess", true);
        model.addAttribute("alert_message", "Successfully Logged Out");
        model.addAttribute("name", "");
        return "index";
    }

    @RequestMapping("/tools")
    public String getTools(Model model){
        model.addAttribute("tools", toolRepo.findAll());
        return "tools";
    }

    @RequestMapping("/checkout")
    public String getCheckout(){
        return "checkout";
    }

    @RequestMapping("/contactus")
    public String getContactUs(Model model){
        ContactUsDto contactDto = new ContactUsDto();
        model.addAttribute("contactForm", contactDto);
        return "contactUs";
    }

    @PostMapping("/contact-form")
    public String getContactForm(@ModelAttribute("contactForm") ContactUsDto contactUsDto, Model model){
        System.out.println("Into Form");
        String to = "419223f1e0-df6321@inbox.mailtrap.io";
        String name = contactUsDto.getFirstName() + " " + contactUsDto.getLastName();
        String email = contactUsDto.getEmail();
        String message = "From: " + name + "\n" + contactUsDto.getMessage();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("9076e525e32acb", "2297149a0e1438");
            }
        });

        try{
            MimeMessage content = new MimeMessage(session);
            content.setFrom(new InternetAddress(email));
            content.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            content.setSubject("New Contact Form Submission");
            content.setText(message);
            System.out.println("Test4");
            Transport.send(content);
            System.out.println("Email sent");
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            return "redirect:/contact-form-fail";
        }

        return "redirect:/contact-form-success";
    }

    @RequestMapping("/contact-form-success")
    public String getContactFormSuccess(Model model){
        model.addAttribute("formSuccess", true);
        model.addAttribute("successMessage", "Email Sent Successfully");
        ContactUsDto contactDto = new ContactUsDto();
        model.addAttribute("contactForm", contactDto);
        return "contactUs";
    }

    @RequestMapping("/signin")
    public String getSignIn(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        loginDto loginDto = new loginDto();
        model.addAttribute("loginUser", loginDto);
        if(session.getAttribute("activeUser") != null){
            model.addAttribute("activeUser", true);
        }
        return "signIn";
    }

    @RequestMapping("/signin-error")
    public String getSignInError(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        loginDto loginDto = new loginDto();
        model.addAttribute("signInError", true);
        model.addAttribute("error_message", "Failed to Sign In");
        model.addAttribute("loginUser", loginDto);
        if(session.getAttribute("activeUser") != null){
            model.addAttribute("activeUser", true);
        }
        return "signIn";
    }

    @RequestMapping("/projects")
    public String getProjects(){
        return "projects";
    }

    @RequestMapping("/signup")
    public String getSignUP(Model model){
        customerDto custDto = new customerDto();
		model.addAttribute("customer", custDto);
        return "signUp";
    }
}

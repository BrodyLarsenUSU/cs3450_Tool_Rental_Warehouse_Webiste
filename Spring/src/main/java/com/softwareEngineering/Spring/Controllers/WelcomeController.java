package com.softwareEngineering.Spring.Controllers;

import com.softwareEngineering.Spring.Repositories.ToolRepository;
import com.softwareEngineering.Spring.Repositories.CustomerRepository;
import com.softwareEngineering.Spring.Repositories.LedgerRepository;
import com.softwareEngineering.Spring.Models.DTOs.ContactUsDto;
import com.softwareEngineering.Spring.Models.DTOs.checkoutDto;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Models.DTOs.ledgerDto;
import com.softwareEngineering.Spring.Models.DTOs.ledgerItemDto;
import com.softwareEngineering.Spring.Models.DTOs.loginDto;
import com.softwareEngineering.Spring.Models.DTOs.lookupDto;
import com.softwareEngineering.Spring.Models.DTOs.removeToolDto;
import com.softwareEngineering.Spring.Models.DTOs.toolDto;
import com.softwareEngineering.Spring.Models.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import com.softwareEngineering.Spring.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Mod;
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
    @Autowired
    private LedgerRepository ledgeRepo;

    //navigate to home page
	@RequestMapping("/")
    public String getStarterIndex(){
        return "index";
    }

    //navigate to home page
    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        return "index";
    }

    //create new account and sign them in.
    @RequestMapping("/index-add-success")
    public String getIndexAddSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("addedSuccess", true);
        model.addAttribute("signedIn", true);
        model.addAttribute("alert_message", "Successfully Created Account");
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        if(temp.isEmployee()){
            model.addAttribute("employee", true);
        }
        return "index";
    }

    //report failure to sign in. 
    @RequestMapping("/index-add-fail")
    public String getIndexAddFail(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("addedFailed", true);
        model.addAttribute("alert_message", session.getAttribute("message"));
        return "index";
    }
    
    //successfully sign in user. 
    @RequestMapping("/index-login-success")
    public String getIndexLoginSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("addedSuccess", true);
        model.addAttribute("signedIn", true);
        model.addAttribute("alert_message", "Successfully Logged In");
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        if(temp.isEmployee()){
            model.addAttribute("employee", true);
        }
        return "index";
    }

    // logout the user. 
    @RequestMapping("/index-logout-success")
    public String getIndexLogoutSuccess(Model model){
        model.addAttribute("addedSuccess", true);
        model.addAttribute("alert_message", "Successfully Logged Out");
        model.addAttribute("name", "");
        return "index";
    }

    // navigate to tools page
    @RequestMapping("/tools")
    public String getTools(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("tools", toolRepo.findAll());
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            model.addAttribute("toolDto", new toolDto());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        return "tools";
    }

    //add reserved tools to user.
    @RequestMapping("/tools-success")
    public String getToolsSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("tools", toolRepo.findAll());
        model.addAttribute("signedIn", true);
        model.addAttribute("toolDto", new toolDto());
        model.addAttribute("checkoutSuccess", true);
        model.addAttribute("alert_message", "Tool Reserved");
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        if(temp.isEmployee()){
            model.addAttribute("employee", true);
        }
        return "tools";
    }

    //checkout tools.
    @RequestMapping("/checkout")
    public String getCheckout(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp != null){
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
            model.addAttribute("signedIn", true);
            ArrayList<Tool> toolList = new ArrayList<>();;
            for(String s : temp.getReservedTools()){
                toolList.add(toolRepo.findToolById(s));
            }
            model.addAttribute("reservedTools", toolList);
            model.addAttribute("checkoutDto", new checkoutDto());
        }
        return "checkout";
    }

    //successfully added tools to user account. 
    @RequestMapping("/checkout-success")
    public String getCheckoutSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp != null){
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
            model.addAttribute("signedIn", true);
            ArrayList<Tool> toolList = new ArrayList<>();;
            for(String s : temp.getReservedTools()){
                toolList.add(toolRepo.findToolById(s));
            }
            model.addAttribute("reservedTools", toolList);
        }
        model.addAttribute("postSuccess", true);
        return "checkout";
    }

    //navigate to contact us page. 
    @RequestMapping("/contactus")
    public String getContactUs(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("activeUser", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        ContactUsDto contactDto = new ContactUsDto();
        model.addAttribute("contactForm", contactDto);
        return "contactUs";
    }

    //submit contact form and send email. 
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

    //successfully sent email. 
    @RequestMapping("/contact-form-success")
    public String getContactFormSuccess(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("activeUser", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        model.addAttribute("formSuccess", true);
        model.addAttribute("successMessage", "Email Sent Successfully");
        ContactUsDto contactDto = new ContactUsDto();
        model.addAttribute("contactForm", contactDto);
        return "contactUs";
    }

    //sign in page
    @RequestMapping("/signin")
    public String getSignIn(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        loginDto loginDto = new loginDto();
        model.addAttribute("loginUser", loginDto);
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("activeUser", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        return "signIn";
    }

    //error signing in
    @RequestMapping("/signin-error")
    public String getSignInError(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        loginDto loginDto = new loginDto();
        model.addAttribute("signInError", true);
        model.addAttribute("error_message", "Failed to Sign In");
        model.addAttribute("loginUser", loginDto);
        if(session.getAttribute("activeUser") != null){
            model.addAttribute("activeUser", true);
            model.addAttribute("signedIn", true);
        }
        return "signIn";
    }

    //projects page. 
    @RequestMapping("/projects")
    public String getProjects(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("activeUser", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        return "projects";
    }

    //sign up page, add user to database. 
    @RequestMapping("/signup")
    public String getSignUP(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        customerDto custDto = new customerDto();
        model.addAttribute("customer", custDto);
        if(temp != null){
            model.addAttribute("signedIn", true);
            model.addAttribute("activeUser", true);
            model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
            if(temp.isEmployee()){
                model.addAttribute("employee", true);
            }
        }
        return "signUp";
    }

    //employee portal shows up if the user is an employee.
    @RequestMapping("/employeePortal")
    public String getEPortal(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("lookupDto", null);
        model.addAttribute("signedIn", true);
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("lookupDto", new lookupDto());
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        model.addAttribute("removeToolDto", new removeToolDto());
        return "employeePortal";
    }

    //search for a nonexistant user. 
    @RequestMapping("/employeePortal-search-error")
    public String getEPortalError(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("lookupDto", null);
        model.addAttribute("signedIn", true);
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("lookupDto", new lookupDto());
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        model.addAttribute("removeToolDto", new removeToolDto());
        model.addAttribute("error", true);
        return "employeePortal";
    }

    //search for a customer. 
    @RequestMapping("/employeePortal-customer-search")
    public String getCustSearch(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        Customer look = (Customer)session.getAttribute("lookupDto");
        model.addAttribute("signedIn", true);
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        model.addAttribute("customer", look);
        model.addAttribute("lookupDto", new lookupDto());
        model.addAttribute("found", true);
        ArrayList<ToolContainer> userTools = look.getContainedCheckoutTools();
        model.addAttribute("tools", userTools);
        model.addAttribute("removeToolDto", new removeToolDto());
        return "employeePortal";
    }

    //navigate to ledger. 
    @RequestMapping("/ledger")
    public String getLedger(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        if(temp == null || !temp.isEmployee()){
            return "redirect:/signin";
        }
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        model.addAttribute("signedIn", true);
        model.addAttribute("ledger", ledgeRepo.findAll());
        model.addAttribute("ledgerDto", new ledgerDto());
        model.addAttribute("ledgerItem", new ledgerItemDto());
        return "ledger";
    }

    //navigate to user portal. Shows up once signed in.
    @RequestMapping("userPortal")
    public String getUserPortal(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Customer temp = (Customer)session.getAttribute("activeUser");
        model.addAttribute("name", temp.getFirstName() + " " + temp.getLastName());
        model.addAttribute("signedIn", true);
        model.addAttribute("customer", temp);
        ArrayList<ToolContainer> userTools = temp.getContainedCheckoutTools();
        model.addAttribute("tools", userTools);
        return "userPortal";
    }
}

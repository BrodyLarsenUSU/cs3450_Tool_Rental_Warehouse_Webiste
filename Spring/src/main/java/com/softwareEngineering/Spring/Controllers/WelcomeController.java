package com.softwareEngineering.Spring.Controllers;

import com.softwareEngineering.Spring.Repositories.ToolRepository;
import com.softwareEngineering.Spring.Repositories.CustomerRepository;
import com.softwareEngineering.Spring.Models.DTOs.ContactUsDto;
import com.softwareEngineering.Spring.Models.DTOs.customerDto;
import com.softwareEngineering.Spring.Models.DTOs.loginDto;
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
    public String getIndexAddSuccess(Model model){
        model.addAttribute("addedSuccess", true);
        model.addAttribute("alert_message", "Successfully Created Account");
        model.addAttribute("name", activeUser.getFirstName() + " " + activeUser.getLastName());
        return "index";
    }

    @RequestMapping("/index-login-success")
    public String getIndexLoginSuccess(Model model){
        model.addAttribute("addedSuccess", true);
        model.addAttribute("alert_message", "Successfully Logged In");
        model.addAttribute("name", activeUser.getFirstName() + " " + activeUser.getLastName());
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
        model.addAttribute("contactForm", new ContactUsDto());
        return "contactUs";
    }

    @PostMapping("/contact-form")
    public String getContactForm(@ModelAttribute("contactForm") ContactUsDto contactUsDto){
        String to = "kostandinos.sergakis@aggiemail.usu.edu";
        String name = contactUsDto.getFirstName() + " " + contactUsDto.getLastName();
        String email = contactUsDto.getEmail();
        String message = contactUsDto.getMessage();
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try{
            MimeMessage content = new MimeMessage(session);
            content.setFrom(new InternetAddress(email));
            content.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            content.setSubject("New Contact Form Submission"):
            content.setText(message);
            Transport.send(content);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }

        return "redirect:/contact-form-success";
    }

    @RequestMapping("/contact-form-success")
    public String getContactFormSuccess(Model model){
        model.addAttribute("formSuccess", true);
        model.addAttribute("successMessage", "Email Sent Successfully");

        return "contactus";
    }

    @RequestMapping("/signin")
    public String getSignIn(Model model){
        loginDto loginDto = new loginDto();
        model.addAttribute("loginUser", loginDto);
        if(activeUser != null){
            model.addAttribute("activeUser", true);
        }
        return "signIn";
    }

    @RequestMapping("/signin-error")
    public String getSignInError(Model model){
        loginDto loginDto = new loginDto();
        model.addAttribute("signInError", true);
        model.addAttribute("error_message", "Failed to Sign In");
        model.addAttribute("loginUser", loginDto);
        if(activeUser != null){
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

package com.softwareEngineering.Spring;

import com.softwareEngineering.Spring.Repositories.ToolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @Autowired
    private ToolRepository toolRepo;

    @RequestMapping("/index")
    public String getIndex(){
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

    @RequestMapping("/signin")
    public String getSignIn(){
        return "signIn";
    }

    @RequestMapping("/signup")
    public String getSignUP(){
        return "signUp";
    }

}

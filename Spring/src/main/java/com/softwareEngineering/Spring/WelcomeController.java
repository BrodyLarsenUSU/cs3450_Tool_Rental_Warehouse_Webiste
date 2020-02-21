package com.softwareEngineering.Spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping("/index")
    public String getIndex(){
        return "index";
    }

    @RequestMapping("/tools")
    public String getToolsPag2(){
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

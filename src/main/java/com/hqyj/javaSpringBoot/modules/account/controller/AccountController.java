package com.hqyj.javaSpringBoot.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author  Jayoung
 * createDate  2020/8/17 0017 17:02
 * version 1.0
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    /**
     * 127.0.0.1:667/account/login   ---- get
     */
    @GetMapping("/login")
    public String loginPage(){
        return "indexSimple";
    }

    @GetMapping("/register")
    public String registerPage(){
        return  "indexSimple";
    }

    /**
     * 127.0.0.1:667/account/users   ---- get
     */
    @GetMapping("/users")
    public String usersPage(){
        return "index";
    }

    /**
     * 127.0.0.1:667/account/roles   ---- get
     */
    @GetMapping("/roles")
        public String rolesPage(){
            return "index";
    }

    /**
     * 127.0.0.1:667/account/roles   ---- get
     */
    @GetMapping("/resources")
    public String resourcesPage(){
        return "index";
    }
}

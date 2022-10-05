package com.torun.mtone.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/add")
    public ModelAndView moveToSignInForm() {
        ModelAndView mv = new ModelAndView("account/add");
        return mv;
    }
}

package com.torun.mtone.account.controller;

import com.torun.mtone.account.service.AccountSvc;
import com.torun.mtone.account.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/account")
public class AccountController {

    private final AccountSvc accountSvcImpl;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountController(AccountSvc accountSvcImpl, PasswordEncoder passwordEncoder) {
        this.accountSvcImpl = accountSvcImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/add")
    public ModelAndView moveToSignInForm() {
        ModelAndView mv = new ModelAndView("account/add");
        mv.addObject("newUser", new UserVo());
        return mv;
    }

    @PostMapping("/add")
    public String signIn(UserVo user) {
        String encodedPassword = passwordEncoder.encode(user.getUser_pw());
        user.setUser_pw(encodedPassword);
        accountSvcImpl.inputAccount(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public ModelAndView moveToLoginForm() {
        ModelAndView mv = new ModelAndView("account/login");
        mv.addObject("user", new UserVo());
        return mv;
    }

    @PostMapping("/login")
    public String login(UserVo user, HttpServletRequest request) {
        Map<String, String> userInfo = accountSvcImpl.getUserInfo(user);
        String userEnteredPassword = user.getUser_pw();
        String actualPassword = userInfo.get("user_pw");
        HttpSession session = request.getSession();
        if(passwordEncoder.matches(userEnteredPassword, actualPassword)) {
            log.debug("로그인 성공");
            session.setAttribute("loginUser", user.getUser_id());
        } else {
            log.debug("로그인 실패");
        }
        return "redirect:/";
    }
}

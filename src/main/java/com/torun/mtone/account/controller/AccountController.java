package com.torun.mtone.account.controller;

import com.torun.mtone.account.service.AccountSvc;
import com.torun.mtone.account.vo.SessionVo;
import com.torun.mtone.account.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
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
        return "redirect:/home";
    }

    @GetMapping("/login")
    public ModelAndView moveToLoginForm() {
        ModelAndView mv = new ModelAndView("account/login");
        mv.addObject(SessionVo.LOGIN_USER, new UserVo());
        return mv;
    }

    @PostMapping("/login")
    public String login(UserVo loginUser, Model model, HttpServletRequest request) {
        Map<String, String> userInfo = accountSvcImpl.getUserInfo(loginUser);
        if(userInfo == null) {
            return "redirect:/account/login";
        }
        String userEnteredPassword = loginUser.getUser_pw();
        String actualPassword = userInfo.get("user_pw");

        if(passwordEncoder.matches(userEnteredPassword, actualPassword)) {
            log.debug("로그인 성공");
            HttpSession session = request.getSession();
            session.setAttribute(SessionVo.LOGIN_USER, userInfo.get("user_id"));
            session.setAttribute(SessionVo.LOGIN_NICKNAME, userInfo.get("user_nickname"));
            session.setAttribute(SessionVo.LOGIN_MBTI, userInfo.get("user_mbti"));
            printLoginUserInSession(session);
        } else {
            log.debug("로그인 실패");
            return "redirect:/account/login";
        }
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session == null) {
            log.info("session={}", "세션 널임");
        } else {
            //세션 데이터 출력
            printLoginUserInSession(session);
        }

        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private void printLoginUserInSession(HttpSession session) {
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}",
                        name, session.getAttribute(name)));
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());
    }
}

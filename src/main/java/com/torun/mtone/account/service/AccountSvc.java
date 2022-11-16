package com.torun.mtone.account.service;

import com.torun.mtone.account.vo.UserVo;
import org.apache.catalina.User;
import org.springframework.validation.Errors;

import java.util.Map;

public interface AccountSvc {
    public void inputAccount(UserVo user);
    public Map<String, String> getUserInfo(UserVo user);

    Map<String, String> validateHandling(Errors errors);
}

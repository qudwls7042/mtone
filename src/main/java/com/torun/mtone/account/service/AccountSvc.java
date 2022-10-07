package com.torun.mtone.account.service;

import com.torun.mtone.account.vo.UserVo;
import org.apache.catalina.User;

import java.util.Map;

public interface AccountSvc {
    public void inputAccount(UserVo user);

    public Map<String, String> getUserInfo(UserVo user);
}

package com.torun.mtone.account.service;

import com.torun.mtone.account.vo.UserVo;
import com.torun.mtone.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountSvcImpl implements AccountSvc {

    private final AccountMapper accountMapper;

    @Autowired
    public AccountSvcImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public void inputAccount(UserVo user) {
        accountMapper.inputAccount(user);
    }

    @Override
    public Map<String, String> getUserInfo(UserVo user) {
        return accountMapper.getUserInfo(user);
    }
}

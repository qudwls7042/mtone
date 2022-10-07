package com.torun.mtone.mapper;

import com.torun.mtone.account.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface AccountMapper {
    public void inputAccount(UserVo vo);

    public Map<String, String> getUserInfo(UserVo userVo);
}

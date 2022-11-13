package com.torun.mtone.account.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Data
public class UserVo {
    @NotEmpty
    String userId;
    @NotEmpty
    String userPw;
    @NotEmpty
    String userName;
    @NotEmpty
    String userNickname;
    @NotEmpty
    String userMbti;
    @NotEmpty
    String userEmail;
    @NotEmpty
    String userTel;

    public UserVo() {

    }


}

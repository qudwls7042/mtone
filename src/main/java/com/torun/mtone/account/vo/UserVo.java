package com.torun.mtone.account.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {

    String user_id;
    String user_pw;
    String user_name;
    String user_email;
    String user_tel;

    public UserVo(String user_id, String user_pw, String user_name, String user_email, String user_tel) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_tel = user_tel;
    }

    @Override
    public String toString() {
        return "userVo{" +
                "user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_tel='" + user_tel + '\'' +
                '}';
    }
}

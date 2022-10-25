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
    String user_id;
    @NotEmpty
    String user_pw;
    @NotEmpty
    String user_name;
    @NotEmpty
    String user_nickname;
    @NotEmpty
    String user_mbti;
    @NotEmpty
    String user_email;
    @NotEmpty
    String user_tel;

    public UserVo() {

    }

    public UserVo(String user_id, String user_pw, String user_name, String user_nickname, String user_mbti, String user_email, String user_tel) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_nickname = user_nickname;
        this.user_mbti = user_mbti;
        this.user_email = user_email;
        this.user_tel = user_tel;
    }

    @Override
    public String toString() {
        return "userVo{" +
                "user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_nickname='" + user_nickname + '\'' +
                ", mbti='" + user_mbti + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_tel='" + user_tel + '\'' +
                '}';
    }
}

package com.torun.mtone.account.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@Data
public class UserVo {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9-_]{2,10}$", message = "아이디는 특수문자를 제외한 2~10자리여야 합니다.")
    String userId;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    String userPw;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    String userName;
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    String userNickname;
    @NotBlank(message = "MBTI는 필수 입력 값입니다.")
    String userMbti;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    String userEmail;
    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    String userTel;

    public UserVo() {

    }


}

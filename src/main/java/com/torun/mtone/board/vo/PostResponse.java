package com.torun.mtone.board.vo;

import lombok.Getter;

@Getter
public class PostResponse {

    private String rn;
    private String storyNo;                // PK
    private String title;                  // 제목
    private String content;                // 내용
    private String views;                   // 조회 수
    private String initDate;                 // 작성자
    private String updateDate;                 // 작성자
    private String likeCnt;                 // 작성자
    private String writer;                 // 작성자
    private String userId;                 // 작성자
}
package com.torun.mtone.board.vo;

import lombok.Getter;

@Getter
public class PostResponse {

    private String rn;
    private String sn;                // PK
    private String title;                  // 제목
    private String content;                // 내용
    private String views;                   // 조회 수
    private String init_date;                 // 작성자
    private String update_date;                 // 작성자
    private String like_cnt;                 // 작성자
    private String writer;                 // 작성자
    private String user_id;                 // 작성자
}
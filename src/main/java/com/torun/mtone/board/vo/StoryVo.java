package com.torun.mtone.board.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StoryVo {

    private String storyNo;
    private String writer;
    private String title;
    private String content;
    private String views;
    private String initDate;
    private String updateDate;
    private String userId;

    public StoryVo() {

    }


}

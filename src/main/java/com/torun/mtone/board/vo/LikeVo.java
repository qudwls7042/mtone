package com.torun.mtone.board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeVo {
    private String StoryNo;
    private String UserId;
    private int clickedCnt;
}

package com.torun.mtone.board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentVo {
    private String story_no;
    private String comment_no;
    private String content;
    private String init_date;

    public CommentVo() {

    }

    public CommentVo(String story_no, String comment_no, String content, String init_date) {
        this.story_no = story_no;
        this.comment_no = comment_no;
        this.content = content;
        this.init_date = init_date;
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "story_no='" + story_no + '\'' +
                ", comment_no='" + comment_no + '\'' +
                ", content='" + content + '\'' +
                ", init_date='" + init_date + '\'' +
                '}';
    }
}

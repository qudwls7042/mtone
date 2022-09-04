package com.torun.mtone.board.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryVo {

    private String story_no;
    private String writer;
    private String title;
    private String content;
    private String views;

    private String init_date;
    private String update_date;

    public StoryVo() {

    }

    public StoryVo(String story_no, String writer, String title, String content, String views, String init_date, String update_date) {
        this.story_no = story_no;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.init_date = init_date;
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        return "StoryVo{" +
                "story_no='" + story_no + '\'' +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views='" + views + '\'' +
                ", init_date='" + init_date + '\'' +
                ", update_date='" + update_date + '\'' +
                '}';
    }
}

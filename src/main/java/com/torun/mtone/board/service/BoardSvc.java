package com.torun.mtone.board.service;

import com.torun.mtone.board.vo.StoryVo;

import java.util.List;
import java.util.Map;

public interface BoardSvc {

    public List<Map<String, String>> readStories();
    public Map<String, String> readStory(String story_no);
    public void inputStory(StoryVo vo);
    public void editStory(StoryVo vo);
    public void deleteStory(String story_no);
}

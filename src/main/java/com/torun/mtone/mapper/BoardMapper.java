package com.torun.mtone.mapper;

import com.torun.mtone.board.vo.StoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    public List<Map<String, String>> readStories();

    public void inputStory(StoryVo vo);
    public void editStory(StoryVo vo);

    public void deleteStory(String story_no);

    public Map<String, String> readStory(String story_no);
}

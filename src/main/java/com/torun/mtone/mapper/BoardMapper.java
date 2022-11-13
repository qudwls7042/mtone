package com.torun.mtone.mapper;

import com.torun.mtone.board.vo.LikeVo;
import com.torun.mtone.board.vo.PostResponse;
import com.torun.mtone.board.vo.StoryVo;
import com.torun.mtone.common.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    public   List<PostResponse> readStories(SearchDto params);

    public int getStoriesCount(SearchDto params);

    public void inputStory(StoryVo vo);
    public void editStory(StoryVo vo);

    public void deleteStory(String story_no);

    public Map<String, String> readStory(String story_no);

    public void inputComment(Map<String, String> comment);
    public List<Map<String, String>> readComments(String story_no);

    void deleteComments(String story_no);
    public void plusViews(String story_no);
    public int getLikeIsClickedCnt(LikeVo likeVo);
    public void deleteClickedCnt(LikeVo likeVo);
    public void inputClickedCnt(LikeVo likeVo);
}

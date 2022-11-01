package com.torun.mtone.board.service;

import com.torun.mtone.board.vo.PostResponse;
import com.torun.mtone.board.vo.StoryVo;
import com.torun.mtone.common.SearchDto;
import com.torun.mtone.paging.PagingResponse;

import java.util.List;
import java.util.Map;

public interface BoardSvc {

    public PagingResponse<PostResponse> readStories(final SearchDto params);
    public Map<String, String> readStory(String story_no);
    public void inputStory(StoryVo vo);
    public void editStory(StoryVo vo);
    public void deleteStory(String story_no);
    public void inputComment(Map<String, String> comment);
    public void deleteComments(String story_no);
    public List<Map<String, String>> readComments(String story_no);
    public void plusViews(String story_no);
}

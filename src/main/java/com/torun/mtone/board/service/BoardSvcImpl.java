package com.torun.mtone.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.torun.mtone.board.vo.LikeVo;
import com.torun.mtone.board.vo.PostResponse;
import com.torun.mtone.board.vo.StoryVo;
import com.torun.mtone.common.SearchDto;
import com.torun.mtone.mapper.BoardMapper;
import com.torun.mtone.paging.Pagination;
import com.torun.mtone.paging.PagingResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardSvcImpl implements BoardSvc{

    private final BoardMapper boardMapper;

    public BoardSvcImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public void inputStory(StoryVo vo) {
        boardMapper.inputStory(vo);
    }

    @Override
    public void editStory(StoryVo vo) {
        boardMapper.editStory(vo);
    }

    @Override
    public void deleteStory(String story_no) {
        boardMapper.deleteStory(story_no);
    }

    @Override
    public void inputComment(Map<String, String> comment) {
        boardMapper.inputComment(comment);
    }

    @Override
    public void deleteComments(String story_no) {
        boardMapper.deleteComments(story_no);
    }

    @Override
    public List<Map<String, String>> readComments(String story_no) {
        List<Map<String, String>> comments = boardMapper.readComments(story_no);
        return comments;
    }

    @Override
    public void plusViews(String story_no) {
        boardMapper.plusViews(story_no);
    }

    @Override
    public int getLikeIsClickedCnt(LikeVo likeVo) {
        return boardMapper.getLikeIsClickedCnt(likeVo);
    }

    @Override
    public void deleteClickedCnt(LikeVo likeVo) {
        boardMapper.deleteClickedCnt(likeVo);
    }

    @Override
    public void inputClickedCnt(LikeVo likeVo) {
        boardMapper.inputClickedCnt(likeVo);
    }

    @Override
    public PagingResponse<PostResponse> readStories(final SearchDto params) {
        int count = boardMapper.getStoriesCount(params);
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        List<PostResponse> list = boardMapper.readStories(params);

        return new PagingResponse<>(list, pagination);
    }

    @Override
    public Map<String, String> readStory(String story_no) {
        Map<String, String> story = boardMapper.readStory(story_no);
        return story;
    }
}

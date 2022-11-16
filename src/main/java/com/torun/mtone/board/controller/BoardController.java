package com.torun.mtone.board.controller;

import com.torun.mtone.board.service.BoardSvc;
import com.torun.mtone.board.vo.CommentVo;
import com.torun.mtone.board.vo.LikeVo;
import com.torun.mtone.board.vo.PostResponse;
import com.torun.mtone.board.vo.StoryVo;
import com.torun.mtone.common.SearchDto;
import com.torun.mtone.paging.PagingResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardSvc boardSvcImpl;

    @Autowired
    public BoardController(BoardSvc boardSvcImpl) {
        this.boardSvcImpl = boardSvcImpl;
    }

    @GetMapping("/stories")
    public String readStories(@ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<PostResponse> response = boardSvcImpl.readStories(params);
        model.addAttribute("response", response);
        return "board/stories";
    }

    // GET
    @GetMapping("/stories/{storyNo}")
    public String readStory(@PathVariable String storyNo, Model model, HttpServletRequest request, HttpServletResponse response) {

        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            Cookie newCookie = createNewCookie(storyNo);
            response.addCookie(newCookie);
            boardSvcImpl.plusViews(storyNo);
            return "board/story";
        }

        // 쿠키 존재함 (세션 포함)

        // 1. visit_cookie라는 이름을 가진 쿠키가 있는지 체크
        // 있다면 해당 쿠키의 값에 스토리 넘버가 있는지 체크
        // 있으면 return
        // 없으면 value에 스토리 넘버를 추가 -> 조회수 1 증가

        StoryVo story = boardSvcImpl.readStory(storyNo);

        for(Cookie cookie : cookies) {
            if("visit_cookie".equals(cookie.getName())) {
                // visit_cookie가 있으면
                if(!hasStoryNo(storyNo, cookie)) {
                    // visit_cookie 값에 storyNo가 없으면
                    setStoryNo(storyNo, cookie);
                    response.addCookie(cookie);
                    boardSvcImpl.plusViews(storyNo);
                }
                // visit_cookie 값에 storyNo가 있으면
                model.addAttribute("story", story);
                return "board/story";
            } else {
                // visit_cookie가 없으면
                Cookie visitCookie = createNewCookie(storyNo);
                response.addCookie(visitCookie);
                boardSvcImpl.plusViews(storyNo);
            }
        }

        model.addAttribute("story", story);

        return "board/story";
    }

    private static void setStoryNo(String storyNo, Cookie cookie) {
        cookie.setValue(cookie.getValue() + "_" + storyNo);
        cookie.setMaxAge(24*60*60);
    }

    private Cookie createNewCookie(String storyNo) {
        Cookie newCookie = new Cookie("visit_cookie", storyNo);
        newCookie.setMaxAge(24*60*60);
        return newCookie;
    }

    private boolean checkNeedToCreateVisitCookie(Cookie[] cookies, String storyNo) {
       return cookies == null;
    }

    private static boolean hasStoryNo(String storyNo, Cookie cookie) {
        return cookie.getValue().contains(storyNo);
    }

    // GET
    @GetMapping("/add")
    public String moveToAddForm(Model model) {
        model.addAttribute("story", new StoryVo());
        return "board/add";
    }

    // POST
    @PostMapping("/add")
    public String addStory(StoryVo vo) {
        boardSvcImpl.inputStory(vo);
        return "redirect:/board/stories";
    }

    // GET
    @GetMapping("/edit/{storyNo}")
    public ModelAndView moveToEditForm(@PathVariable String storyNo) {
        ModelAndView mv = new ModelAndView("board/edit");
        mv.addObject("story", boardSvcImpl.readStory(storyNo));
        return mv;
    }

    // PUT
    @PutMapping ("/edit/{storyNo}")
    public String editStory(StoryVo story, String storyNo) {
        boardSvcImpl.editStory(story);
        return "redirect:/board/stories/" + storyNo;
    }

    // DELETE
    @DeleteMapping("/delete/{storyNo}")
    public String deleteStory(@PathVariable String storyNo) {
        boardSvcImpl.deleteStory(storyNo);
        boardSvcImpl.deleteComments(storyNo);
        return "redirect:/board/stories";
    }

    // GET
    @ResponseBody
    @PostMapping("/comments")
    public List<Map<String, String>> comments(@RequestBody CommentVo commentVo) {
        List<Map<String, String>> data = boardSvcImpl.readComments(commentVo.getStory_no());
        return data;
    }

    // POST
    @ResponseBody
    @PostMapping("/reply")
    public Map<String, Object> addComment(@RequestBody HashMap comment) {
        boardSvcImpl.inputComment(comment);
        Map<String, Object> result = new HashMap<>();
        result.put("result", true);
        result.put("msg", "성공");
        return result;
    }

    @ResponseBody
    @PostMapping("/likes")
    public int getLikes(@RequestBody LikeVo likeVo) {
        return boardSvcImpl.getLikeIsClickedCnt(likeVo);
    }

    @ResponseBody
    @PostMapping("/likes/click")
    public int clickLike(@RequestBody LikeVo likeVo) {
        if(likeVo.getClickedCnt() > 0) {
            boardSvcImpl.deleteClickedCnt(likeVo);
        } else {
            boardSvcImpl.inputClickedCnt(likeVo);
        }
        return boardSvcImpl.getLikeIsClickedCnt(likeVo);
    }
}

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
    public ModelAndView readStory(@PathVariable String storyNo, HttpServletRequest request, HttpServletResponse response) {

        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();

        if(checkNeedToCreateVisitCookie(cookies, storyNo)) {
            Cookie newCookie = createNewCookie(storyNo);
            response.addCookie(newCookie);
            boardSvcImpl.plusViews(storyNo);
        } else {
            for(Cookie cookie : cookies) {
                if("visit_cookie".equals(cookie.getName())) {
                    if(!hasStoryNo(storyNo, cookie)) {
                        setStoryNo(storyNo, cookie);
                        response.addCookie(cookie);
                        boardSvcImpl.plusViews(storyNo);
                    }
                }
            }
        }

        ModelAndView mv = new ModelAndView("board/story");
        mv.addObject("story", boardSvcImpl.readStory(storyNo));

        return mv;
    }

    private static void setStoryNo(String story_no, Cookie cookie) {
        cookie.setValue(cookie.getValue() + "_" + story_no);
        cookie.setMaxAge(24*60*60);
    }

    private Cookie createNewCookie(String story_no) {
        Cookie newCookie = new Cookie("visit_cookie", story_no);
        newCookie.setMaxAge(24*60*60);
        return newCookie;
    }

    private boolean checkNeedToCreateVisitCookie(Cookie[] cookies, String story_no) {
       return cookies == null;
    }

    private static boolean hasStoryNo(String story_no, Cookie cookie) {
        return cookie.getValue().contains(story_no);
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

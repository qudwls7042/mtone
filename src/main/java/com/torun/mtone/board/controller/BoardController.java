package com.torun.mtone.board.controller;

import com.torun.mtone.board.service.BoardSvc;
import com.torun.mtone.board.vo.CommentVo;
import com.torun.mtone.board.vo.StoryVo;
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

    // GET
    @GetMapping("/stories")
    public ModelAndView readStories() {
        ModelAndView mv = new ModelAndView("board/stories");
        mv.addObject("stories", boardSvcImpl.readStories());
        return mv;
    }

    // GET
    @GetMapping("/stories/{story_no}")
    public ModelAndView readStory(@PathVariable String story_no, HttpServletRequest request, HttpServletResponse response) {

        /* 조회수 로직 */
        Cookie[] cookies = request.getCookies();

        if(checkNeedToCreateVisitCookie(cookies)) {
            Cookie newCookie = createNewCookie(story_no);
            response.addCookie(newCookie);
            boardSvcImpl.plusViews(story_no);
        }

        ModelAndView mv = new ModelAndView("board/story");
        mv.addObject("story", boardSvcImpl.readStory(story_no));

        return mv;
    }

    private Cookie createNewCookie(String story_no) {
        Cookie newCookie = new Cookie("visit_cookie", story_no);
        newCookie.setMaxAge(24*60*60);
        return newCookie;
    }
    private boolean checkNeedToCreateVisitCookie(Cookie[] cookies) {
        // 쿠키가 null이면 만들어야함
        // 쿠키가 null이 아닌데 visit_cookie가 있으면 안만들어도됨
        // 쿠키가 null이 아닌데 visit_cookie가 없으면 만들어야함
        boolean needToCreateCookie = true;

        if(cookies != null) {
            int visitCookieCnt = 0;
            for(Cookie cookie : cookies) {
                if("visit_cookie".equals(cookie.getName())) {
                    visitCookieCnt++;
                }
            }
            if(visitCookieCnt == 1) {
                needToCreateCookie = false;
            }
        }

        return needToCreateCookie;
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
    @GetMapping("/edit/{story_no}")
    public ModelAndView moveToEditForm(@PathVariable String story_no) {
        ModelAndView mv = new ModelAndView("board/edit");
        mv.addObject("story", boardSvcImpl.readStory(story_no));
        return mv;
    }

    // PUT
    @PutMapping ("/edit/{story_no}")
    public String editStory(StoryVo story, String story_no) {
        boardSvcImpl.editStory(story);
        return "redirect:/board/stories/" + story_no;
    }

    // DELETE
    @DeleteMapping("/delete/{story_no}")
    public String deleteStory(@PathVariable String story_no) {
        boardSvcImpl.deleteStory(story_no);
        boardSvcImpl.deleteComments(story_no);
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
}

package com.torun.mtone.board.controller;

import com.torun.mtone.board.service.BoardSvc;
import com.torun.mtone.board.vo.CommentVo;
import com.torun.mtone.board.vo.StoryVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView readStories() {
        ModelAndView mv = new ModelAndView("board/stories");
        mv.addObject("stories", boardSvcImpl.readStories());
        return mv;
    }

    @GetMapping("/stories/{story_no}")
    public ModelAndView readStory(@PathVariable String story_no) {
        ModelAndView mv = new ModelAndView("board/story");
        mv.addObject("story", boardSvcImpl.readStory(story_no));
        return mv;
    }

    @GetMapping("/add")
    public String moveToAddForm(Model model) {
        model.addAttribute("story", new StoryVo());
        return "board/add";
    }

    @PostMapping("/add")
    public String addStory(StoryVo vo) {
        boardSvcImpl.inputStory(vo);
        return "redirect:/board/stories";
    }

    @GetMapping("/edit/{story_no}")
    public ModelAndView moveToEditForm(@PathVariable String story_no) {
        ModelAndView mv = new ModelAndView("board/edit");
        mv.addObject("story", boardSvcImpl.readStory(story_no));
        return mv;
    }

    @PostMapping("/edit")
    public String editStory(StoryVo story) {
        boardSvcImpl.editStory(story);
        return "redirect:/board/stories/" + story.getStory_no();
    }

    @PostMapping("/delete")
    public String deleteStory(String story_no) {
        boardSvcImpl.deleteStory(story_no);
        boardSvcImpl.deleteComments(story_no);
        return "redirect:/board/stories";
    }

    @ResponseBody
    @PostMapping("/comments")
    public List<Map<String, String>> comments(@RequestBody CommentVo commentVo) {
        List<Map<String, String>> data = boardSvcImpl.readComments(commentVo.getStory_no());
        return data;
    }

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

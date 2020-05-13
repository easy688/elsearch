package com.learn.elsearch.controller;

import com.learn.elsearch.service.JoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author liyuhui
 */
@Controller
public class JoupController {
    @Autowired
    private JoupService joupService;

    @GetMapping("/{keyWord}")
    @ResponseBody
    public List<Map<String,String>> getJoup(@PathVariable("keyWord") String keyWord)throws IOException {
        return joupService.getJoup(keyWord);
    }
    @GetMapping("/search/{keyWord}")
    @ResponseBody
    public List<Map<String,String>> search(@PathVariable("keyWord") String keyWord)throws IOException {
        return joupService.search(keyWord);
    }
}

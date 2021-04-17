package ru.koshkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/testSpring")
public class TestController {

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping
    private String getTest(Model model) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("Lorem", "ipsum", "dolor", "sit", "amet"));
        model.addAttribute("arr", list);
        return "test";
    }

}

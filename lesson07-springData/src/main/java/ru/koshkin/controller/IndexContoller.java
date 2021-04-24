package ru.koshkin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexContoller {

    private final static Logger logger = LoggerFactory.getLogger(IndexContoller.class);

    @GetMapping
    private String getIndexMap(Model model) {
        return "index";
    }

}

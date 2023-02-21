package com.ruoyi.web.controller.topic;

import com.ruoyi.system.service.ITitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic/title")
public class titleController {

    @Autowired
    private ITitleService titleService;
}

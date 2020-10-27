package com.intertek.demo.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:31
 */
@Controller
public class TestController {

    @RequestMapping("/hello.html")
    public @ResponseBody String hello() {
        return "hello spring boot" + "！ 你好，Spring Boot！";
    }
}

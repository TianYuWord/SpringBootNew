package cn.tianyu20.modlue1.modlue1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${xiaoyu.msg}")
    private String msg;

    @GetMapping("/msg")
    public String getMsg() {
        return msg;
    }

    @GetMapping("/index.html")
    public String index(){
        return "hello Spring";
    }

    @GetMapping("/log")
    public void log(){
        logger.trace("-----------trace-----------");
        logger.debug("-----------debug-----------");
        logger.info("-----------info-----------");
        logger.warn("-----------warn-----------");
        logger.error("-----------error-----------");
    }
}
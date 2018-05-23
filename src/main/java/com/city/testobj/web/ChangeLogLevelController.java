package com.city.testobj.web;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.city.testobj.util.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by City Mo on 2018/5/23.
 */
@RequestMapping("/log")
@RestController
public class ChangeLogLevelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLogLevelController.class);

    @RequestMapping("/change")
    public ApiResponse changeLog(String className,String level){

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(className).setLevel(Level.valueOf(level));

        return ApiResponse.success("hai");
    }

    @RequestMapping("/out")
    public ApiResponse out(){
        LOGGER.info("info now :{}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        LOGGER.error("error now :{}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        LOGGER.debug("debug now :{}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        LOGGER.trace("trace now :{}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        return ApiResponse.success("hai");
    }
}

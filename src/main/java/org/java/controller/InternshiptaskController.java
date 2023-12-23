package org.java.controller;

import lombok.extern.slf4j.Slf4j;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.pojo.User;
import org.java.mapper.ConTaskMapper;
import org.java.service.InternshiptaskService;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@Slf4j
public class InternshiptaskController {
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private ConTaskMapper conTaskMapper;

//    @GetMapping("/getInfo")
//    public Result<Internshiptask> getInfo() {
//        return internshiptaskService
//    }

    @GetMapping("/getsymble")
    public Result<Internshiptask> getsymble(String courseCategory,String academicTerm,String className) {
        // 根据课程类别和课程学期,查询
        Internshiptask internshiptask = internshiptaskService.getSymbol(courseCategory, academicTerm, className);
        return Result.success(internshiptask);
    }

    @PostMapping("/addInfo")
    public Result addInfo(@RequestBody Internshiptask internshiptask) {
        if (internshiptaskService.addTask(internshiptask)){
            return  Result.success();
        }else {
            return Result.error("操作失败");
        }
    }
}

package org.java.controller;

import lombok.extern.slf4j.Slf4j;
import org.java.entity.dto.InternShipTaskDto;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.pojo.User;
import org.java.mapper.ConTaskMapper;
import org.java.service.InternshiptaskService;
import org.java.utils.resonse.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@Slf4j
public class InternshiptaskController {
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private ConTaskMapper conTaskMapper;

    @GetMapping("/getTasks")
    public Result<List<Internshiptask>> getTasks(@RequestParam Map<String ,Integer> map) {
        Integer pageNumber = map.get("pageNumber");
        Integer pageSize = map.get("pageSize");
        return Result.success(internshiptaskService.getTasks(pageNumber,pageSize));
    }

    @GetMapping("/getsymble")
    public Result<Internshiptask> getsymble(String courseCategory,String academicTerm,String className) {
        // 根据课程类别和课程学期,查询
        Internshiptask internshiptask = internshiptaskService.getSymbol(courseCategory, academicTerm, className);
        return Result.success(internshiptask);
    }

    @PostMapping("/addTask")
    public Result addInfo(@RequestBody InternShipTaskDto internShipTaskDto) {
        Internshiptask internshiptask = new Internshiptask();
        // 类复制
        BeanUtils.copyProperties(internshiptask,internShipTaskDto);
        if (internshiptaskService.addTask(internshiptask)){
            return  Result.success();
        }else {
            return Result.error("操作失败");
        }
    }
}

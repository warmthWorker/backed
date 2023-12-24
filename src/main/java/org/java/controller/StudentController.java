package org.java.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.java.entity.dto.CheckInDto;
import org.java.entity.pojo.CheckIn;
import org.java.entity.pojo.Student;
import org.java.entity.vo.ApplyTaskVo;
import org.java.entity.vo.CheckInVo;
import org.java.mapper.CheckInMapper;
import org.java.mapper.StudentMapper;
import org.java.service.CheckInService;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private CheckInService checkInService;
    @Autowired
    private StudentMapper studentMapper;


    // 获取某班所有学生
    @GetMapping("/getAllStuByClass")
    public Result<List<Student>> getAllStuByClass(String className){
        List<Student> studentList = studentMapper.selectList(new QueryWrapper<Student>()
                .eq("class_name", className));
        log.info("获取某班所有学生",studentList);
        return Result.success(studentList);
    }
    // 根据学生学号获取学生信息
    @GetMapping("/getStuById")
    public Result<Student> getStuById(String id){
        Student student = studentMapper.selectOne(new QueryWrapper<Student>()
                .eq("id", id));
        log.info("根据学生学号获取学生信息",student);
        return Result.success(student);
    }
    // 打卡操作
    @PostMapping("/checkIn")
    public Result checkIn(@RequestBody CheckInDto checkInDto){
        log.info("打卡操作",checkInDto);
       return Result.success(checkInService.checkInOption(checkInDto));
    }
    //  查询某班某天打卡记录

    // 查询某学生某天打卡记录
    @GetMapping("/getInfoById")
    public Result<ArrayList<CheckInVo>> getInfoByOne(@RequestParam Map<String, String> map) throws ParseException {
        log.info("查询某学生某天打卡记录",map);
        String stuId = map.get("stuId");
        String date = map.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strDate = dateFormat.parse(date);
        return Result.success(checkInService.getStudentCheckInRecords(stuId, strDate));
    }
    /**
     *
     * @param map
     * @return
     */
}

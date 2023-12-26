package org.java.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.java.entity.SecurityUser;
import org.java.entity.dto.CheckInDto;
import org.java.entity.dto.CheckInStudentsDto;
import org.java.entity.dto.InputScoreDto;
import org.java.entity.pojo.*;
import org.java.entity.vo.ApplyTaskVo;
import org.java.entity.vo.CheckInStudentVo;
import org.java.entity.vo.CheckInVo;
import org.java.entity.vo.EndTimeTaskVo;
import org.java.mapper.CheckInMapper;
import org.java.mapper.ScoreMapper;
import org.java.mapper.StudentMapper;
import org.java.service.CheckInService;
import org.java.service.InternshiptaskService;
import org.java.service.ScoreService;
import org.java.service.TeaTaskService;
import org.java.utils.resonse.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private TeaTaskService teaTaskService;


    /**
     * 获取某班所有学生
     * @param map
     * @return
     */
    @GetMapping("/getAllStuByClass")
    public Result<PageInfo<Student>> getAllStuByClass(@RequestParam Map<String, String> map){
//         开启分页PageNumber PageSize
        PageHelper.startPage(Integer.parseInt(map.get("pageNumber")),
                            Integer.parseInt(map.get("pageSize")));
        if (!map.get("stuName").isEmpty()){
            List<Student> studentList = studentMapper.selectList(new QueryWrapper<Student>()
                    .eq("class_name", map.get("className"))
                    .like("name", map.get("stuName")));
            return Result.success(new PageInfo<>(studentList));
        }
        List<Student> studentList = studentMapper.selectList(new QueryWrapper<Student>()
                .eq("class_name", map.get("className")));
        log.info("获取某班所有学生{}",studentList);
        return Result.success(new PageInfo<>(studentList));
    }

    /**
     * 打卡操作
     * @param checkInDto
     * @return
     */
    @PostMapping("/checkIn")
    public Result checkIn(@RequestBody CheckInDto checkInDto){
        log.info("打卡操作{}",checkInDto);
        if (checkInService.checkInOption(checkInDto)>0){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    //获取某班某天的未打卡人信息

    /**
     * 获取某班某天的未打卡人信息
     * @param checkInStudentsDto
     * @return
     */
    @PostMapping("/getNoCheckInStudents")
    public Result<PageInfo<CheckInStudentVo>> getNoCheckInStudents(@RequestBody CheckInStudentsDto checkInStudentsDto){
        log.info("获取某班某天的未打卡人信息{}",checkInStudentsDto);

        return Result.success(checkInService.getNoCheckedInStudents
                (checkInStudentsDto));

    }
    // 获取某班某天以打卡人信息

    /**
     * 获取某班某天以打卡人信息
     * @param checkInStudentsDto
     * @return
     */
    @PostMapping("/getCheckInStudents")
    public Result<PageInfo<CheckInStudentVo>>  getCheckInStudents(@RequestBody CheckInStudentsDto checkInStudentsDto){
        log.info("获取某班某天以打卡人信息{}",checkInStudentsDto);
        return Result.success(checkInService.getCheckedInStudents
                (checkInStudentsDto));
    }
    /**
     * 查询某学生某天打卡记录
     * @param map
     * @return
     * @throws ParseException
     */
    @GetMapping("/getInfoById")
    public Result<ArrayList<CheckInVo>> getInfoByOne(@RequestParam Map<String, String> map) throws ParseException {
        log.info("查询某学生某天打卡记录{}",map);
        String stuId = map.get("stuId");
        String date = map.get("date");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date strDate = dateFormat.parse(date);
        return Result.success(checkInService.getStudentCheckInRecords(stuId, strDate));
    }
    /**
     * 老师输入学生成绩
     * @param inputScoreDto
     * @return
     */
    @PostMapping("/inputMajor")
    public Result inputMajor(@RequestBody InputScoreDto inputScoreDto){
        log.info("老师输入学生成绩{}",inputScoreDto);
        // 当任务课程还没有结束时，不能输入专业成绩
        Internshiptask task = internshiptaskService.getOne(new QueryWrapper<Internshiptask>()
                .eq("task_id", inputScoreDto.getTaskId()));
        if (task.getStatus()!= 2){
            return Result.error("任务未结束，不可填写专业成绩");
        }
        // 构建更新的 Score 对象
        Score score = new Score();
        score.setStuId(inputScoreDto.getStuId());
        score.setMajor(inputScoreDto.getMajorScore());
        // 构建更新条件
        UpdateWrapper<Score> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", inputScoreDto.getStuId());

        // 执行更新操作
        int updateResult = scoreMapper.update(score, updateWrapper);
        if (updateResult >0) {
            return Result.success("学生成绩更新成功");
        } else {
            return Result.error("学生成绩更新失败");
        }
    }

    // 获取学生总成绩

    /**
     * 获取学生总成绩
     * @param map
     * @return
     */
    @GetMapping("/getTotalScore")
    public Result<Map<String, Double>> getTotalScore(@RequestParam Map<String, String> map){
        log.info("获取学生总成绩{}",map);
        String stuId = map.get("stuId");
        String taskId = map.get("taskId");
        if (stuId.isEmpty() || taskId.isEmpty()){
            return Result.error("输入数据不完整");
        }
        Map<String, Double> doubleMap = scoreService.getStuTotalScore(Integer.parseInt(stuId), Integer.parseInt(taskId));
        log.info("总成绩：{}",doubleMap);
        return Result.success(doubleMap);
    }

}

package org.java.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.java.entity.SecurityUser;
import org.java.entity.dto.InternShipTaskDto;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.pojo.TeaTask;
import org.java.entity.pojo.User;
import org.java.entity.vo.ApplyTaskVo;
import org.java.entity.vo.HistoryTaskVo;
import org.java.entity.vo.TeaTaskVo;
import org.java.mapper.ConTaskMapper;
import org.java.mapper.UserMapper;
import org.java.service.ConTaskService;
import org.java.service.InternshiptaskService;
import org.java.service.TeaTaskService;
import org.java.utils.resonse.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@Slf4j

//@Tag(name = "任务相关")
//@Api("任务相关")
//@Api("实训任务相关")
public class InternshiptaskController {
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private TeaTaskService teaTaskService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getTasks")
//    @ApiOperation("获取所有实训任务")
//    @Operation(
//            summary = "获取所有实训任务",
//            description = "获取所有实训任务")
    public Result<List<ApplyTaskVo>> getTasks(@RequestParam Map<String, String> map) {

        log.info("获取数据：{}",map);
        Integer pageNumber = Integer.valueOf(map.get("pageNumber"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        log.info("获取所有实训任务");
        return Result.success(internshiptaskService.getTasks(pageNumber, pageSize));
//        return null;
    }

    @GetMapping("/getTasksByTerm")
    public Result<List<ApplyTaskVo>> getTasksByTerm(@RequestParam Map<String, String> map) {
        log.info("获取当前学期所有实习任务",map);
        int pageNumber = Integer.parseInt(map.get("pageNumber"));
        int pageSize = Integer.parseInt(map.get("pageSize"));
        int academicTerm = Integer.parseInt(map.get("academicTerm"));
        String courseName = map.get("courseName");
        return Result.success(internshiptaskService.getTasksByTerm(courseName,academicTerm,pageNumber, pageSize));
    }

    @GetMapping("/getsymble")
    public Result<Internshiptask> getsymble(@RequestParam Map<String, String> map) {
        log.info("获取关联实习任务", map);
        String courseCategory = map.get("courseCategory");
        int academicTerm = Integer.parseInt(map.get("academicTerm"));
        String courseName = map.get("courseName");
        // 根据课程类别和课程学期,查询
        Internshiptask internshiptask = internshiptaskService.getSymbol(courseCategory, academicTerm, courseName);
        return Result.success(internshiptask);
    }

    @PostMapping("/addTask")
    public Result addInfo(@RequestBody InternShipTaskDto internShipTaskDto) {
        log.info("添加任务", internShipTaskDto);
        Internshiptask internshiptask = new Internshiptask();
        // 类复制
        BeanUtils.copyProperties(internShipTaskDto, internshiptask);
        if (internshiptaskService.addTask(internshiptask)) {
            return Result.success();
        } else {
            return Result.error("操作失败");
        }
    }

    /**
     * 教师报名
     * @param taskId
     * @return
     */
    @GetMapping("/applyTaskTea")
    public Result applyTaskTea(Integer taskId) {
        log.info("教师报名{}",taskId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        TeaTask teaTask = new TeaTask();
        teaTask.setMark(0); // 未通过
        teaTask.setTaskId(taskId);
        teaTask.setUserId(user.getId());
        teaTask.setTime(new Date());
        teaTask.setAcademicTerm(internshiptaskService.getTaskTermByTaskId(taskId));
        if (teaTaskService.applyTask(teaTask)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    /**
     * 获取需要通过报名的记录
     */
    @GetMapping("/getApplyInfo")
    public Result<List<TeaTaskVo>> getApplyInfo(){
        return Result.success(teaTaskService.findApplyInfo());
    }

    /**
     * 管理员通过审核
     * @param teaTaskId
     * @return
     */
    @GetMapping("/applyTaskSys")
    public Result applyTaskSys(Integer teaTaskId) {
        log.info("管理员通过审核{}",teaTaskId);
        TeaTask teaTaskById = teaTaskService.getTeaTaskById(teaTaskId);
        teaTaskById.setMark(1); //系统教师
        if (teaTaskService.applyTask(teaTaskById)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    /**
     * 系统教师加入其他老师
     * @param map
     * @return
     */
    @GetMapping("/intoTask")
    public Result intoTask(@RequestParam Map<String, Integer> map) {
        log.info("系统教师加入其他老师" ,map);
        Integer taskId = map.get("taskId");
        Integer teaId = map.get("teaId");

        TeaTask teaTask = new TeaTask();
        teaTask.setMark(2); // 其他教师
        teaTask.setTaskId(taskId);
        teaTask.setUserId(teaId);
        teaTask.setTime(new Date());
        if (teaTaskService.intoTask(teaTask)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    /**
     * 教师历史实习任务任教情况
     * @return
     */
    @GetMapping("/getHistory")
    public Result<List<HistoryTaskVo>>  getHistoryTask(){
        List<HistoryTaskVo> historyTask = teaTaskService.findHistoryTask();
        log.info("教师历史实习任务任教情况",historyTask);
        return Result.success(historyTask);
    }

    /**
     * 根据姓名查询历史实习任务任教情况
     * @return
     */
    @GetMapping("/getHistoryByName")
    public Result<List<HistoryTaskVo>> getHistoryByName(String username){
        List<HistoryTaskVo> historyByName = teaTaskService.getHistoryByName(username);
        log.info("根据姓名查询历史实习任务任教情况",historyByName);
        return Result.success(historyByName);
    }


    /**
     * 模糊搜索教师名称
     * @return
     */
    @GetMapping("/findByName")
    public Result<List<User>> findUserByName(String username){
        log.info("模糊搜索教师名称",username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);

        List<User> userList = userMapper.selectList(queryWrapper);
        return Result.success(userList);
    }

}

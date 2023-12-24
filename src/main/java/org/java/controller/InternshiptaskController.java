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

public class InternshiptaskController {
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private TeaTaskService teaTaskService;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/getTasksByTerm")
    public Result<List<ApplyTaskVo>> getTasksByTerm(@RequestParam Map<String, String> map) {
        log.info("获取当前学期所有实习任务{}",map);

        String  pageNumber = map.get("pageNumber");
        String pageSize = map.get("pageSize");
        String academicTerm= map.get("academicTerm");
        String courseName = map.get("courseName");
        int parseInt ;
        int pareSize ;
        int academicTermI ;
        if (pageNumber.isEmpty() || pageSize.isEmpty() || academicTerm.isEmpty()){
            return Result.error("输入数据不完整");
        }
        parseInt  = Integer.parseInt(pageNumber);
        pareSize = Integer.parseInt(pageSize);
        academicTermI = Integer.parseInt(academicTerm);
//        System.out.println(parseInt);
        return Result.success(internshiptaskService.getTasksByTerm(courseName,academicTermI,parseInt, pareSize));
    }

    @GetMapping("/getsymble")
    public Result<Internshiptask> getsymble(@RequestParam Map<String, String> map) {
        log.info("获取关联实习任务", map);
        String courseCategory = map.get("courseCategory");
        String academicTerm = map.get("academicTerm");
        String courseName = map.get("courseName");
        if (courseCategory.isEmpty() || academicTerm.isEmpty() || courseName.isEmpty()){
            return Result.error("输入数据不完整");
        }
        Integer IacademicTerm = Integer.parseInt(academicTerm);
        // 根据课程类别和课程学期,查询
        Internshiptask internshiptask = internshiptaskService.getSymbol(courseCategory, IacademicTerm, courseName);
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
        log.info("获取需要通过报名的记录");
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
    public Result intoTask(@RequestParam Map<String, String> map) {
        log.info("系统教师加入其他老师" ,map);
        String taskId = map.get("taskId");
        String teaId = map.get("teaId");
        int taskIdI ;
        int teaIdI ;

        if (taskId.isEmpty() || teaId.isEmpty()){
            return Result.error("输入数据不完整");
        }
        taskIdI = Integer.parseInt(taskId);
        teaIdI = Integer.parseInt(teaId);
        TeaTask teaTask = new TeaTask();
        teaTask.setMark(2); // 其他教师
        teaTask.setTaskId(taskIdI);
        teaTask.setUserId(teaIdI);
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

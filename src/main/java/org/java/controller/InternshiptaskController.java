package org.java.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.java.entity.SecurityUser;
import org.java.entity.dto.InternShipTaskDto;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.pojo.TeaTask;
import org.java.entity.pojo.User;
import org.java.entity.vo.ApplyTaskVo;
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
    public Result<List<ApplyTaskVo>> getTasks(@RequestParam Map<String, Integer> map) {
        Integer pageNumber = map.get("pageNumber");
        Integer pageSize = map.get("pageSize");
        return Result.success(internshiptaskService.getTasks(pageNumber, pageSize));
    }

    @GetMapping("/getsymble")
    public Result<Internshiptask> getsymble(@RequestParam Map<String, String> map) {
        log.info("获取全部", map);
        String courseCategory = map.get("courseCategory");
        String academicTerm = map.get("academicTerm");
        String className = map.get("className");
        // 根据课程类别和课程学期,查询
        Internshiptask internshiptask = internshiptaskService.getSymbol(courseCategory, academicTerm, className);
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
     * 报名
     *
     * @param taskId
     * @return
     */
    @GetMapping("/applyTask")
    public Result applyTask(Integer taskId) {
        log.info("报名",taskId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        TeaTask teaTask = new TeaTask();
        teaTask.setMark(1); // 系统教师
        teaTask.setTaskId(taskId);
        teaTask.setUserId(user.getId());
        teaTask.setDate(new Date(String.valueOf(new SimpleDateFormat("yyyy-MM-dd"))));
        if (teaTaskService.applyTask(teaTask)){
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
        teaTask.setDate(new Date(String.valueOf(new SimpleDateFormat("yyyy-MM-dd"))));
        if (teaTaskService.intoTask(teaTask)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    /**
     * 教师历史实习任务任教情况
     * @return
     */
//    @GetMapping("/getHistory")
//    public


    /**
     * 模糊搜索教师名称
     * @return
     */
    @GetMapping("/findByName")
    public Result<List<User>> findUserByName(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);

        List<User> userList = userMapper.selectList(queryWrapper);
        return Result.success(userList);
    }

}

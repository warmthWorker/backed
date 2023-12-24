package org.java.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.SecurityUser;
import org.java.entity.pojo.ConTask;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.pojo.TeaTask;
import org.java.entity.pojo.User;
import org.java.entity.vo.ApplyTaskVo;
import org.java.mapper.ConTaskMapper;
import org.java.mapper.InternshiptaskMapper;
import org.java.mapper.TeaTaskMapper;
import org.java.service.InternshiptaskService;
import org.java.service.TeaTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author 程子
* @description 针对表【internshiptask】的数据库操作Service实现
* @createDate 2023-12-23 11:09:42
*/
@Service
public class InternshiptaskServiceImpl extends ServiceImpl<InternshiptaskMapper, Internshiptask>
    implements InternshiptaskService{
    @Autowired
    private InternshiptaskMapper mapper;
    @Autowired
    private ConTaskMapper conTaskMapper;
    @Autowired
    private TeaTaskMapper teaTaskMapper;
    @Override
    public boolean addTask(Internshiptask internshiptask){
        if (mapper.insert(internshiptask) > 0){
            // 进行关联
            // 如果courseCategory和courseName都相同，则代表可进行关联
            ConTask conTask = conTaskMapper.selectOne(new QueryWrapper<ConTask>()
                    .eq("course_category", internshiptask.getCourseCategory())
                    .eq("course_name", internshiptask.getCourseName()));
            if (conTask!=null){
                // 上个学年，学期字段相差11
                if (internshiptask.getAcademicTerm()==conTask.getTerm()+11){
                    // 如果是相差一学年
                    conTask.setLTaskId(conTask.getNTaskId());
                    conTask.setNTaskId(internshiptask.getAcademicTerm());
                }else {
                    // 更新关联表
                    conTask.setNTaskId(internshiptask.getTaskId());
                    conTask.setLTaskId(null);
                }
                conTaskMapper.insert(conTask);
            }else {
                //conTask为空，生成一个
                ConTask conTask1 = new ConTask();
                conTask1.setTerm(internshiptask.getAcademicTerm());
                conTask1.setNTaskId(internshiptask.getTaskId());
                conTask1.setCourseCategory(internshiptask.getCourseCategory());
                conTask1.setCourseName(internshiptask.getCourseName());
                conTaskMapper.insert(conTask1);
            }
            return true;
        }
        return false;
    }

    /**
     * 查询所有学期
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ApplyTaskVo> getTasks(int pageNumber,int pageSize){
        List<Internshiptask> selectList = mapper.selectList(new QueryWrapper<>());

        List<ApplyTaskVo> applyTaskVos = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        //查询单个老师所有学期的选课
        List<TeaTask> teaTaskList = teaTaskMapper.selectList(new QueryWrapper<TeaTask>()
                .eq("user_id", user.getId()));

        for (Internshiptask internshiptask : selectList) {
            // todo 不展示已经选过的

            ApplyTaskVo applyTaskVo = new ApplyTaskVo();
            BeanUtils.copyProperties(applyTaskVo, internshiptask);
        }
        return getPage(applyTaskVos,pageNumber,pageSize);
    }

    /**
     * 查询当前学期
     * @param academicTerm
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ApplyTaskVo> getTasksByTerm(int academicTerm, int pageNumber, int pageSize) {
        //查询当前学期的任务
        List<Internshiptask> selectList = mapper.selectList(new QueryWrapper<Internshiptask>()
                .eq("academic_term",academicTerm));

        List<ApplyTaskVo> applyTaskVos = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        //查询单个老师一个学期的选课
        List<TeaTask> teaTaskList = teaTaskMapper.selectList(new QueryWrapper<TeaTask>()
                .eq("user_id", user.getId())
                .eq("academic_term",academicTerm));

        for (Internshiptask internshiptask : selectList) {
            // todo 不展示已经选过的
            for (TeaTask teaTask : teaTaskList) {
                // 如果在本学期老师id和任务id都相同,则表示已经选过
                if (Objects.equals(user.getId(), teaTask.getUserId()) &&
                        teaTask.getTaskId().equals(internshiptask.getTaskId())){
                    break;
                }
            }
            ApplyTaskVo applyTaskVo = new ApplyTaskVo();
            BeanUtils.copyProperties(applyTaskVo, internshiptask);
        }
        return getPage(applyTaskVos,pageNumber,pageSize);
    }

    @Override
    public Internshiptask getSymbol(String courseCategory,String academicTerm,String className){
        Integer taskId = conTaskMapper.findCon(courseCategory, className);
        QueryWrapper<Internshiptask> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Internshiptask> taskId1 = queryWrapper.eq("task_id", taskId);
        return mapper.selectOne(taskId1);
    }

    /**
     * 分页
     * @param tasks
     * @param pageNumber 当前页
     * @param pageSize 每页大小
     * @return
     */
    public static List<ApplyTaskVo> getPage(List<ApplyTaskVo> tasks, int pageNumber, int pageSize) {
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min((pageNumber + 1) * pageSize, tasks.size());

        if (fromIndex > toIndex || fromIndex >= tasks.size()) {
            // 页数超出范围，返回空列表或适当处理
            return List.of();
        }
        return tasks.subList(fromIndex, toIndex);
    }
}





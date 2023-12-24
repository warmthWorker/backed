package org.java.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 程子
 * @description 针对表【internshiptask】的数据库操作Service实现
 * @createDate 2023-12-23 11:09:42
 */
@Service
@Slf4j
public class InternshiptaskServiceImpl extends ServiceImpl<InternshiptaskMapper, Internshiptask>
        implements InternshiptaskService {
    @Autowired
    private InternshiptaskMapper mapper;
    @Autowired
    private ConTaskMapper conTaskMapper;
    @Autowired
    private TeaTaskMapper teaTaskMapper;

    @Override
    public Integer getTaskTermByTaskId(Integer taskId) {
        return mapper.selectById(taskId).getAcademicTerm();
    }

    @Transactional
    @Override
    public boolean addTask(Internshiptask internshiptask) {
        if (mapper.insert(internshiptask) > 0) {
            // 进行关联
            // 如果courseCategory和courseName都相同，则代表可进行关联
            log.info("如果courseCategory和courseName都相同，则代表可进行关联");
            ConTask conTask = conTaskMapper.selectOne(new QueryWrapper<ConTask>()
                    .eq("course_category", internshiptask.getCourseCategory())
                    .eq("course_name", internshiptask.getCourseName()));
            if (conTask != null) {
                // 上个学年，学期字段相差10
                if (internshiptask.getAcademicTerm() == conTask.getTerm() + 10) {
                    // 如果是相差一学年
                    log.info("相差一学年");
                    conTask.setLTaskId(conTask.getNTaskId());
                    conTask.setNTaskId(internshiptask.getTaskId());
                } else {
                    // 更新关联表
                    log.info("更新关联表");
                    conTask.setNTaskId(internshiptask.getTaskId());
                    conTask.setLTaskId(null);
                }
                conTaskMapper.updateById(conTask);
            } else {
                //conTask为空，生成一个
                log.info("conTask为空，生成一个");
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
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ApplyTaskVo> getTasks(int pageNumber, int pageSize) {
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
        return getPage(applyTaskVos, pageNumber, pageSize);
    }

    /**
     * 查询当前学期
     *
     * @param academicTerm
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<ApplyTaskVo> getTasksByTerm(String courseName,int academicTerm, int pageNumber, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNumber, pageSize);
        List<ApplyTaskVo> applyTaskVos = new ArrayList<>();
        if (courseName != null || courseName.isEmpty() || courseName.equals("undefined")){
            //先给模糊查询
            List<Internshiptask> course_names = mapper.selectList(new QueryWrapper<Internshiptask>()
                    .like("course_name", courseName));
            for (Internshiptask course_name : course_names) {
                ApplyTaskVo applyTaskVo = new ApplyTaskVo();
                BeanUtils.copyProperties(course_name, applyTaskVo);
                applyTaskVos.add(applyTaskVo);
            }
            // 获取分页信息
            PageInfo<ApplyTaskVo> pageInfo = new PageInfo<>(applyTaskVos);
            return pageInfo.getList();
        }
        //查询当前学期的任务
        List<Internshiptask> selectList = mapper.selectList(new QueryWrapper<Internshiptask>()
                .eq("academic_term", academicTerm));
        log.info("查询当前学期的任务{}", selectList);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = securityUser.getUser();
        log.info("用户{}", user);
        //查询单个老师一个学期的选课
        List<TeaTask> teaTaskList = teaTaskMapper.selectList(new QueryWrapper<TeaTask>()
                .eq("user_id", user.getId())
                .eq("academic_term", academicTerm));
        log.info("查询单个老师一个学期的选课:{}", teaTaskList);
        for (Internshiptask internshiptask : selectList) {
            // todo 不展示已经选过的
            for (TeaTask teaTask : teaTaskList) {
                // 如果在本学期老师id和任务id都相同,则表示已经选过
                if (Objects.equals(user.getId(), teaTask.getUserId()) &&
                        teaTask.getTaskId().equals(internshiptask.getTaskId())) {
                    break;
                }
            }
            ApplyTaskVo applyTaskVo = new ApplyTaskVo();
            BeanUtils.copyProperties(internshiptask, applyTaskVo);
            applyTaskVos.add(applyTaskVo);
        }
        log.info("applyTaskVos{}", applyTaskVos);
        // 获取分页信息
        PageInfo<ApplyTaskVo> pageInfo = new PageInfo<>(applyTaskVos);
        // 返回分页结果
        return pageInfo.getList();
    }

    @Override
    public Internshiptask getSymbol(String courseCategory, Integer academicTerm, String course_name) {

        List<ConTask> conTaskList = conTaskMapper.selectList(new QueryWrapper<ConTask>()
                .eq("course_category", courseCategory)
                .eq("course_name", course_name));
        log.info("获取同类型同名的");
        QueryWrapper<Internshiptask> queryWrapper = new QueryWrapper<>();
        for (ConTask conTask : conTaskList) {
            // 上个学年，学期字段相差10
            if (academicTerm == conTask.getTerm() + 10) {
                log.info("找到符合条件的conTask：{}",conTask);
                queryWrapper.eq("task_id", conTask.getNTaskId());
                return mapper.selectOne(queryWrapper);
            }
        }
        // 如果没有符合的，就进行匹配
        Integer taskId = conTaskMapper.findCon(courseCategory, course_name);
        if (taskId!= null){
            log.info("没有符合的，就进行匹配：{}",taskId);
            return mapper.selectById(taskId);
        }
        return null;
    }

    /**
     * 分页
     *
     * @param tasks
     * @param pageNumber 当前页
     * @param pageSize   每页大小
     * @return
     */
    public static List<ApplyTaskVo> getPage(List<ApplyTaskVo> tasks, int pageNumber, int pageSize) {
        int totalItems = tasks.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        if (pageNumber < 0 || pageNumber >= totalPages) {
            // 页数超出范围，返回空列表或适当处理
            return List.of();
        }

        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min((pageNumber + 1) * pageSize, totalItems);

        return tasks.subList(fromIndex, toIndex);
    }
}





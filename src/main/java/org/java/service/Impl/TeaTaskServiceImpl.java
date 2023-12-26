package org.java.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.java.entity.dto.GetUserDataDto;
import org.java.entity.pojo.TeaTask;
import org.java.entity.pojo.User;
import org.java.entity.vo.EndTimeTaskVo;
import org.java.entity.vo.GetUserDataVo;
import org.java.entity.vo.HistoryTaskVo;
import org.java.entity.vo.TeaTaskVo;
import org.java.mapper.TeaTaskMapper;
import org.java.mapper.UserMapper;
import org.java.service.TeaTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TeaTaskServiceImpl extends ServiceImpl<TeaTaskMapper, TeaTask> implements TeaTaskService {
    @Autowired
    private TeaTaskMapper teaTaskMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 管理员进行审核
     * @param teaTask
     * @return
     */
    @Override
    public boolean applyTask(TeaTask teaTask){
        if (teaTaskMapper.insert(teaTask) >0){
            return true;
        }
        return false;
    }

    /**
     * 系统教师手动加入老师
     * @param teaTask
     * @return
     */
    @Override
    public boolean intoTask(TeaTask teaTask){
        if (teaTaskMapper.insert(teaTask) >0){
            return true;
        }
        return false;
    }

    /**
     * 历史实习任务任教情况
     * @return
     */
    @Override
    public List<HistoryTaskVo> findHistoryTask(){
        return teaTaskMapper.findHistoryTask();
    }

    @Override
    public List<HistoryTaskVo> getHistoryById(Integer teaId) {
        return teaTaskMapper.getHistoryById(teaId);
    }

    @Override
    public List<TeaTaskVo> findApplyInfo(Integer taskId){
        return teaTaskMapper.findTeaTaskInfo(taskId);
    }
    @Override
    public TeaTask getTeaTaskById(Integer id){
        return teaTaskMapper.selectById(id);
    }

    @Override
    public List<EndTimeTaskVo> selectInternshipTaskByUserId(Integer userId){
        return teaTaskMapper.selectInternshipTaskByUserId(userId);
    }
    @Override
    public PageInfo<User> getUserData(GetUserDataDto getUserDataDto){
        PageHelper.startPage(getUserDataDto.getPageNumber(), getUserDataDto.getPageSize());
        log.info("获取教师：{}",getUserDataDto);
        List<TeaTask> teaTaskList = teaTaskMapper.selectList(new QueryWrapper<TeaTask>()
                .eq("task_id", getUserDataDto.getTaskId())
                .eq("academic_term", getUserDataDto.getTerm()));

        if (!getUserDataDto.getUsername().isEmpty()){
            List<User> userList = userMapper.selectList(new QueryWrapper<User>()
                    .like("username", getUserDataDto.getUsername()));
            List<User> result = userList.stream()
                    .filter(user -> teaTaskList.stream()
                            .noneMatch(teaTask -> teaTask.getUserId().equals(user.getId())))
                    .toList();
            return new PageInfo<>(result);
        }
        List<User> userList = userMapper.selectList(null);
        List<User> result = userList.stream()
                .filter(user -> teaTaskList.stream()
                        .noneMatch(teaTask -> teaTask.getUserId().equals(user.getId())))
                .toList();
        return new PageInfo<>(result);
    }
}

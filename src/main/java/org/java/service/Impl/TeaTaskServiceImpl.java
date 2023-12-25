package org.java.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.pojo.TeaTask;
import org.java.entity.vo.EndTimeTaskVo;
import org.java.entity.vo.GetUserDataVo;
import org.java.entity.vo.HistoryTaskVo;
import org.java.entity.vo.TeaTaskVo;
import org.java.mapper.TeaTaskMapper;
import org.java.service.TeaTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeaTaskServiceImpl extends ServiceImpl<TeaTaskMapper, TeaTask> implements TeaTaskService {
    @Autowired
    private TeaTaskMapper teaTaskMapper;

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
    public List<GetUserDataVo> getUserData(String username){
        return teaTaskMapper.getUserData(username);
    }
}

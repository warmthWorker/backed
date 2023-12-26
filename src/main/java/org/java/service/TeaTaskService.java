package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.java.entity.dto.GetUserDataDto;
import org.java.entity.pojo.TeaTask;
import org.java.entity.pojo.User;
import org.java.entity.vo.EndTimeTaskVo;
import org.java.entity.vo.GetUserDataVo;
import org.java.entity.vo.HistoryTaskVo;
import org.java.entity.vo.TeaTaskVo;

import java.util.List;

public interface TeaTaskService extends IService<TeaTask> {
    //管理员
    public boolean applyTask(TeaTask teaTask);
    //系统教师
    public boolean intoTask(TeaTask teaTask);

    public List<HistoryTaskVo> findHistoryTask();

    public List<HistoryTaskVo> getHistoryById(Integer teaId);

    public List<TeaTaskVo> findApplyInfo(Integer taskId);

    public TeaTask getTeaTaskById(Integer id);

    public List<EndTimeTaskVo> selectInternshipTaskByUserId(Integer userId);

    public PageInfo<User> getUserData(GetUserDataDto getUserDataDto);
}

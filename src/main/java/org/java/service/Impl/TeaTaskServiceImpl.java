package org.java.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.pojo.TeaTask;
import org.java.mapper.TeaTaskMapper;
import org.java.service.TeaTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}

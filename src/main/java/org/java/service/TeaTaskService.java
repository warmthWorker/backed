package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.java.entity.pojo.TeaTask;

public interface TeaTaskService extends IService<TeaTask> {
    //管理员
    public boolean applyTask(TeaTask teaTask);
    //系统教师
    public boolean intoTask(TeaTask teaTask);
}

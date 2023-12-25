package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.vo.ApplyTaskVo;

import java.util.Date;
import java.util.List;

public interface InternshiptaskService extends IService<Internshiptask> {


    public Integer getTaskTermByTaskId(Integer taskId);
    public boolean addTask(Internshiptask internshiptask);


    public List<ApplyTaskVo> getTasksByTerm(String courseName,int getTasksByTerm,int pageNumber, int pageSize);

    public Internshiptask getSymbol(String courseCategory,Integer academicTerm,String className);

    public long calculateTaskDuration (Integer taskId);
}

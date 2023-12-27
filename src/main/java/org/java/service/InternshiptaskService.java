package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.vo.ApplyTaskVo;
import org.java.entity.vo.EndTimeTaskVo;
import org.java.utils.resonse.Result;

import java.util.HashMap;
import java.util.List;

public interface InternshiptaskService extends IService<Internshiptask> {


    public Integer getTaskTermByTaskId(Integer taskId);
    public boolean addTask(Internshiptask internshiptask);


    public PageInfo<Internshiptask> getTasksByTerm(String courseName, int getTasksByTerm, int pageNumber, int pageSize);

    public Internshiptask getSymbol(String courseCategory,Integer academicTerm,String courseName);

    public long calculateTaskDuration (Integer taskId);

    public PageInfo<Internshiptask> getTimeOutTask(int academicTerm, int pageNumber, int pageSize);

    public List<Internshiptask> getEndTasks();

    public List<String> findAllTeasByOneTask(Integer taskId);
}

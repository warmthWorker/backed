package org.java.service;

import org.java.entity.pojo.Internshiptask;
import org.java.entity.vo.ApplyTaskVo;

import java.util.List;

public interface InternshiptaskService {


    public Integer getTaskTermByTaskId(Integer taskId);
    public boolean addTask(Internshiptask internshiptask);


    public List<ApplyTaskVo> getTasksByTerm(String courseName,int getTasksByTerm,int pageNumber, int pageSize);

    public Internshiptask getSymbol(String courseCategory,Integer academicTerm,String className);
}

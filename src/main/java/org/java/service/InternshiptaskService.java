package org.java.service;

import org.java.entity.pojo.Internshiptask;
import org.java.entity.vo.ApplyTaskVo;

import java.util.List;

public interface InternshiptaskService {

    public boolean addTask(Internshiptask internshiptask);

    public List<ApplyTaskVo> getTasks(int pageNumber, int pageSize);

    public Internshiptask getSymbol(String courseCategory,String academicTerm,String className);
}

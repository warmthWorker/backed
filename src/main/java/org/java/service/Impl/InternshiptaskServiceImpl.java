package org.java.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.pojo.ConTask;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.vo.ApplyTaskVo;
import org.java.mapper.ConTaskMapper;
import org.java.mapper.InternshiptaskMapper;
import org.java.service.InternshiptaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 程子
* @description 针对表【internshiptask】的数据库操作Service实现
* @createDate 2023-12-23 11:09:42
*/
@Service
public class InternshiptaskServiceImpl extends ServiceImpl<InternshiptaskMapper, Internshiptask>
    implements InternshiptaskService{
    @Autowired
    private InternshiptaskMapper mapper;
    @Autowired
    private ConTaskMapper conTaskMapper;
    @Override
    public boolean addTask(Internshiptask internshiptask){
        if (mapper.insert(internshiptask) > 0){

            ConTask conTask = conTaskMapper.selectOne(new QueryWrapper<ConTask>()
                    .eq("task_id", internshiptask.getTaskId()));
            // 比较年份，年份大就替换
            if (Integer.parseInt(internshiptask.getAcademicTerm())>conTask.getTerm()){
               conTask.setLTaskId(conTask.getNTaskId());
               conTask.setNTaskId(Integer.parseInt(internshiptask.getAcademicTerm()));
            }
            conTaskMapper.insert(conTask);
            return true;
        }
        return false;
    }
    @Override
    public List<ApplyTaskVo> getTasks(int pageNumber,int pageSize){
        List<Internshiptask> selectList = mapper.selectList(new QueryWrapper<>());
        List<ApplyTaskVo> applyTaskVos = new ArrayList<>();

        for (Internshiptask internshiptask : selectList) {
            ApplyTaskVo applyTaskVo = new ApplyTaskVo();
            BeanUtils.copyProperties(applyTaskVo, internshiptask);

        }
        return getPage(applyTaskVos,pageNumber,pageSize);
    }
    @Override
    public Internshiptask getSymbol(String courseCategory,String academicTerm,String className){
        Integer taskId = conTaskMapper.findCon(courseCategory, className);
        QueryWrapper<Internshiptask> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Internshiptask> taskId1 = queryWrapper.eq("task_id", taskId);
        return mapper.selectOne(taskId1);
    }

    /**
     * 分页
     * @param tasks
     * @param pageNumber 当前页
     * @param pageSize 每页大小
     * @return
     */
    public static List<ApplyTaskVo> getPage(List<ApplyTaskVo> tasks, int pageNumber, int pageSize) {
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min((pageNumber + 1) * pageSize, tasks.size());

        if (fromIndex > toIndex || fromIndex >= tasks.size()) {
            // 页数超出范围，返回空列表或适当处理
            return List.of();
        }
        return tasks.subList(fromIndex, toIndex);
    }
}





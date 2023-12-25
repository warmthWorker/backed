package org.java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.java.entity.pojo.TeaTask;
import org.java.entity.vo.EndTimeTaskVo;
import org.java.entity.vo.GetUserDataVo;
import org.java.entity.vo.HistoryTaskVo;
import org.java.entity.vo.TeaTaskVo;

import java.util.List;

@Mapper
public interface TeaTaskMapper extends BaseMapper<TeaTask> {

    /**
     * 获取所有老师历史选课记录
     * @return
     */
    List<HistoryTaskVo> findHistoryTask();

    public List<HistoryTaskVo> getHistoryById(@Param("teaId")Integer teaId);

    public List<TeaTaskVo>  findTeaTaskInfo(@Param("taskId")Integer taskId);

    List<EndTimeTaskVo> selectInternshipTaskByUserId(@Param("userId")Integer userId);

    List<GetUserDataVo> getUserData(@Param("username")String username);
}

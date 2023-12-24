package org.java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.java.entity.pojo.ConTask;


@Mapper
public interface ConTaskMapper extends BaseMapper<ConTask> {
    @Select("SELECT l_task_id FROM con_task WHERE course_category = #{course_category} AND MATCH(course_name) AGAINST (#{course_name}) AND l_task_id IS NOT NULL")
    public Integer findCon(@Param("course_category")String course_category,@Param("course_name")String course_name);
}

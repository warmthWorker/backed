package org.java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.java.entity.pojo.Internshiptask;


import java.util.List;


@Mapper
public interface InternshiptaskMapper extends BaseMapper<Internshiptask> {

    List<Internshiptask> selectInternshipTaskByUserId(@Param("userId")Integer userId);
}

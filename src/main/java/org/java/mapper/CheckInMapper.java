package org.java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.java.entity.pojo.CheckIn;

@Mapper
public interface CheckInMapper extends BaseMapper<CheckIn> {
}

package org.java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.java.entity.pojo.Score;

@Mapper
public interface ScoreMapper extends BaseMapper<Score> {
}

package org.java.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.java.entity.pojo.CheckIn;
import org.java.entity.pojo.Student;
import org.java.entity.vo.CheckInStudentVo;

import java.util.Date;
import java.util.List;

@Mapper
public interface CheckInMapper extends BaseMapper<CheckIn> {

    List<CheckInStudentVo> getNoCheckInStudents(
            @Param("className") String className,
            @Param("attendanceDate") Date attendanceDate
    );

    List<CheckInStudentVo> getCheckedInStudents(
            @Param("className") String className,
            @Param("attendanceDate") Date attendanceDate
    );
}

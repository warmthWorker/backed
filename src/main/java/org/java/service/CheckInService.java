package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.java.entity.dto.CheckInDto;
import org.java.entity.pojo.CheckIn;
import org.java.entity.vo.CheckInStudentVo;
import org.java.entity.vo.CheckInVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface CheckInService extends IService<CheckIn> {
    public Integer checkInOption(CheckInDto checkInDto);

//    public List<CheckIn> getClassCheckInRecords(String className, Date date);

    public ArrayList<CheckInVo> getStudentCheckInRecords(String studentId, Date date);

    public Integer getCheckInDays(Integer stuId, Integer taskId);

    public List<CheckIn> getCheckInRecords(Integer stuId, Integer taskId);
    public List<CheckInStudentVo> getNoCheckedInStudents(String courseName, Date attendanceDate);

    public List<CheckInStudentVo> getCheckedInStudents(String courseName, Date attendanceDate);
}

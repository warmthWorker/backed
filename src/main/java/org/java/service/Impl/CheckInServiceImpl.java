package org.java.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.dto.CheckInDto;
import org.java.entity.pojo.CheckIn;
import org.java.entity.vo.CheckInVo;
import org.java.mapper.CheckInMapper;
import org.java.service.CheckInService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckInServiceImpl extends
        ServiceImpl<CheckInMapper, CheckIn> implements CheckInService {

    @Autowired
    private CheckInMapper checkInMapper;


    // 打卡操作
    @Override
    public Integer checkInOption(CheckInDto checkInDto){
        CheckIn checkIn = new CheckIn();
        BeanUtils.copyProperties(checkInDto, checkIn);
        //  开始时间
        Date checkInTime = checkInDto.getCheckInTime();
        // 结束时间
        Date checkOutTime = checkInDto.getCheckOutTime();
        // 打卡时间
        Date attendanceDate = checkInDto.getAttendanceDate();
        if (checkInTime != null && checkOutTime != null) {
            if (attendanceDate.before(checkInTime)) {
                checkIn.setStatus(1); // 在上班之前打卡
                checkInMapper.insert(checkIn);
                return 1;
            } else if (attendanceDate.before(checkOutTime)
                    && attendanceDate.after(checkInTime)) {
                checkIn.setStatus(2); // 在上班之中打卡
                checkInMapper.insert(checkIn);
                return 2;
            }
        }
        checkIn.setStatus(0); // 下班之后或其他情况
        checkInMapper.insert(checkIn);
        return 0;
    }

//    @Override
//    public List<CheckIn> getClassCheckInRecords(String className, Date date) {
//        // 调用MyBatis-Plus的查询方法，获取某班某天的打卡记录
//        return checkInMapper.selectList(
//                new QueryWrapper<CheckIn>()
//                        .eq("class_name", className)
//                        .between("attendance_date", date, new Date(date.getTime() + 24 * 60 * 60 * 1000 - 1)) // 24小时内
//        );
//    }

    @Override
    public ArrayList<CheckInVo> getStudentCheckInRecords(String studentId, Date date) {
        // 调用MyBatis-Plus的查询方法，获取某学生某天的打卡记录
        List<CheckIn> checkInList = checkInMapper.selectList(
                new QueryWrapper<CheckIn>()
                        .eq("stu_id", studentId)
                        .between("attendance_date", date, new Date(date.getTime() + 24 * 60 * 60 * 1000 - 1)) // 24小时内
        );
        ArrayList<CheckInVo> checkInVos = new ArrayList<>();
        for (CheckIn checkIn : checkInList) {
            CheckInVo checkInVo = new CheckInVo();
            //类复制
            BeanUtils.copyProperties(checkIn, checkInVo);
        }
        return checkInVos;
    }
}

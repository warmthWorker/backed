package org.java.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInDto {
    private String id;
    private String stuId;
    private Integer taskId;
    private Date checkInTime;//学习开始打卡时间
    private Date checkOutTime;
    private Date attendanceDate;
    //打卡状态
}

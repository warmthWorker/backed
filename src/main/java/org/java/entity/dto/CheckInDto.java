package org.java.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInDto {
    private String stuId;
    private Integer taskId;
//    private Time checkInTime;//学习开始打卡时间
//    private Time checkOutTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date attendanceDate;
    private Integer status;//打卡状态
}

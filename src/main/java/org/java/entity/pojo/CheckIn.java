package org.java.entity.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckIn {// 打卡表
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String stuId;
    private Integer taskId;
    private Time checkInTime = Time.valueOf("09:00:00");
    private Time checkOutTime = Time.valueOf("18:00:00");
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date attendanceDate;
    private Integer status; //打卡状态 :0未打卡  1成功  2迟到
}

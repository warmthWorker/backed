package org.java.entity.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckIn {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String stuId;
    private Integer taskId;
    private Date checkInTime;
    private Date checkOutTime;
    private Date attendanceDate;
    private Integer status; //打卡状态 :0未打卡  1成功  2迟到
    private String remarks;
}

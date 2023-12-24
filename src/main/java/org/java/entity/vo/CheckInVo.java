package org.java.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInVo {
    private String id;
    private String stuId;
    private Integer taskId;
    private Date checkInTime;
    private Date checkOutTime;
    private Date attendanceDate;
}

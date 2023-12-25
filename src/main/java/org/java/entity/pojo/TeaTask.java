package org.java.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 老师选课表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeaTask {// 教师选课记录表
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer taskId;
    private Integer userId;
    private Integer academicTerm;//学期
    private Integer mark = 0;// 审核状态: 0 为审核 1已审核(管理员指定的系统教师) 2 系统教师加入的其他教师
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    private Date time;
}

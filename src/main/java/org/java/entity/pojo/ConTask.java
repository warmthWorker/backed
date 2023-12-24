package org.java.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConTask {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer courseId;
    private Integer term;// 学期
    private String courseCategory;
    private String courseName;
    private Integer nTaskId; //本次试验任务id
    private Integer lTaskId; // 上次试验任务id
}

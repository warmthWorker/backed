package org.java.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConTask {
    private Integer id;
    private Integer courseId;
    private Integer term;
    private String courseCategory;
    private String courseName;
    private Integer nTaskId; //本次试验任务id
    private Integer lTaskId; // 上次试验任务id
}

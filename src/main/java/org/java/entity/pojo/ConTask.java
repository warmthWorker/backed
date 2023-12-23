package org.java.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConTask {
    private Integer id;
    private Integer course_id;
    private Integer term;
    private String course_category;
    private String course_name;
    private Integer n_task_id;
    private Integer l_task_id;
}

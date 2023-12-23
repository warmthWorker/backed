package org.java.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer id;
    private String name;
    private Integer t_number;
    private Integer n_number;
    private Integer credit;
    private Integer period;
    private Integer mark;
}

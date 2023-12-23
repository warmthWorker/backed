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
    private Integer tNumber;
    private Integer nNumber;
    private Integer credit;
    private Integer period;
    private Integer mark;
}

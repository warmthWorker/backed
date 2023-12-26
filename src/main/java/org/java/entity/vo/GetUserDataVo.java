package org.java.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDataVo {
    private Integer userId;
    private String username;
    private String collegeName;
}

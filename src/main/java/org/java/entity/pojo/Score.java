package org.java.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    @TableId(type = IdType.AUTO)
    private Integer sid;
    private Integer stuId;
    private Integer taskId;
    private Integer peacetime;
    private Integer major;
    private Integer total;
}

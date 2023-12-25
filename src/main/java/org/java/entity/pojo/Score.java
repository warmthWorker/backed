package org.java.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor// 分数表其中peacetime表是平时表现分，major表示专业分（需要任课老师填写）
public class Score {
    @TableId(type = IdType.AUTO)
    private Integer sid;
    private Integer stuId;
    private Integer taskId;
    private double peacetime;
    private double major;
    private double total;
}

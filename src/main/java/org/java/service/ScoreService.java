package org.java.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.java.entity.pojo.Score;

import java.util.Map;

public interface ScoreService extends IService<Score> {

    public double calculatePeacetimeScore(Integer stuId, Integer taskId);

    public double getMajorScore(Integer stuId, Integer taskId);

    public Map<String, Double> getStuTotalScore(Integer stuId, Integer taskId);
}

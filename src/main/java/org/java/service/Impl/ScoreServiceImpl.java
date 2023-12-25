package org.java.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.java.entity.pojo.Score;
import org.java.mapper.ScoreMapper;
import org.java.service.CheckInService;
import org.java.service.InternshiptaskService;
import org.java.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Autowired
    private CheckInService checkInService;
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 计算学生平时成绩
     * @param stuId
     * @param taskId
     * @return
     */
    @Override
    // 计算学生平时成绩
    public double calculatePeacetimeScore(Integer stuId, Integer taskId) {
        // 打卡天数
        Integer checkInDays = checkInService.getCheckInDays(stuId, taskId);
        // 总天数
        long totalDays = internshiptaskService.calculateTaskDuration(taskId);
        // 根据具体的计算规则计算平时成绩
        // 平时成绩=（打卡天数/考勤天数）*50
        if (totalDays == 0) {
            return 0;
        }
        double result = (checkInDays * 50.0) / totalDays;
        String formattedResult = String.format("%.2f", result);
        return Double.parseDouble(formattedResult);
    }
    @Override
    //获取学生专业成绩
    public double getMajorScore(Integer stuId, Integer taskId){
        Score score = scoreMapper.selectOne(new QueryWrapper<Score>()
                .eq("stu_id", stuId).eq("task_id", taskId));
        return score.getMajor();
    }

    @Override
    // 获取学生总成绩
    public Map<String, Double> getStuTotalScore(Integer stuId, Integer taskId){
        HashMap<String, Double> doubleHashMap = new HashMap<>();
        double peacetimeScore = calculatePeacetimeScore(stuId, taskId);
        double majorScore = getMajorScore(stuId, taskId);
        double totalScore = peacetimeScore + majorScore;

        // 更新总分数
        UpdateWrapper<Score> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("stu_id", stuId).eq("task_id", taskId)
                .set("peacetime", peacetimeScore)
                .set("major", majorScore)
                .set("total", totalScore);

        scoreMapper.update(null, updateWrapper);

        doubleHashMap.put("peacetimeScore",peacetimeScore);
        doubleHashMap.put("majorScore",majorScore);
        doubleHashMap.put("totalScore",totalScore);
        return doubleHashMap;

    }
}

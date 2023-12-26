package org.java.utils;

import lombok.extern.slf4j.Slf4j;
import org.java.entity.pojo.Internshiptask;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class WorkloadCalculatorUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public double calculateWorkload(Internshiptask internshiptask) {
        log.info("工作量计算");
        // 将字符串转换为 LocalDateTime 对象
//        LocalDateTime beginTime = LocalDateTime.parse((CharSequence) internshiptask.getBeginTaskTime(), formatter);
//        LocalDateTime endTime = LocalDateTime.parse((CharSequence) internshiptask.getTaskDeadline(), formatter);
        String beginTimeStr = formatter.format(internshiptask.getBeginTaskTime().toInstant());
        String endTimeStr = formatter.format(internshiptask.getTaskDeadline().toInstant());

        // 然后再进行解析
        LocalDateTime beginTime = LocalDateTime.parse(beginTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
        // 计算周次
        long weeks = calculateWeeks(beginTime, endTime);
        // 计算总工作量
        return switch (internshiptask.getCourseCategory()) {
            case "机电工程训练中心", "汽车修理实习", "创新实习" ->
                    weeks * ((double) internshiptask.getStudentCount() / 50) * 25;
            case "翔宇创新精英班实习（实训）" -> weeks * 20;
            default -> //其他实习（含社会实践）
                    weeks * ((double) internshiptask.getStudentCount() / 50) * 20;
        };
    }
    // 计算周次
    private static long calculateWeeks(LocalDateTime beginTime, LocalDateTime endTime) {
        return beginTime.until(endTime, ChronoUnit.WEEKS) + 1;
    }
}

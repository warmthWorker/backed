package org.java.utils;

import lombok.extern.slf4j.Slf4j;
import org.java.entity.pojo.Internshiptask;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
public class WorkloadCalculatorUtil {
    public double calculateWorkload(Internshiptask internshiptask) {
        log.info("工作量计算");

        Date beginDate = internshiptask.getBeginTaskTime();
        Date endDate = internshiptask.getTaskDeadline();

        // 计算周次
        long weeks = calculateWeeks(beginDate, endDate);

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
    private static long calculateWeeks(Date beginDate, Date endDate) {
        long millisecondsPerWeek = 7 * 24 * 60 * 60 * 1000; // 一周的毫秒数
        long diffInMilliseconds = endDate.getTime() - beginDate.getTime();

        return diffInMilliseconds / millisecondsPerWeek + 1;
    }
}

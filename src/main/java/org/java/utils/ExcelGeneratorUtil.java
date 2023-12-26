package org.java.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.java.entity.pojo.Internshiptask;
import org.java.entity.vo.OutFileDataVo;
import org.java.mapper.UserMapper;
import org.java.service.InternshiptaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelGeneratorUtil {

    @Autowired
    private WorkloadCalculatorUtil workloadCalculatorUtil;
    @Autowired
    private InternshiptaskService internshiptaskService;
    @Autowired
    private UserMapper userMapper;

    public byte[] generateExcel(List<String> usernames, Integer taskId,Integer userId) throws IOException {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("任务工作量分配表");
        Internshiptask internshiptask = internshiptaskService.getOne(
                new QueryWrapper<Internshiptask>().eq("task_id", taskId));
        String username = userMapper.selectById(userId).getUsername();
        // 创建单元格样式
        CellStyle centerAlignStyle = workbook.createCellStyle();
        centerAlignStyle.setAlignment(HorizontalAlignment.CENTER);
        centerAlignStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        String[] headers = {"课程代码", "课程名称", "课程性质", "合班信息", "学分", "总学时", "讲课学时", "已选人数", "总工作量", "负责教师", "个人工作量"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(centerAlignStyle);
            cell.setCellValue(headers[i]);
            sheet.setColumnWidth(i, 7 * 256);
        }
        int rowNum = 1;
        // 在这里添加逻辑来填充数据到工作表
        Row row = sheet.createRow(rowNum); //第二行
        // 根据你的实体类属性设置相应的单元格值
        row.createCell(0).setCellValue(internshiptask.getCourseCode());//课程代码
        row.createCell(1).setCellValue(internshiptask.getCourseName());//课程名称
        row.createCell(2).setCellValue(internshiptask.getCourseCategory());//课程性质
        row.createCell(3).setCellValue(internshiptask.getClassName());//合班信息
        row.createCell(4).setCellValue(internshiptask.getCreditHours());//学分
        row.createCell(5).setCellValue(internshiptaskService.calculateTaskDuration(taskId) * 8);//总学时 任务天数 * 8
        row.createCell(6).setCellValue(internshiptaskService.calculateTaskDuration(taskId) * 8);//讲课学时
        row.createCell(7).setCellValue(internshiptask.getStudentCount());//已选人数

        // 计算总工作量，这里需要根据你的逻辑进行计算
        double totalWorkload = workloadCalculatorUtil.calculateWorkload(internshiptask);
        row.createCell(8).setCellValue(totalWorkload);//总工作量
        row.createCell(9).setCellValue(username);//负责教师
        // 其他属性依次设置...
//        for (String username : usernames) {
//            Row row2 = sheet.createRow(rowNum++);
//            // 多名老师一起
//            row2.createCell(9).setCellValue(username);//负责教师
//        }
        row.createCell(10).setCellValue(totalWorkload);//个人工作量

        // 将工作簿写入字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        return outputStream.toByteArray();
    }
}

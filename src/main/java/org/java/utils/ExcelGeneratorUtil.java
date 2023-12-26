//package org.java.utils;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.java.entity.vo.OutFileDataVo;
//import org.java.service.InternshiptaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public class ExcelGeneratorUtil {
//
//    @Autowired
//    private WorkloadCalculatorUtil workloadCalculatorUtil;
//    @Autowired
//    private InternshiptaskService internshiptaskService;
//
//    public byte[] generateExcel(List<String> username, Integer taskId) throws IOException {
//        // 创建工作簿
//        Workbook workbook = new XSSFWorkbook();
//        // 创建工作表
//        Sheet sheet = workbook.createSheet("任务工作量分配表");
//
//        // 创建标题行
//        Row headerRow = sheet.createRow(0);
//        String[] headers = {"课程代码", "课程名称", "课程性质", "合班信息", "学分", "总学时", "讲课学时", "已选人数", "总工作量", "负责教师", "个人工作量"};
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//        }
//
//        // 在这里添加逻辑来填充数据到工作表
//        Row row = sheet.createRow(1); //第二行
//        // 根据你的实体类属性设置相应的单元格值
//        row.createCell(0).setCellValue(outFileDataVo.getCourseCode());
//        row.createCell(1).setCellValue(outFileDataVo.getCourseName());
//        row.createCell(2).setCellValue(outFileDataVo.getCourseCategory());
//        row.createCell(3).setCellValue(outFileDataVo.getClassName());
//        row.createCell(4).setCellValue(outFileDataVo.getCreditHours());
//        // 其他属性依次设置...
//
//        // 计算总工作量，这里需要根据你的逻辑进行计算
//        double totalWorkload = workloadCalculatorUtil.calculateWorkload()
//        row.createCell(8).setCellValue(totalWorkload);
//
//        // 设置负责教师和个人工作量，这里需要根据你的逻辑进行设置
//        row.createCell(9).setCellValue("负责教师姓名");
//        row.createCell(10).setCellValue("个人工作量");
//
//        // 将工作簿写入字节数组输出流
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//
//        return outputStream.toByteArray();
//    }
//}

package com.jok.zxserver.utils;


import com.jok.zxserver.domain.entity.question.Question;
import com.jok.zxserver.domain.entity.question.QuestionTest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author JOKER
 * create time 2024/12/8 16:35
 */
public class QuestionExportModule {
    public static void main(String[] args) throws IOException {
        fillExcel();
    }
    public static void writeExcel() throws IOException {
        Workbook workbook = questionImportModule();
        // 文件路径
        String fileName = "G:/test.xlsx";
        FileOutputStream os = new FileOutputStream(fileName);
        workbook.write(os);
        os.close();
        workbook.close();

        System.out.println("生成完毕");

    }

    /**
     * 创建测试题导入模板工作簿
     * @return
     */
    public static Workbook questionImportModule(){
        String message = "填写说明:\r\n" +
                "1,每个题目必须填写序号,单元,题目,题目类型以及答案；\r\n" +
                "2,目前暂不支持填空题，每个题最多有10个选项，按照模板每个填写各项答案和分值\r\n" +
                "3,填写之后又清除的表格请使用删除的方式来删除内容，不要使用清除内容来清除";

        List<String> heads = new ArrayList<>();
        heads.add("序号");
        heads.add("题目内容");
        heads.add("题目类型");
        heads.add("单元");
        for (int i = 0; i < 5; i++) {
            heads.add("答案" + i);
            heads.add("答案" + i + "分值");
        }
        Workbook workbook = new XSSFWorkbook(); // 创建工作簿
        Sheet workSheet1 = workbook.createSheet("测试sheet"); // 创建工作表
        CellStyle messageCellStyle = workbook.createCellStyle(); // 设置提示语单元格样
        messageCellStyle.setWrapText(true);
        messageCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        Row row1 = workSheet1.createRow(0); // 提示语单元格所在行
        row1.setHeight((short) 1500);
        Cell messageCell = row1.createCell(0); // 提示语单元格
        messageCell.setCellStyle(messageCellStyle);
        messageCell.setCellValue(new XSSFRichTextString(message));

        Row row2 = workSheet1.createRow(1);
        int i = 0;
        int mergeCol = heads.toArray().length;
        workSheet1.addMergedRegion(new CellRangeAddress(0,0,0,mergeCol-1));
        for (String s:heads){
            row2.createCell(i).setCellValue(s);
            i++;
        }
        return workbook;
    }
    public static void readExcel(InputStream inputStream) throws IOException {
//        FileInputStream fileInputStream = new FileInputStream("G:/test.xlsx");
        Workbook wb = new XSSFWorkbook(inputStream);
        Sheet sheet = wb.getSheetAt(0);
        int i = 0;
        for (Row row:sheet){
            if(i <2){
                i++;
                continue;
            }
            int j = 0;
            List<QuestionTest> questionTests = new ArrayList<>();
            new Question();
            for (Cell cell:row){
                CellType cellType = cell.getCellType();
                System.out.println(cellType);
                switch (cellType){
                    case STRING:
                        if(cell.getRichStringCellValue()!=null && !cell.getRichStringCellValue().equals("")){
                            System.out.println(cell.getStringCellValue());
                        }
                        break;
                    case NUMERIC:
                        System.out.println(String.valueOf(cell.getNumericCellValue()));
                }

            }
        }

    }

    // 填充问题的单元分类
    public static void fillExcel() throws IOException {
        int[] t1 = {1 ,4 ,12 ,27 ,40 ,42 ,48 ,49 ,52 ,53 ,56,58};
        int[] t2 = {3 ,9 ,10 ,28 ,38 ,45 ,46 ,51 ,55,65 };
        int[] t3 = {6 ,21 ,34 ,36 ,37 ,41 ,61 ,69,73};
        int[] t4 = {5 ,14 ,15 ,20 ,22 ,26 ,29 ,30 ,31 ,32 ,54 ,71,79};
        int[] t5 = {2 ,17 ,23 ,33 ,39 ,57 ,72 ,78 ,80,86};
        int[] t6 = {11 ,24 ,63 ,67 ,74,81};
        int[] t7 = {13 ,25 ,47 ,50 ,70 ,75,82 };
        int[] t8 = {8 ,18 ,43 ,68 ,76,83};
        int[] t9 = {7 ,16 ,35 ,62 ,77 ,84 ,85 ,87 ,88,90};
        int[] t10 = {19 ,44 ,59 ,60 ,64 ,66,89};
        List<int[]> ints = Arrays.asList(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10);

        FileInputStream inputStream = null;
        Workbook workbook = null;
        try {
            inputStream = new FileInputStream("G:/example.xlsx");
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for(int i = 0 ; i < ints.size() ; i++){
                for(int j = 0 ;j < ints.get(i).length;j++){
                    System.out.println("题号："+ints.get(i)[j]+"列"+3);
                    Cell cell = sheet.getRow(ints.get(i)[j] + 1).getCell(3);
                    System.out.println("单元格对象"+cell.toString());
                    if (cell != null) {
                        cell.setCellValue(i+1);
                    }

                }
            }
            try (FileOutputStream outputStream = new FileOutputStream("G:/example2.xlsx")) {
                workbook.write(outputStream);
            }
            workbook.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            workbook.close();
            inputStream.close();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
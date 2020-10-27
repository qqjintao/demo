package com.intertek.demo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jacksy.qin
 * @date 2019/10/15 10:08
 */

@Slf4j
public class ReadExcelUtil {
    private Workbook wb;
    private Sheet sheet;
    private Row row;

    public ReadExcelUtil(String filepath, int sheetIndex) {
        if(filepath==null){
            return;
        }
        String ext = filepath.substring(filepath.lastIndexOf("."));
        try {
            InputStream is = new FileInputStream(filepath);
            if(".xls".equals(ext)){
                wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                wb = new XSSFWorkbook(is);
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("FileNotFoundException", e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException", e);
        }
    }

    public ReadExcelUtil(MultipartFile file, int sheetIndex) {
        if(file.isEmpty()){
            return;
        }
        try {
            String filename = file.getOriginalFilename();
            if(StringUtils.endsWith(filename, ".xls")){
                wb = new HSSFWorkbook(file.getInputStream());
            }else if(StringUtils.endsWith(filename, ".xlsx")){
                wb = new XSSFWorkbook(file.getInputStream());
            }else{
                wb=null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("FileNotFoundException", e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException", e);
        }
    }

    /**
     * 读取Excel表格表头的内容
     *
     * @param sheetIndex
     * @return String 表头内容的数组
     * @author zengwendong
     */
    public String[] readExcelTitle(int sheetIndex) throws Exception{
        if(wb==null){
            throw new Exception("Workbook对象为空！");
        }
        sheet = wb.getSheetAt(sheetIndex);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            // title[i] = getStringCellValue(row.getCell((short) i));
			/*String headerValue = "";
			if(row.getCell(i) != null && row.getCell(i).toString().length() > 0){
				headerValue = row.getCell(i).getCellFormula();
			}*/
            title[i] = row.getCell(i).toString();
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     *
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     * @author zengwendong
     */
    public Map<Integer, Map<Integer,Object>> readExcelContent(int sheetIndex) throws Exception{
        if(wb==null){
            throw new Exception("Workbook对象为空！");
        }
        Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();

        sheet = wb.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 0; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<Integer,Object> cellValue = new HashMap<Integer, Object>();
            while (j < colNum) {
                if(row != null){
                    Object obj = getCellFormatValue(row.getCell(j));
                    cellValue.put(j, obj);
                }
                j++;
            }
            content.put(i, cellValue);
        }
        return content;
    }

    /**
     *
     * 根据Cell类型设置数据
     *
     * @param cell
     * @return
     * @author zengwendong
     */
    private Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        try{
            if (cell != null) {
                // 判断当前Cell的Type
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                    case Cell.CELL_TYPE_FORMULA: {
                        // 判断当前的cell是否为Date
                        if (DateUtil.isCellDateFormatted(cell)) {
                            // 如果是Date类型则，转化为Data格式
                            // data格式是带时分秒的：2013-7-10 0:00:00
                            // cellvalue = cell.getDateCellValue().toLocaleString();
                            // data格式是不带带时分秒的：2013-7-10
                            Date date = cell.getDateCellValue();
                            DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                            cellvalue = formater.format(date);
                        } else {// 如果是纯数字

                            // 取得当前Cell的数值
                            cellvalue = String.valueOf(cell.getNumericCellValue());
                        }
                        break;
                    }
                    case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                        // 取得当前的Cell字符串
                        cellvalue = cell.getRichStringCellValue().getString();
                        break;
                    default:// 默认的Cell值
                        cellvalue = "";
                }
            } else {
                cellvalue = "";
            }
        }catch(Exception e){

        }
        return cellvalue;
    }
}

package com.qingyun.utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtils {

	
    /**
     * @Description 根据输入流得到Workbook的方法
     * @author 张立增
     * @Date 2020年1月30日 下午6:09:31
     */
	public static Workbook getWorkBook(InputStream in,String fileName) throws Exception  {
		Workbook workbook = null;
		try {
		    if(fileName.endsWith("xls")){ //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
	            workbook = new HSSFWorkbook(in);
	        }else if(fileName.endsWith("xlsx")){ //2007 及2007以上
	            workbook = new XSSFWorkbook(in); //2003
	        }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return workbook;
	}
	
	/**
	 * @Description 解析cell的方法
	 * @author 张立增[zhanglizeng] Tel：18860126570
	 * @createDate 2018年4月25日 下午1:08:17
	 */
	public static String parseCell(Cell cell) {
		String cellValue = "";  
		SimpleDateFormat sdf = new  SimpleDateFormat("yyy-MM-dd");//日期格式化  
		if(cell != null && !"".equals(cell)){
			switch (cell.getCellType()){
	        case Cell.CELL_TYPE_NUMERIC:   //数字
	             if("General".equals(cell.getCellStyle().getDataFormatString())) {  
	            	 cellValue = cell.getNumericCellValue()+"";     
                 }else if("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {  
                	 cellValue = sdf.format(cell.getDateCellValue());  
                 }else if(HSSFDateUtil.isCellDateFormatted(cell)){  
                     Date date = cell.getDateCellValue();  
                     cellValue = sdf.format(date);                 
                 }else {  
                	 cellValue = cell.getNumericCellValue()+"";  
                 }              
	            break;
	        case Cell.CELL_TYPE_STRING: //字符串
	            cellValue = String.valueOf(cell.getStringCellValue());
	            break;
	        case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getNumericCellValue());          
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "";
                break;
            default:
                cellValue = "";
                break;   
		    }
		}
        return cellValue;
	}
	
	/**
	 * @Description 按字符串解析cell的方法
	 * @author 张立增[zhanglizeng] Tel：18860126570
	 * @createDate 2018年4月25日 下午1:08:17
	 */
	public static String parseCellStr(Cell cell) {
		return String.valueOf(cell.getStringCellValue());
	}
}

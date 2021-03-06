package com.example.business.util;

import com.example.business.model.CouponFileSendModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.coyote.Response;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class fileUtil {

    //校验手机号格式正则表达式
    private final static String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";

    public List<CouponFileSendModel> fileContent(String filePath) {
        CouponFileSendModel couponFileSendModel = new CouponFileSendModel();
        File f = new File(filePath);
        int row = 0;
        List<CouponFileSendModel> list = new ArrayList<CouponFileSendModel>();
        try {

            try {
                Workbook book = null;//
                book = Workbook.getWorkbook(f);
                Sheet sheet = book.getSheet(0); //获得第一个工作表对象
                for (int i = 1; i < sheet.getRows(); i++) {
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        Cell cell = sheet.getCell(j, i); //获得单元格
                        System.out.print(cell.getContents() + " ");
                        couponFileSendModel.setPhone(cell.getContents());
                        list.add(couponFileSendModel);
                    }
                    System.out.print("\n");
                }
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
        return  list;
    }

    public int countFileRow(String filePath) {
        CouponFileSendModel couponFileSendModel = new CouponFileSendModel();
        File f = new File(filePath);
        int row = 0;
        List<CouponFileSendModel> list = new ArrayList<CouponFileSendModel>();
        try {
            Workbook book = null;//
            try {
                book = Workbook.getWorkbook(f);
                Sheet sheet = book.getSheet(0); //获得第一个工作表对象
                row =  sheet.getRows()-1;
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    return  row;
    }

    public Response countFileRowXslx(String filePath) {
        int rowNum =0;
        Response response = new Response();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            //解析excel
            POIFSFileSystem pSystem = new POIFSFileSystem(fis);
            //获取整个excel
            HSSFWorkbook hb = null;
            hb = new HSSFWorkbook(pSystem);
            /*System.out.println(hb.getNumCellStyles());*/
            //获取第一个表单sheet
            HSSFSheet sheet = hb.getSheetAt(0);
            rowNum = sheet.getLastRowNum();
            //获取第二行
            int secondRow = sheet.getFirstRowNum()+1;
            //增加表格样式校验
            for (int i = 2996; i < sheet.getLastRowNum()+1 ;i++){
                HSSFRow row = sheet.getRow(i);
                if (row == null){
                    rowNum--;
                }else {
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(row.getLastCellNum() - 1);
                    if (!(cell != null && cell.getCellType() != CellType.BLANK)) {
                        rowNum--;
                    } else {
                        String value = getCellValue(cell);
                        //String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";
                        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(value);
                        if (!(m.matches() && value.length() == 11)) {
                            response.setMessage("第" + (i + 1) + "行手机号不匹配！");
                            //throw new RuntimeException("第"+(i+1)+"行手机号不匹配！");
                            return response;
                        }
                    }
                }
            }
           response.setMessage(String.valueOf(rowNum));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return response ;
    }
    public List<CouponFileSendModel> fileContentXslx(String filePath) {
        List<CouponFileSendModel> list=new ArrayList<CouponFileSendModel>();
        try {
        //用流的方式先读取到你想要的excel的文件
        FileInputStream fis=new FileInputStream(new File(filePath));
        //解析excel
        POIFSFileSystem pSystem=new POIFSFileSystem(fis);
        //获取整个excel
        HSSFWorkbook hb=new HSSFWorkbook(pSystem);
        System.out.println(hb.getNumCellStyles());
        //获取第一个表单sheet
        HSSFSheet sheet=hb.getSheetAt(0);
        //获取第二行
        int secondRow = sheet.getFirstRowNum()+1;
        //获取最后一行
        int lastrow = sheet.getLastRowNum();
        //循环行数依次获取列数
        for (int i = secondRow; i < lastrow+1; i++) {
            //获取哪一行i
            HSSFRow row=sheet.getRow(i);
            if (row!=null) {
                //获取这一行的第一列
                int firstcell = row.getFirstCellNum();
                //获取这一行的最后一列
                int lastcell = row.getLastCellNum();
                //创建一个集合，用处将每一行的每一列数据都存入集合中
                for (int j = firstcell; j <lastcell; j++) {
                    //获取第j列
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
                    if (cell!=null && cell.getCellType() != CellType.BLANK) {
                        System.out.print(getCellValue(cell)+"\t");
                        CouponFileSendModel couponFileSendModel = new CouponFileSendModel();
                        couponFileSendModel.setPhone(getCellValue(cell));
                        list.add(couponFileSendModel);
                    }
                }
            }
            fis.close();
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return null;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#");
        //根据自己的情况进行类型添加
        switch (cell.getCellType()) {
            case NUMERIC:
                cellValue=decimalFormat.format(cell.getNumericCellValue()).toString().trim();
                break;
            case STRING:
                cellValue = cell.getStringCellValue().toString().trim();
                break;
        }
        return cellValue;
    }

    public void readXmlRow(String path){
        try {
            FileInputStream fis=new FileInputStream(new File(path));
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNext()){
                String date = scanner.next();
                System.out.println(date+"###########");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            int count= 0;
            FileInputStream fis=new FileInputStream(new File("D:/template/test.xlsx"));
            //解析excel
            POIFSFileSystem pSystem=new POIFSFileSystem(fis);
            //获取整个excel
            HSSFWorkbook hb=new HSSFWorkbook(pSystem);
            System.out.println(hb.getNumCellStyles());
            //获取第一个表单sheet
            HSSFSheet sheet=hb.getSheetAt(0);
            //获取第二行
            int secondRow = sheet.getFirstRowNum()+1;
            //获取最后一行
            int lastrow = sheet.getLastRowNum();
            System.out.println(lastrow+"%%%%%%%%%%%%%%%%");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



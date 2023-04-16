package org.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {

    private final String excelFilePath;
    Workbook work = null;
    private FileInputStream fin = null;

    public ExcelReader(String fileName) {

        excelFilePath = System.getProperty("user.dir") + "/Resources/testdata/";

        if (!fileName.endsWith(".xlsx")) {

            fileName = fileName + ".xlsx";
        }


        String file = excelFilePath + fileName;

        try {

            fin = new FileInputStream(file);

            work = new XSSFWorkbook(fin);

            fin.close();
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public String readCell(String sheetName, int row, int col) {

        DataFormatter formatter = new DataFormatter();

        Sheet sheet = work.getSheet(sheetName);

        Row roww = sheet.getRow(row);
        Cell cell = roww.getCell(col);

        return formatter.formatCellValue(cell);

    }

    public String readCell(int sheetIndex, int row, int col) {

        DataFormatter formatter = new DataFormatter();

        Sheet sheet = work.getSheetAt(sheetIndex);
        Row roww = sheet.getRow(row);
        Cell cell = roww.getCell(col);

        return formatter.formatCellValue(cell);

    }

}

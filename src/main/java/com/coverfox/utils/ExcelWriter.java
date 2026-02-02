package com.coverfox.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWriter {
    public static String writeSingleColumn(String sheetName, String header, List<String> rows, String filePrefix) {
        String filename = "output/" + filePrefix + "-" + DateTimeUtil.timestamp() + ".xlsx";
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(sheetName);
            int r = 0;
            Row headerRow = sheet.createRow(r++);
            headerRow.createCell(0).setCellValue(header);
            for (String val : rows) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(val);
            }
            for (int i = 0; i < 1; i++) sheet.autoSizeColumn(i);
            try (FileOutputStream out = new FileOutputStream(filename)) {
                wb.write(out);
            }
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String writePolicies(String sheetName, List<String[]> rows, String filePrefix) {
        String filename = "output/" + filePrefix + "-" + DateTimeUtil.timestamp() + ".xlsx";
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet(sheetName);
            int r = 0;
            Row headerRow = sheet.createRow(r++);
            headerRow.createCell(0).setCellValue("#");
            headerRow.createCell(1).setCellValue("Insurer");
            headerRow.createCell(2).setCellValue("Price (₹)");
            headerRow.createCell(3).setCellValue("Premium (₹)");
            int idx = 1;
            for (String[] rowData : rows) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(idx++);
                row.createCell(1).setCellValue(rowData[0]);
                row.createCell(2).setCellValue(rowData[1]);
                row.createCell(3).setCellValue(rowData[2]);
            }
            for (int i = 0; i < 4; i++) sheet.autoSizeColumn(i);
            try (FileOutputStream out = new FileOutputStream(filename)) {
                wb.write(out);
            }
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

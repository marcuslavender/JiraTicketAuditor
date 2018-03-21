package com.bgch.JiraTicketLogger;

import java.io.*;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.*;


public abstract class SpreadSheet {

    private String excelFilePath;
    private static String defaultExcelFilePath;
    private String sheetName;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private InputStream fileInput;
    private OutputStream fileOutput;
    private String worksheetName;


    protected SpreadSheet() {

        this.sheetName = null;
        this.excelFilePath = null;
        SpreadSheet.defaultExcelFilePath = "/Users//Documents/ISTicketTally.xls";
    }

    protected static String getDefaultExcelFileName() {
        return SpreadSheet.defaultExcelFilePath;
    }

    protected String getExcelFilePath() {
        return this.excelFilePath;
    }

    protected String getSheetName() {
        return this.sheetName;
    }

    protected HSSFSheet getSheet() {
        return this.sheet;
    }

    protected HSSFWorkbook getWorkbook() {
        return this.workbook;
    }

    protected String getWorkSheetName()
    {
        return this.sheetName;
    }

    protected void setExcelFilePath(String aUsername) {
        this.excelFilePath = aUsername;
    }

    protected void setSheetName(String aSheetName) {
        this.sheetName = aSheetName;
    }

    protected void setHSSFSheet(HSSFSheet aSheet) {
        this.sheet = aSheet;
    }

    protected void setHSSFWorkbook(HSSFWorkbook aWorkbook) {
        this.workbook = aWorkbook;
    }

    protected InputStream getFileInputStream() {
        return this.fileInput;
    }


    protected void setFileInputStream(InputStream aInput) {
        this.fileInput = aInput;

    }

    protected void setFileOutputStream(OutputStream aOutput) {
        this.fileOutput = aOutput;

    }

    protected OutputStream getFileOutputStream() {
        return this.fileOutput;
    }


    protected void checkFileExists() {
        File f = new File(this.getExcelFilePath());
        if (f.exists() && !f.isDirectory())
        {
            System.out.println("File " + this.getExcelFilePath() + " Already exists do you wish to overwrite? y/n");
            Scanner input = new Scanner(System.in);
            while (input.nextLine().isEmpty())
            {
                System.out.println("Overwrite: y/n");
                if (input.nextLine().matches("[Yy]es|YES"))
                {
                    System.out.println("Ok, overwriting File");
                    f.delete();

                }
                else
                {
                    System.out.println("Ok, will now exit");
                    System.exit(0);

                }
            }
        }

    }
}







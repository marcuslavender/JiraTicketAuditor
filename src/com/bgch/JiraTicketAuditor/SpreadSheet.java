package com.bgch.JiraTicketAuditor;

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

    protected void setExcelFilePath(String aPathname) {
        this.excelFilePath = aPathname;
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
        String action = null;
        File f = new File(this.getExcelFilePath());
        if (f.exists() && !f.isDirectory())
        {
            System.out.println("File " + this.getExcelFilePath() + " Already exists do you wish to overwrite?");

            boolean loop = true;
            while (loop == true)
            {
                System.out.print("Overwrite: y/n ");
                Scanner s = new Scanner(System.in);
                String input = s.nextLine();


                if (input.matches("[Yy]es|YES")) {
                    System.out.println("Ok, overwriting File");
                    f.delete();
                    action = "Overwrite";
                    loop = false;
                }
                else if(input.matches("[Nn]o|NO")) {
                    //System.out.println("Ok will save results to new sheet");
                    System.out.println("Ok will now exit");
                    System.out.println("");
                    action = "new sheet";
                    loop = false;
                    System.exit(0);
                }
                else {
                    System.out.println("Invalid answer, try again");
                }



            }
        }
        //return action;
    }
}







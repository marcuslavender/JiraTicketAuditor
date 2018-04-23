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


    protected String checkFileExists() {
        String actionToTake = "";
        File f = new File(this.getExcelFilePath());
        if (f.exists() && !f.isDirectory())
        {
            System.out.println("File " + this.getExcelFilePath() + " Already exists do you wish to overwrite?");


            while (actionToTake.isEmpty()) {
                System.out.print("Overwrite: y/n ");
                Scanner s = new Scanner(System.in);
                String input = s.nextLine();


                if (input.matches("[Yy]es|YES")) {
                    System.out.println("Ok, overwriting File");
                    System.out.println("");
                    f.delete();
                    actionToTake = "create new workbook";

                } else if (input.matches("[Nn]o|NO")) {
                    //System.out.println("Ok will save results to new sheet");
                    System.out.println("Ok will now exit");
                    System.out.println("");
                    actionToTake = "new sheet";


                } else {
                    System.out.println("Invalid answer, try again");
                }




            }
        }
        else {
            System.out.println("No file exists will create a new one");
            actionToTake = "create new workbook";


        }

        return actionToTake;


    }

    /**
     *
     * Method which prompts user to enter name for the spreadsheet. Loops until it receives an input.
     * Sheetname must be less than 18 charachters
     */
    public void EnterSheetName() {
        boolean nameEntered = false;
        while (nameEntered == false) {
            System.out.println("");
            System.out.println("Enter a sheet name for Excel workbook related to the query - 18 character limit!");
            Scanner name = new Scanner(System.in);
            String sheetName = name.nextLine();
            if (sheetName.isEmpty()) {
                System.out.println("");
                System.out.println("No name entered!, enter a name related to the query");
            } else if (sheetName.length() > 18) {
                System.out.println("Sheet name is too long!");
            } else {
                this.setSheetName(sheetName);
                nameEntered = true;
            }

        }
    }
}







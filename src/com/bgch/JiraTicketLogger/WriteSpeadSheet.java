package com.bgch.JiraTicketLogger;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

public class WriteSpeadSheet extends SpreadSheet
{




    public WriteSpeadSheet()
    {
        super();

    }



    public void createFileOutputStream()
    {
        try {
            this.setFileOutputStream(new FileOutputStream(this.getExcelFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void createWorkBook()
    {

            HSSFWorkbook wb = new HSSFWorkbook();
            this.setHSSFWorkbook(wb);


    }

    public void createSheet(HSSFWorkbook aWorkbook)
    {
        HSSFSheet sheet = aWorkbook.createSheet(this.getSheetName());
        this.setHSSFSheet(sheet);
    }
}

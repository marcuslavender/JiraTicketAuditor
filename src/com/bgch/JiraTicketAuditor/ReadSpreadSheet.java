package com.bgch.JiraTicketAuditor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadSpreadSheet extends  SpreadSheet
{

    private HSSFRow row;
    private HSSFCell cell;


    public ReadSpreadSheet()
    {
        super();
        this.createFileInputStream();
    }

    protected HSSFCell getCell()
    {
        return this.cell;
    }

    protected void setCell(HSSFCell aCell)
    {
        this.cell = aCell;

    }

    protected HSSFRow getRow()
    {
        return this.row;
    }

    protected void setRow(HSSFRow aRow)
    {
        this.row = aRow;

    }

        protected void createFileInputStream()
        {
            try {
                this.setFileInputStream(new FileInputStream(this.getExcelFilePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }


        protected void createWorkBook()
        {
            try {
                HSSFWorkbook wb = new HSSFWorkbook(this.getFileInputStream());
                this.setHSSFWorkbook(wb);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        protected void createSheet(HSSFWorkbook aWorkbook)
        {
            HSSFSheet sheet = aWorkbook.getSheetAt(0);
            this.setHSSFSheet(sheet);
        }



    }


package com.bgch.JiraTicketAuditor;



import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.*;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.io.*;
import java.util.*;

//import static org.apache.poi.ss.usermodel.WorkbookFactory.*;


/**
 * Created by marcus.lavender on 11/02/2018.
 *
 */
public class Main {



    public static void main(String[] args) {


        //Create  User Credentials object
        UserCredentials credentials = new UserCredentials();

        //Get Username/Password from user
        credentials.setUsername();
        credentials.setPassword();

        //Create user options object
        Options option = new Options();


        //Ask user what option they would like to do
        option.setOption();

        switch (option.getOption()) {
            case "Search":

                /**
                 *
                 * Create objects used to authenticate to Jira
                 */
                //Create JiraRestClient Authentication handler
                JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();

                // Create Jira Authentication object
                JiraAuthentication auth = new JiraAuthentication(Options.hostname);
                auth.setCreateFromFactory(factory);

                //Create Jira API handle
                auth.createJiraRestApiHandle(credentials.getUsername(), credentials.getPassword());


                /**
                 *
                 * Objects used to search Jira
                 */

                //Create Jira Search object
                JiraSearch search = new JiraSearch();

                //Ask user for fields to search for: options - Navigable , All, done
                //Typically only Navigable is needed.
                search.fieldsToReturn();
                //Populates a hashmap with a list of queries that can be searched for.
                search.setQueries();
                // Give the user the options of queries that can be searched for
                search.chooseSearchQuery();
                // Searches Jira and returns results in 'searchQuery' variable
                search.setSearchQueryResults(auth.getClient(), search.getSearchChoice());


                /**
                 *
                 * Get individual tickets from the result of the search query
                 *
                 */

                System.out.println();
                System.out.println("");
                System.out.println("=============================================");
                System.out.println("Now extracting ticket info from search Result");


                // Calls the search query and captures results
                SearchResult result = search.getSearchQueryResults().claim();

                Iterator<Issue> it = result.getIssues().iterator();
                while (it.hasNext()) {
                    String issues = it.next().getKey();
                    System.out.println(issues);
                }


                /**
                 *
                 * Write the results to spreadsheet
                 *
                 */


                System.out.println("");


                String answer = "";
                while (answer.isEmpty()) {
                    System.out.println("Write results to file? : Enter y/n");
                    Scanner s = new Scanner(System.in);
                    String input = s.nextLine();


                    if (input.matches("[Yy]|[Yy]es|YES")) {
                        System.out.println("Ok, now writing to file");
                        System.out.println("");
                        answer = input;

                    } else if (input.matches("[Nn]|[Nn]o|no")) {
                        System.out.println("Ok, Will now exit");
                        System.exit(0);
                    } else {
                        System.out.println("Invalid Option entered, try again");
                    }
                }

                // Ask user to enter a file path/name to write file to
                // if not specified then default directory /Users/<username>/Documents/ISTicketTally.xls will be used.
                System.out.println("Enter path to save file, e.g. /Users/<username>/Documents/ISTicketTally.xls");

                //Create spreadsheet object
                WriteSpeadSheet writeToSpeadSheet = new WriteSpeadSheet();

                //Builds Default Excel file path using username provided for Jira as this should match Mac username

                String defaultPath = new StringBuilder(SpreadSheet.getDefaultExcelFileName()).insert(7, credentials.getUsername()).toString();
                System.out.println("Press 'return' for Default Path. Default Path is: " + defaultPath);
                System.out.println("===================================================");
                System.out.println("");

                //Checks if a Path is entered otherwise sets default path
                Scanner p = new Scanner(System.in);
                String path = p.nextLine();
                if (path.isEmpty()) {
                    writeToSpeadSheet.setExcelFilePath(defaultPath);


                } else {

                    writeToSpeadSheet.setExcelFilePath(path);
                }


                // Check if there is an existing excel file and if there is add a new worksheet to the workbook.
                // Otherwise ask the user if they wish to Overwrite the existing workbook.


                WriteSpeadSheet existingWorkbook = new WriteSpeadSheet();
                existingWorkbook.setExcelFilePath(writeToSpeadSheet.getExcelFilePath());
                if (existingWorkbook.checkFileExists().equals("new sheet")) {

                    File file = new File(existingWorkbook.getExcelFilePath());
                    try {
                        existingWorkbook.setFileOutputStream(new FileOutputStream(file));

                        if (file.exists()) {
                            try {
                                existingWorkbook.setHSSFWorkbook((HSSFWorkbook) org.apache.poi.ss.usermodel.WorkbookFactory.create(file));
                                HSSFSheet sheet = existingWorkbook.getWorkbook().createSheet(option.getDate() + " - " + existingWorkbook.getSheetName());

                                // Iterates over results and returns issue key
                                ArrayList<String> keyArray = new ArrayList<>();
                                ArrayList<String> summaryArray = new ArrayList<>();
                                ArrayList<String> statusArray = new ArrayList<>();
                                ArrayList<DateTime> timeArray = new ArrayList<>();
                                ArrayList<String> typeArray = new ArrayList<>();
                                ArrayList<String> priorityArray = new ArrayList<>();
                                ArrayList<DateTime> createdDateArray = new ArrayList<>();


                                Iterator<Issue> itr = result.getIssues().iterator();
                                while (itr.hasNext()) {
                                    int count = 0;
                                    Issue issue = itr.next();
                                    String issues = issue.getKey();
                                    String summary = issue.getSummary();
                                    String status = issue.getStatus().getName();
                                    String issueType = issue.getIssueType().getName();
                                    String priority = issue.getPriority().getName();
                                    DateTime createdDate = issue.getCreationDate().toDateTime();
                                    DateTime updatedDate = issue.getUpdateDate().toDateTime();




                                    System.out.println(issues + " " + ": " + summary + " - " + status);
                                    System.out.println("");


                                    keyArray.add(count, issues);
                                    summaryArray.add(count, summary);
                                    statusArray.add(count, status);
                                    timeArray.add(count, updatedDate);
                                    typeArray.add(count,issueType);
                                    priorityArray.add(count,priority);
                                    createdDateArray.add(count,createdDate);

                                    count = count + 1;
                                }

                                for (int i = 0; i < keyArray.size(); i++) {
                                    HSSFRow header = existingWorkbook.getSheet().createRow(0);
                                    header.createCell(0).setCellValue("Number");
                                    header.createCell(1).setCellValue("Ticket ID");
                                    header.createCell(2).setCellValue("Summary");
                                    header.createCell(3).setCellValue("Status");
                                    header.createCell(4).setCellValue("Updated Date");
                                    header.createCell(5).setCellValue("Created Date");
                                    header.createCell(6).setCellValue("Priority");
                                    header.createCell(7).setCellValue("Issue Type");


                                    HSSFRow row = existingWorkbook.getSheet().createRow(i + 1);

                                    HSSFCell cell = row.createCell(0);
                                    HSSFCell cell2 = row.createCell(1);
                                    HSSFCell cell3 = row.createCell(2);
                                    HSSFCell cell4 = row.createCell(3);
                                    HSSFCell cell5 = row.createCell(4);
                                    HSSFCell cell6 = row.createCell(5);
                                    HSSFCell cell7 = row.createCell(6);
                                    HSSFCell cell8 = row.createCell(7);


                                    cell.setCellValue(i);
                                    cell2.setCellValue(keyArray.get(i));
                                    cell3.setCellValue(summaryArray.get(i));
                                    cell4.setCellValue(statusArray.get(i));

                                    // Create Date format object
                                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy MMM dd");
                                    // Use format object to format Date and convert to string
                                    cell5.setCellValue(timeArray.get(i).toString(formatter));
                                    cell6.setCellValue(createdDateArray.get(i).toString(formatter));
                                    cell7.setCellValue(priorityArray.get(i));
                                    cell8.setCellValue(typeArray.get(i));
                                }

                                try {
                                    HSSFRow total = existingWorkbook.getSheet().createRow(keyArray.size() + 2);
                                    total.createCell(0).setCellValue("Total");
                                    total.createCell(1).setCellValue(result.getTotal());
                                    writeToSpeadSheet.checkFileExists();


                                    writeToSpeadSheet.createFileOutputStream();
                                    writeToSpeadSheet.getWorkbook().write(writeToSpeadSheet.getFileOutputStream());
                                    writeToSpeadSheet.getFileOutputStream().flush();
                                    writeToSpeadSheet.getFileOutputStream().close();
                                    //}

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            } catch (Exception fileInput) {
                                fileInput.getMessage();
                            }

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else
                {

                    /**
                     *
                     *Overwrite existing file / create new sheet
                     *
                     */



                    // Create workbook
                    writeToSpeadSheet.createWorkBook();

                    //Set Sheetname to today's date
                    option.setDate();

                    //Ask user for a name for the worksheet


                    writeToSpeadSheet.EnterSheetName();



                    writeToSpeadSheet.setSheetName(option.getDate() + " - " + writeToSpeadSheet.getSheetName());


                    //Create sheet in workbook
                    writeToSpeadSheet.createSheet(writeToSpeadSheet.getWorkbook());

                    // Iterates over results and returns issue key
                    ArrayList<String> keyArray = new ArrayList<>();
                    ArrayList<String> summaryArray = new ArrayList<>();
                    ArrayList<String> statusArray = new ArrayList<>();
                    ArrayList<DateTime> timeArray = new ArrayList<>();
                    ArrayList<String> typeArray = new ArrayList<>();
                    ArrayList<String> priorityArray = new ArrayList<>();
                    ArrayList<DateTime> createdDateArray = new ArrayList<>();


                    Iterator<Issue> itr = result.getIssues().iterator();
                    while (itr.hasNext()) {
                        int count = 0;
                        Issue issue = itr.next();
                        String issues = issue.getKey();
                        String summary = issue.getSummary();
                        String status = issue.getStatus().getName();
                        DateTime date = issue.getUpdateDate().toDateTime();
                        String issueType = issue.getIssueType().getName();
                        String priority = issue.getPriority().getName();
                        DateTime createdDate = issue.getCreationDate().toDateTime();
                        System.out.println(issues + " " + ": " + summary + " - " + status);
                        System.out.println("");


                        keyArray.add(count, issues);
                        summaryArray.add(count, summary);
                        statusArray.add(count, status);
                        timeArray.add(count, date);
                        typeArray.add(count,issueType);
                        priorityArray.add(count,priority);
                        createdDateArray.add(count,createdDate);
                        count = count + 1;
                    }

                    for (int i = 0; i < keyArray.size(); i++) {
                        HSSFRow header = writeToSpeadSheet.getSheet().createRow(0);
                        header.createCell(0).setCellValue("Number");
                        header.createCell(1).setCellValue("Ticket ID");
                        header.createCell(2).setCellValue("Summary");
                        header.createCell(3).setCellValue("Status");
                        header.createCell(4).setCellValue("Updated Date");
                        header.createCell(5).setCellValue("Created Date");
                        header.createCell(6).setCellValue("Priority");
                        header.createCell(7).setCellValue("Issue Type");


                        HSSFRow row = writeToSpeadSheet.getSheet().createRow(i + 1);

                        HSSFCell cell = row.createCell(0);
                        HSSFCell cell2 = row.createCell(1);
                        HSSFCell cell3 = row.createCell(2);
                        HSSFCell cell4 = row.createCell(3);
                        HSSFCell cell5 = row.createCell(4);
                        HSSFCell cell6 = row.createCell(5);
                        HSSFCell cell7 = row.createCell(6);
                        HSSFCell cell8 = row.createCell(7);


                        cell.setCellValue(i);
                        cell2.setCellValue(keyArray.get(i));
                        cell3.setCellValue(summaryArray.get(i));
                        cell4.setCellValue(statusArray.get(i));

                        // Create Date format object
                        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy MMM dd");
                        // Use format object to format Date and convert to string
                        cell5.setCellValue(timeArray.get(i).toString(formatter));
                        cell6.setCellValue(createdDateArray.get(i).toString(formatter));
                        cell7.setCellValue(priorityArray.get(i));
                        cell8.setCellValue(typeArray.get(i));
                    }

                    try {
                        HSSFRow total = writeToSpeadSheet.getSheet().createRow(keyArray.size() + 2);
                        total.createCell(0).setCellValue("Total");
                        total.createCell(1).setCellValue(result.getTotal());
                        //writeToSpeadSheet.checkFileExists();

                        writeToSpeadSheet.createFileOutputStream();
                        writeToSpeadSheet.getWorkbook().write(writeToSpeadSheet.getFileOutputStream());
                        writeToSpeadSheet.getFileOutputStream().flush();
                        writeToSpeadSheet.getFileOutputStream().close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }





                        /**
                    //Read spreadsheet to get column total





                        ReadSpreadSheet readSpreadSheet = new ReadSpreadSheet();
                        readSpreadSheet.createWorkBook();
                        HSSFSheet sheet = readSpreadSheet.getWorkbook().getSheet(writeToSpeadSheet.getSheetName());
                        HSSFRow readRow;
                        HSSFCell readCell;

                        Iterator rows = readSpreadSheet.getSheet().;

                        while (rows.hasNext())
                        {
                              rows.next();
                             Iterator cells = readSpreadSheet.getRow().cellIterator();

                             while (cells.hasNext())
                             {
                                 cells.hasNext();

                                 if(readSpreadSheet.getCell().getCellType() == HSSFCell.CELL_TYPE_STRING)
                                 {
                                     System.out.print(readSpreadSheet.getCell().getStringCellValue());

                                 }
                                 else if ( readSpreadSheet.getCell().getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                                 {
                                     System.out.print(readSpreadSheet.getCell().getNumericCellValue());
                                 }
                                 else
                                 {
                                     System.out.print("Cell type is not a String or Integer value");
                                 }
                             }

                             System.out.println();
                        }


                    } else
                    {
                        System.exit(0);
                    }

                    */



                case "create":
                    break;

                case "delete":
                    break;


            }

        }
    }



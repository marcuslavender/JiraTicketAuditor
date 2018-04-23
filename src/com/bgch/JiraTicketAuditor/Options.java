package com.bgch.JiraTicketAuditor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

    /**
     * Created by marcus.lavender on 11/02/2018.
     */
    public class Options {


        private String command;
        private boolean runAgain;
        private String todaysDate;
        public static final String hostname = "http://jira.bgchtest.info";


        public Options()
        {
            this.command = null;
            this.runAgain = true;
            this.todaysDate = null;

        }



        /**
         * Method to get API request from user
         * @return this.command - Ask user what API request they would like to perform.
         * Only search is working currently
         */
        protected String getOption()
        {
            return this.command;
        }

        /**
         * Method to set API request from user
         */
        protected void setOption() {
            System.out.println("Choose corresponding number for API request");
            System.out.println("===========================================");

            List<String> options = new ArrayList<>();
            options.add("Search");
            options.add("Create - not yet working!");
            options.add("delete - not yet working!");

            int count = 1;
            for(String element : options)
            {
                System.out.println(count + ":" + element.toString());
                count += 1;
            }

            //String[] APIOption = new String[4];
            //APIOption[1] = "search";
            //APIOption[2] = "create - Not yet working";
            //APIOption[3] = "delete - Not yet working";

            //for (int i = 1; i < APIOption.length; i++) {
              //  System.out.print(i + ":");
                //System.out.println(APIOption[i]);
            //}
            String answer = "";
            while(answer.isEmpty()) {
                System.out.println("");
                System.out.print("Enter an option:" + " ");

                try {
                    Scanner s = new Scanner(System.in);
                    String input = s.nextLine();
                    if (Integer.parseInt(input) > (count - 1)) {
                        System.out.println("Invalid option");
                    } else {
                        this.command = options.get(Integer.parseInt(input) - 1);
                        answer = this.command;
                    }
                } catch (NumberFormatException anException) {
                    anException.printStackTrace();
                }
            }
        }

        /**
         * Method to ask user if they wish to perform another query
         *
         */
        protected void setRunAgain() {
            String again = "";
            System.out.println("Would you like to run another query?: y/n ");

            while(again.isEmpty()) {

                Scanner input = new Scanner(System.in);
                again = input.nextLine();

            }

            if (again.equals("yes")) {
                    this.runAgain = true;
                }

                else if (again.equals("no"))
                {
                    this.runAgain = false;
                }
                else {
                    System.out.println("Invalid input, try again");
            }

            }



        /**
         * Method to get runAgain variable
         *
         * @return this.runAgain
         */
        public boolean getRunAgain()
        {
            return this.runAgain;
        }

        /**
         *
         * Method to set today's date to this.date variable
         *
         */
        public void setDate()
        {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            this.todaysDate = dateFormatter.format(today.getTime());

        }

        /**
         *
         * Method to get Today's Date
         * @return this.todaysDate - Current days date
         */
        public String getDate()
        {
            return this.todaysDate;
        }

    }


package com.bgch.JiraTicketLogger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

    /**
     * Created by marcus.lavender on 11/02/2018.
     */
    public class Options {


        private String command;
        private boolean runAgain;
        private String todaysDate;


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

            String[] APIOption = new String[4];
            APIOption[1] = "search";
            APIOption[2] = "create - Not yet working";
            APIOption[3] = "delete - Not yet working";

            for (int i = 1; i < APIOption.length; i++) {
                System.out.print(i + ":");
                System.out.println(APIOption[i]);
            }

            Scanner s = new Scanner(System.in);
            this.command = APIOption[s.nextInt()];
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


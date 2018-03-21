package com.bgch.JiraTicketLogger;
import java.util.Scanner;

    /**
     * Created by marcus.lavender on 11/02/2018.
     */
    public class UserCredentials
    {

        private String username;
        private String password;

        UserCredentials()
        {

            username = null;
            password = null;

        }


        /**
         * Public method to get username
         * @return this.username
         */
        public String getUsername()
        {
            return this.username;
        }


        /**
         * Public method to set username
         *
         */
        public void setUsername()
        {
            System.out.println("Enter Jira  username:");
            Scanner s = new Scanner(System.in);
            this.username = s.next();

        }


        /**
         * Public method to get password
         * @return this.password - users password
         */
        protected String getPassword()

        {
            return this.password;
        }



        /**
         * Public method to set password
         *
         */
        public void setPassword()
        {
            System.out.println("Enter Jira  password: ");
            Scanner s = new Scanner(System.in);
            this.password = s.next();
        }



    }




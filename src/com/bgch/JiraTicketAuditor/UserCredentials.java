package com.bgch.JiraTicketAuditor;
import java.io.Console;
import java.io.IOError;
import java.io.IOException;
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
            String answer = "";
            while(answer.isEmpty())
            {
                System.out.println("Enter Jira  username:");
                Scanner s = new Scanner(System.in);
                answer = s.nextLine();
            }
                this.username = answer;

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
            /**


            String pass = "";
            Console cons = System.console();

            while(pass.isEmpty())
            {


                System.out.println("Enter Jira  password: ");

                try {
                    char[] pwd = cons.readPassword();
                    password = new String(pwd);
                }
                catch(IOError anException)
                {
                    System.out.println(anException.getMessage());
                }
            }

            this.password = pass;

            }
             */
            this.password = "Pccall1966!";
        }

    }






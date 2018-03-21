package com.bgch.JiraTicketAuditor;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;

import java.util.*;


    /**
     * Created by marcus.lavender on 11/02/2018.
     */
    public class JiraSearch

    {
        private Promise<SearchResult> searchQueryResults;
        private Set<String> fields = new HashSet<>();
        private String[] fieldToSearch;
        private Map<String,String> queries = new LinkedHashMap<String,String>();
        private String searchChoice;
        private String[] keys = new String[6];






        public JiraSearch()

        {
            this.fieldToSearch = new String[4];
            this.searchQueryResults = null;
            this.searchChoice = null;



        }

        /**
         *
         * Returns fields variable of the type Set
         *
         */
        private Set<String> getFields()
        {
            return fields;
        }

        /**
         *
         * Add field names to Set fields
         * @param aField - Can be either 'Navigable' or 'All'
         */
        private void setFields(String aField)
        {

            this.fields.add(aField);

            Iterator<String> itr = this.fields.iterator();
            while(itr.hasNext())
            {
                System.out.println(itr.next());
            }


        }


        /**
         *
         * Method to set String searchChoice
         * @param  aSearchChoice - JQL query to search
         */
        private void setSearchChoice(String aSearchChoice)
        {
            this.searchChoice = aSearchChoice;
        }





        /**
         * Method to return searchQuery Variable
         *
         * @return this.searchQueryResults  returns the search results object which contains all the different issues
         */
        protected Promise<SearchResult> getSearchQueryResults()
        {
            return this.searchQueryResults;
        }


        /**
         * Method to set this.searchQuery variable
         *
         * @param handle - requires a Jira rest client object handle,  and JQL query string.
         *  Method which initiates connection to Jira to perform the query and return the results as a Promise<SearchResult>
         *  object
         *
         */
        protected void setSearchQueryResults(JiraRestClient handle, String aSearchChoice)

        {
            this.searchQueryResults = this.searchJira(handle, aSearchChoice);

        }

        /**
         *
         * Method to get Query keys array
         * @return this.keys - returns a 'keys' array which contains only the integer value of the key used for the
         * Jira query
         */
        protected String[] getQueryKeys()
        {
            return this.keys;
        }


        /**
         *
         * Method to return 'queries' hashmap
         * This contains Query description as the key and JQL syntax query as the value
         */
        protected Map getQueries()
        {
            return this.queries;
        }


        /**
         *
         * Method to get the queries stored in searchChoice variable
         *
         */
        protected String getSearchChoice()
        {
            return this.searchChoice;
        }


        /**
         * Method using Jira Search Client to connect to Jira  API and return results in object of type Promise<SearchResult>
         * @param handle aSearchChoice -  Requires a Jira Rest Client handle object, and a  String JQL query search choice
         * @return result
         */
        private Promise<SearchResult> searchJira(JiraRestClient handle, String aSearchChoice) {

            SearchRestClient searchClient = handle.getSearchClient();
            Promise<SearchResult> result = searchClient.searchJql(aSearchChoice, 100, 0, this.getFields());

            return result;

        }

        /**
         *
         * Method which lists the choice of searches the user can make which are stored in a
         *  Hashmap queries.
         *
         */
        protected void chooseSearchQuery() {
            //Print out the list of options
            System.out.println("Choose a search query from the list below:");
            System.out.println("==========================================");

            int count = 1;
            for (Object key : this.getQueries().keySet()) {
                System.out.println(key);   // + this.getQueries().get(key)) - shows the JQL syntax
                keys[count] = key.toString().substring(0,1);
                count = count + 1;
            }

            System.out.println("");
            System.out.println("Enter a number : ");
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();


            while(!input.isEmpty()) {
                String answer = null;

                if (input.equals(keys[1])) {
                    answer = this.getQueries().get("1: Tickets with TSD, updated today").toString();
                    this.setSearchChoice(answer);
                    break;
                }

                else if(input.equals(keys[2])) {
                     answer = this.getQueries().get("2: Tickets with BAU, updated today").toString();
                     this.setSearchChoice(answer);
                     break;
                    }

                 else {
                    System.out.println("Invalid input");
                    System.exit(0);
                }
            }


            //loop until input is valid

               // switch (input) {
                 //   case 1:
                   //     String answer = ;
                     //   this.setSearchChoice(answer);
                       // break;

                    //default:
                      //  System.out.println("Invalid input, please try again");
                        //break;


              //  }

            }



        /**
         *
         * Method which populates the queries hashmap with the available Jira searches which are
         *
         */
        protected void setQueries()
        {
            //this.queries.put("0:", new String("previous"));
            //this.queries.put("1: Tickets updated today :", new String("project= \"IS Support\" AND status = \"With TSD\"  AND updatedDate >= startOfDay() ORDER BY createdDate DESC"));
            //this.queries.put("2:", new String("next"));

            this.queries.put("1: Tickets with TSD, updated today","project= \"IS Support\" AND status = \"With TSD\"  AND updatedDate >= startOfDay() ORDER BY createdDate DESC");
            this.queries.put("2: Tickets with BAU, updated today", "project= \"IS Support\" AND status = \"With BAU\" AND updatedDate >= startOfDay() ORDER BY createdDate DESC");
            //add subsequent queries here
        }



        /**
         *
         * Ask user which fields to search for currently only navigable and default are available
         * @return fieldToSearch - Navigable, All, done
         *
         */
        private int fieldsToReturn()
        {

            System.out.println();
            System.out.println("Choose fields to search for by entering corresponding number");
            System.out.println("===========================================================");


            this.fieldToSearch[1] = " *navigable";
            this.fieldToSearch[2] = " *all";
            this.fieldToSearch[3] = " done";

            for (int i = 1; i < fieldToSearch.length; i++) {
                System.out.println(i + ": " + this.fieldToSearch[i]);


            }
            System.out.println();
            System.out.println();

            Scanner s = new Scanner(System.in);
            int answer = s.nextInt();
            return answer;

        }


        /**
         *
         * Loop to ask user which fields to search for
         *  and adds the selection by sending message to addFieldsToSet
         *  If user chooses 3 then it breaks the loop
         *
         */
        protected void loopAskForFields()
        {
            System.out.println("Choose a field or  'done' to finish");
            boolean loop = true;

            while(loop)
            {
                int answer;
                answer = this.fieldsToReturn();
               if(answer == 3)
               {
                   loop = false;

               } else
               {
                   this.setFields(this.fieldToSearch[answer]);
               }

            }

        }


    }


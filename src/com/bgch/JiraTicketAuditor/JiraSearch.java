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
        private Map<String,String> queries;
        private String searchChoice;
        private String[] keys = new String[6];









        public JiraSearch()

        {
            this.fieldToSearch = new String[4];
            this.searchQueryResults = null;
            this.searchChoice = null;
            this.queries = new LinkedHashMap<>();




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
         * Method to set this.searchQueryResults variable
         * @param aSearchChoice - JQL query string.
         * @param handle - requires a Jira rest client object handle, of type  JiraRestClient
         *  Method which initiates connection to Jira to perform the query and return the results as a Promise object of type SearchResult
         *  object
         *
         */
        protected void setSearchQueryResults(JiraRestClient handle, String aSearchChoice)

        {
            this.searchQueryResults = this.searchJira(handle, aSearchChoice);

        }




        /**
         *
         * Method to return 'queries' hashmap
         * @return this.queries -
         */
        protected Map getQueries()
        {
            return this.queries;
        }


        /**
         * @return this.searchChoice - Method to return the queries stored in searchChoice variable
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
                System.out.println(key);
                  // + this.getQueries().get(key)) - shows the JQL syntax
                keys[count] = key.toString().substring(0,1);
                count = count + 1;
            }
            System.out.println("");


            String answer = "";
            //boolean loop = true;
            while (answer.isEmpty()) {


                System.out.print("Enter a number : ");
                Scanner s = new Scanner(System.in);
                String input = s.nextLine();





                    if (input.equals(keys[1])) {







                        answer = this.getQueries().get("1: Tickets with TSD, updated today").toString();
                        this.setSearchChoice(answer);
                        //loop = false;
                    }



                        else if(input.equals(keys[2])) {
                        answer = this.getQueries().get("2: Tickets with BAU, updated today").toString();
                        this.setSearchChoice(answer);
                        //loop = false;

                    }


                        else if(input.equals(keys[3])) {
                        answer = this.getQueries().get("3: Tickets with status changed by TSD updated or created in the last week").toString();
                        this.setSearchChoice(answer);
                        //loop = false;
                    } else {
                        System.out.println("Invalid input, try again");

                    }
                }


            }






        /**
         *
         * Method which populates the queries hashmap with the available Jira searches
         *
         */
        protected void setQueries()
        {



            this.queries.put("1: Tickets with TSD, updated today","project= \"IS Support\" AND status = \"With TSD\"  AND updatedDate >= startOfDay() ORDER BY createdDate DESC");
            this.queries.put("2: Tickets with BAU, updated today","project= \"IS Support\" AND status = \"With BAU\" AND updatedDate >= startOfDay() ORDER BY createdDate DESC");
            this.queries.put("3: Tickets with status changed by TSD updated or created in the last week","project = \"IS Support\" AND status changed BY membersOf(\"TSD Team\") AND (created >= startOfWeek() OR updated >= startOfWeek())");
            //add subsequent queries here
        }



        /**
         *
         * Ask user which fields to search for currently only navigable and default are available. These are added to a 'Set' called fields
         * fieldToSearch - Navigable, All, done
         *
         */
        protected void fieldsToReturn() {

            System.out.println();
            System.out.println("Choose fields to search for by entering corresponding number");
            System.out.println("===========================================================");


            this.fieldToSearch[1] = " *navigable";
            this.fieldToSearch[2] = " *all";
            //this.fieldToSearch[3] = " done";

            for (int i = 1; i < fieldToSearch.length; i++) {
                System.out.println(i + ": " + this.fieldToSearch[i]);


            }
            System.out.println();
            System.out.println();



            String answer = "";
            while (answer.isEmpty()) {
                System.out.print("Enter a number: ");
                Scanner s = new Scanner(System.in);
                String input = s.nextLine();

                if (input.matches("1|2"))
                {
                    System.out.println("You chose option " + input);
                    this.setFields(this.fieldToSearch[Integer.parseInt(input)]);
                    answer = input;
                    System.out.println("");

                }
                else {
                    System.out.println("Invalid option entered, try again");
                    System.out.println("");
                }
            }
        }

    }


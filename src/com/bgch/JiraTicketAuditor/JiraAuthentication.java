package com.bgch.JiraTicketAuditor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;


public class JiraAuthentication
{
    private String jiraHostname;
    private JiraRestClientFactory createFromFactory;
    private JiraRestClient client;


    public JiraAuthentication(String aHostname)
    {

        this.jiraHostname = aHostname;



    }


    /**
     *
     * Method to get Jira hostname
     * @return this.hostname
     */
    public String getHostname()
    {
        return this.jiraHostname;
    }

    /**
     *
     * Method to set Jira Hostname
     *
     */
    public void setHostname()
    {
        System.out.println("Enter a hostname");
        Scanner s = new Scanner(System.in);
        while(!s.hasNext())
        {
            this.jiraHostname = s.next();
        }

    }


    /**
     *
     * Method to get createFromFactory variable
     *
     */
    public JiraRestClientFactory getCreateFromFactory()
    {
        return this.createFromFactory;
    }

    /**
     *
     * Method to set createFromFactory variable
     *
     */
    protected void setCreateFromFactory(JiraRestClientFactory aFactory)
    {
        this.createFromFactory = aFactory;
    }

    /**
     *
     * Method to get client
     * @return client
     */
    protected JiraRestClient getClient()
    {
        return this.client;
    }

    /**
     *
     * Method to set Jira Rest Client
     * @param aClient
     */
    private void setClient(JiraRestClient aClient)
    {
        this.client = aClient;
    }



    /**
     *
     * Method to create Jira Rest API Login Handle
     * @param username,password
     */
    protected void createJiraRestApiHandle(String username, String password)
    {

        URI uri = null;
        try {
            uri = new URI(Main.hostname);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        this.setClient(this.getCreateFromFactory().createWithBasicHttpAuthentication(uri, username, password ));








    }



}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.brax.OakmontFinanceGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * This class stores static functions for gathering financial data from across the interwebs.
 * @author Josh
 */
public class DataCapture {
    /**
     * Grabs financial data from Yahoo's webservers using YQL
     * @param Symbol
     * @param Start
     * @param End
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.MalformedURLException
     * @throws java.io.IOException
     * @throws org.json.JSONException
     * @author Frank Insana
     */
    public static StockData getStockDataYQL(String Symbol, String Start, String End) throws UnsupportedEncodingException, MalformedURLException, IOException, JSONException{
        String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=";
        String query = "select * from yahoo.finance.historicaldata where symbol = \"" + Symbol+ "\" and startDate = \"" + Start +"\" and endDate = \""+End+"\"";
        String fullUrlStr = baseUrl + URLEncoder.encode(query, "UTF-8") + "&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

        URL fullUrl = new URL(fullUrlStr);
        try{
            InputStream is = fullUrl.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            JSONTokener tok = new JSONTokener(out.toString());
            JSONObject result = new JSONObject(tok);
            JSONArray Quote = result.getJSONObject("query").getJSONObject("results").getJSONArray("quote");
            int counter=0;

            StockData arr = new StockData();
            for(int i=Quote.length()-1; i>=0; i--){
                String date = Quote.getJSONObject(i).getString("Date");
                double openCost = Quote.getJSONObject(i).getDouble("Open");
                double closeCost = Quote.getJSONObject(i).getDouble("Close");
                double highCost = Quote.getJSONObject(i).getDouble("High");
                double lowCost = Quote.getJSONObject(i).getDouble("Low");
                arr.setIndex(openCost,closeCost,highCost,lowCost, date,0);
                counter++;
            }
            return arr;
        }catch(IOException e){
            System.out.println("IO Excetion");
            return null;
        }
        
    }
}

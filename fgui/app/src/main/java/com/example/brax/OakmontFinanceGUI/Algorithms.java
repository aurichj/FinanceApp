package com.example.brax.OakmontFinanceGUI;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 * This class stores static functions for processing stock data in an attempt to make some $$$
 * @author Joshua Aurich
 * @author Kyle Butler
 */
public class Algorithms {

    /**
     * Runs our basic, highly inefficient trading algorithm against the data
     * @param symbol
     * @param tradeCost
     * @param investment
     * @param startDate
     * @param endDate
     * @return 
     * @author Joshua Aurich
     * @author Kyle Butler
     */
    public static double runAlgorithm2(final String symbol,double tradeCost, double investment, final String startDate, final String endDate){
        try {
            //the algorithm
            boolean exit = false;
            StockData yql  = DataCapture.getStockDataYQL(symbol,startDate,endDate);

            //YQLResults results = getStockData(symbol,startDate,endDate);            
            int yqlSize = 0;
            if(yql == null){
                exit = true;
                System.out.println("ERROR!!! Null results from YQL!");
            }
            if(!exit){
                yqlSize = yql.getSize();
                if(yqlSize < 3){
                    System.out.println("ERROR!!! NOT ENOUGH DATA!");
                    exit = true;
                }
            }
            if(!exit){
                double upperSell = 0.05;
                double lowerSell = 0.9;
                double lowerBuy = 0.80;
                MarketProfile stock = new MarketProfile(investment,tradeCost);
                stock.buy(yql.getOpen(0),yql.getDate(0));
                stock.setBounds(yql.getOpen(0), upperSell, lowerSell);
                for(int i = 1; i < yqlSize; i++){
                    if(stock.inTheMarket()){
                        if(yql.getLow(i) <= stock.getLowerBound()){
                            stock.sell(stock.getLowerBound(),yql.getDate(i));
                            stock.setBounds(stock.getLowerBound(), upperSell, lowerBuy);
                        }else if(yql.getHigh(i) >= stock.getUpperBound()){
                            stock.sell(stock.getUpperBound(),yql.getDate(i));
                            stock.setBounds(stock.getUpperBound(), upperSell, lowerBuy);
                        }
                    }else{
                        if( yql.getLow(i) <= stock.getLowerBound()){
                            stock.buy(stock.getLowerBound(),yql.getDate(i));
                            stock.setBounds(stock.getLowerBound(), upperSell, lowerSell);
                        }
                    }
                }
                if(stock.inTheMarket()){
                    stock.sell(yql.getClose(yqlSize-1),yql.getDate(yqlSize-1));
                }
                return stock.getProfit(yql.getClose(yqlSize-1));
            }else{
                return -999999999;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Oakmont.class.getName()).log(Level.SEVERE, null, ex);
            return -999999999;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Oakmont.class.getName()).log(Level.SEVERE, null, ex);
            return -999999999;
        } catch (IOException ex) {
            Logger.getLogger(Oakmont.class.getName()).log(Level.SEVERE, null, ex);
            return -999999999;
        } catch (JSONException ex) {
            Logger.getLogger(Oakmont.class.getName()).log(Level.SEVERE, null, ex);
            return -999999999;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.brax.OakmontFinanceGUI;

/**
 * This class stores what is currently going on in the investment market.
 * Buy and sell values as well as the current profit of the investment are stored here.
 * @author Joshua Aurich
 */
public class MarketProfile {
    private double initialInvestment;
    private double currentInvestment;
    private double currentShares;
    private double tradeCost;
    private boolean inTheMarket;

    private double upperBound;
    private double lowerBound;

    public MarketProfile(double initialInvestment,double tradeCost){
        this.initialInvestment = initialInvestment;
        this.currentInvestment = initialInvestment;
        currentShares = 0;
        this.tradeCost = tradeCost;
        this.inTheMarket = false;
    }

    public void buy(double cost, String date){
        currentShares = (currentInvestment-tradeCost)/cost;
        System.out.println("Bought into market with "+currentShares+" shares at $"+cost+" on "+date);
        inTheMarket = true;
    }

    public void sell(double cost, String date){
        System.out.println("Sold out of the market for $"+getProfit(cost)+" profit at $"+cost+" per share on "+date);
        currentInvestment = -tradeCost + (currentShares*cost);
        currentShares = 0;
        inTheMarket = false;
    }

    public double getProfit(double cost){
        if(inTheMarket){
            return -tradeCost + (currentShares*cost) - currentInvestment;
        }else{
            return currentInvestment - initialInvestment;
        }
    }

    public void setBounds(double cost, double upPercent, double downPercent){
        upperBound = cost * (1 + upPercent);
        lowerBound = cost * downPercent;
        System.out.println("Setting bounds between "+upperBound+" and "+lowerBound);
    }

    public double getUpperBound(){
        return upperBound;
    }

    public double getLowerBound(){
        return lowerBound;
    }

    public boolean inTheMarket(){
        return inTheMarket;
    }
}
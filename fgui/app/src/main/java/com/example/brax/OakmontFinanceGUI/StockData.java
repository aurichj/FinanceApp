package com.example.brax.OakmontFinanceGUI;

import java.util.ArrayList;

/**
 * This class stores one timestamp worth of stock information.
 * @author Joshua Aurich
 */
public class StockData {
    private ArrayList<Double> open;
    private ArrayList<Double> close;
    private ArrayList<Double> high;
    private ArrayList<Double> low;
    private ArrayList<String> date;
    private ArrayList<Double> volume;
    private int size;

    public StockData(){
        open = new ArrayList<Double>();
        close = new ArrayList<Double>();
        high = new ArrayList<Double>();
        low = new ArrayList<Double>();
        date = new ArrayList<String>();
        volume = new ArrayList<Double>();
        size = 0;
    }

    public void setIndex(double openEntry, double closeEntry, double highEntry, double lowEntry, String dateEntry, double volumeEntry){
        open.add(openEntry);
        close.add(closeEntry);
        high.add(highEntry);
        low.add(lowEntry);
        date.add(dateEntry);
        volume.add(volumeEntry);
        size++;
    }

    public StockData getIndex(int index){
        StockData data = new StockData();
        data.setIndex(open.get(index),close.get(index),high.get(index),low.get(index),date.get(index),volume.get(index));
        return data;
    }

    public double getOpen(int index){
        return open.get(index);
    }

    public double getClose(int index){
        return close.get(index);
    }

    public String getDate(int index){
        return date.get(index);
    }

    public double getHigh(int index){
        return high.get(index);
    }

    public double getLow(int index){
        return low.get(index);
    }

    public double getVolume(int index){
        return volume.get(index);
    }

    public int getSize(){
        return size;
    }
}

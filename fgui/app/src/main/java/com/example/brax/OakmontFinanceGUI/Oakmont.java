package com.example.brax.OakmontFinanceGUI;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The main class which operates the simulation.
 * @author Braxon Tawatao
 */
public class Oakmont extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_sample);
        EditText Sym = (EditText) findViewById(R.id.editText5);
        EditText Cost = (EditText) findViewById(R.id.editText);
        EditText Invest = (EditText) findViewById(R.id.editText2);
        EditText StartDate = (EditText) findViewById(R.id.editText3);
        EditText EndDate = (EditText) findViewById(R.id.editText4);
        Sym.setText("IBM");
        Cost.setText("7.50");
        Invest.setText("10000");
        StartDate.setText("2014-01-07");
        EndDate.setText("2015-01-07");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonOnClick(View v) {
        String sym="";
        String cost="";
        String invest="";
        String startDate="";
        String endDate="";
        double result=0;
        double finalsum=0;

        //Button button=(Button) v;
        EditText Sym = (EditText) findViewById(R.id.editText5);
        EditText Cost = (EditText) findViewById(R.id.editText);
        EditText Invest = (EditText) findViewById(R.id.editText2);
        EditText StartDate = (EditText) findViewById(R.id.editText3);
        EditText EndDate = (EditText) findViewById(R.id.editText4);
        TextView startSum = (TextView) findViewById(R.id.textView6);
        TextView endSum = (TextView) findViewById(R.id.textView7);
        TextView profit = (TextView) findViewById(R.id.textView8);
        sym = Sym.getText().toString().toUpperCase();
        cost = Cost.getText().toString();
        invest = Invest.getText().toString();
        startDate = StartDate.getText().toString();
        endDate = EndDate.getText().toString();

        getStock runnable = new getStock(sym,startDate,endDate,Double.parseDouble(cost),Double.parseDouble(invest));
        Thread myThread = new Thread(runnable);
        myThread.start();
        boolean checkResults = false;
        while(!checkResults){

            if(runnable.isDone()){
                //System.out.println(runnable.getNumber());
                finalsum = runnable.getNumber();
                checkResults = true;
            }else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (finalsum > -Double.parseDouble(invest)-10) {
            startSum.setText("$" + invest);
            double endMoney = Double.parseDouble(invest)+finalsum;
            endSum.setText("$" + String.format( "%.2f", endMoney ));
            profit.setText("$" + String.format( "%.2f", finalsum ));
        }else{
            profit.setText("Cannot report results.");
        }

    }

    /**
     * Divides workload onto separate thread to release main thread.
     * @author Joshua Aurich
     */
    class getStock implements Runnable{
        private StockData results;
        private volatile boolean done;
        private String symbol;
        private String startDate;
        private String endDate;
        private double number;
        private double tradeCost;
        private double investment;
        public StockData getResults(){
            System.out.println("Returning: "+results.getSize());
            return results;
        }
        public boolean isDone(){
            return done;
        }

        public double getNumber(){
            return number;
        }
        public getStock(String sym, String sDate, String eDate, double cost, double investment){
            symbol = sym;
            startDate = sDate;
            endDate = eDate;
            done = false;
            tradeCost = cost;
            this.investment = investment;
        }

        @Override
        public void run(){
            number = Algorithms.runAlgorithm2(symbol,tradeCost,investment,startDate,endDate);
            done = true;
        }


    }
}
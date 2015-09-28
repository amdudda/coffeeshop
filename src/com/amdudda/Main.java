package com.amdudda;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        // DONE: read in data from coffee.txt
        /*
        Each product has a unique name, so we can use that
        as a key for a hashmap linking to an arraylist that
        stores the cost to make and sale price of each product
        index 0 will be the cost to make
        index 1 will be the price it's sold at
        index 2 will be the quantity sold that day for that product.
        */
        HashMap<String, ArrayList<Double>> productInfo = new HashMap<String, ArrayList<Double>>();
        fetchData(productInfo);

        // DONE: get data about quantities sold
        salesData(productInfo);

        /* debugging
        for (String key : productInfo.keySet()) {
            System.out.println(key + ": cost = $" + productInfo.get(key).get(0)
                    + " sale = $" + productInfo.get(key).get(1) + " units = "
                    + productInfo.get(key).get(2));
        }
        */

        // TODO: Generate sales report
        createReport(productInfo);

    }

    private static void createReport(HashMap<String, ArrayList<Double>> p_info) throws IOException {
        // takes data and generates report, stored as "sales-report.txt"
        // set up our data streams
        String fname = "./data/sales-report.txt";
        File f = new File(fname);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bufWrite = new BufferedWriter(fw);

        // TODO: report header

        // TODO: report body

        // TODO: report footer

        // close our data streams
        bufWrite.close();
        fw.close();
    }

    private static void salesData(HashMap<String, ArrayList<Double>> p_info) {
        // gets user input and appends to the array list for each product
        // initialize scanner for data input
        Scanner s = new Scanner(System.in);
        int d;  // will hold user input; set negative so it hits first while loop
        for (String key : p_info.keySet()) {
            // DONE: error handling & data validation
            // need to set d to be negative so it loops once
            d = -1; // reset d to be negative so it hits first while loop
            while (d < 0) {
                try {
                    System.out.println("How many units of " + key + " were sold today?");
                    d = s.nextInt();
                    if (d < 0) System.out.println("You entered a negative number. Please reenter.");
                } catch (InputMismatchException ime) {
                    System.out.println("You do not seem to have entered a whole number (no decimals).  Please try again.");
                    s = new Scanner(System.in);
                } catch (Exception e) {
                    System.out.println("Something seems to be wrong with your input.  Please try again.");
                    System.out.println(e.toString());
                    s = new Scanner(System.in);
                }  // end try-catch
            } // end while
            // we need to cast the int as a Double so we don't break the arraylist.
            p_info.get(key).add(Double.parseDouble(""+d));
        }

    }

    private static void fetchData(HashMap<String, ArrayList<Double>> p_info) throws IOException {
        // we want to read in data from coffee.txt,
        // so we'll need to set up data streams for that.
        // doesn't return anything because the hashmap is being passed by reference
        String fn = "./data/coffee.txt";
        File f = new File(fn);
        FileReader fr = new FileReader(f);
        BufferedReader bufRead = new BufferedReader(fr);

        // TODO: Exception handling for bad data
        // read in the first line of the file
        String line = bufRead.readLine();
        // while there is still data to read, add it to the hashmap
        while (line != null) {
            // index 0 will be the cost to make
            // index 1 will be the price it's sold at
            ArrayList<Double> money = new ArrayList<>();
            // decompose the data - notice it's still string data!
            String prod_name = line.substring(0, line.indexOf(";"));
            String cost_to_make = line.substring(line.indexOf(";") + 1, line.lastIndexOf(";"));
            String sale_price = line.substring(line.lastIndexOf(";") + 1);
            // add the cost & sale info to the arraylist - make sure to convert to Double type
            money.add(Double.parseDouble(cost_to_make));
            money.add(Double.parseDouble(sale_price));
            p_info.put(prod_name, money);
            // move to the next line
            line = bufRead.readLine();
        } // end while loop

        // close the data streams
        bufRead.close();
        fr.close();
    }  // end fetchData
}

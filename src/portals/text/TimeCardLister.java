/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portals.text;

import payroll.TimeCard;
import portals.DataDirectoryInitializer;
import portals.TimeCardFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Alonso del Arte
 */
public class TimeCardLister {
    
    public static ArrayList<TimeCard> getCards() 
            throws ClassNotFoundException, IOException {
        if (!DataDirectoryInitializer.hasBeenSet()) {
            DataDirectoryInitializer.setDir();
        }
        File dir = DataDirectoryInitializer.getDir();
        TimeCardFilter filter = new TimeCardFilter();
        File[] matches = dir.listFiles(filter);
        ArrayList<TimeCard> list = new ArrayList<>();
        for (File file : matches) {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            TimeCard card = (TimeCard) objStream.readObject();
            list.add(card);
        }
        return list;
    }
    
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Time Card Lister program, version 0.1");
        System.out.println();
        try {
            ArrayList<TimeCard> cards = getCards();
            System.out.println("Found " + cards.size() + " records...");
            cards.stream().map((card) -> {
                System.out.println();
                System.out.println("Name: " + card.getEmployee().getFullName());
                return card;
            }).map((card) -> {
                System.out.println("SSN: " + card.getEmployee().getFullName());
                return card;
            }).map((card) -> {
                System.out.println("Card period start: " + card.getStartTime());
                return card;
            }).map((card) -> {
                System.out.println("Card is current: " + card.isCurrent());
                return card; 
            }).map((card) -> {
                System.out.println("Card has been verified: "
                        + card.hasBeenVerified());
                return card;
            }).map((card) -> {
                System.out.println("Card has been paid: " + card.hasBeenPaid());
                return card; 
            }).map((card) -> {
                System.out.println("Card total time in minutes: "
                        + card.getMinutesSoFar());
                return card; 
            }).map((TimeCard card) -> {
                System.out.println("Card estimated pre-tax total: "
                        + card.getPreTaxTotal().toString());
                return card;
            });
        } catch (ClassNotFoundException cnfe) {
            System.out.println("ClassNotFoundException occurred...");
            System.out.println("\"" + cnfe.getMessage() + "\"");
            System.out.println("Please refer this to the programming team.");
        } catch (IOException ioe) {
            System.out.println(ioe.getClass().getName() + " occurred...");
            System.out.println("\"" + ioe.getMessage() + "\"");
        }
    }

}

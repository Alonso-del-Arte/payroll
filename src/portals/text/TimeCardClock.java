/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portals.text;

import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import payroll.TimeCard;
import portals.CurrentTimeCardFetcher;
import time.DateTimeRange;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The software equivalent of what is usually quite clumsily called a "time
 * clock."
 * @author Alonso del Arte
 */
public class TimeCardClock {

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Time Card Clock program, version 0.1");
        System.out.println();
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Please enter the last four of your SSN: ");
            String ssnStr = input.nextLine();
            int last4 = Integer.parseInt(ssnStr);
            Employee employee;
            try {
                ArrayList<Employee> records = EmployeeRecordsLister.getRecords(last4);
                switch (records.size()) {
                    case 0:
                        System.out.println("No matching records found, sorry...");
                        employee = Employee.getNullEmployee();
                        System.exit(0);
                        break;
                    case 1:
                        employee = records.get(0);
                        break;
                    default:
                        System.out.println("Multiple matches found");
                        for (int i = 0; i < records.size(); i++) {
                            Employee curr = records.get(i);
                            System.out.println("[" + (i + 1) + "] "
                                    + curr.getFullName() + " "
                                    + ((SocialSecurityNumber) curr.getTIN()).toRedactedString());
                        }
                        System.out.print("Which one? ");
                        String choiceStr = input.nextLine();
                        int sel = Integer.parseInt(choiceStr) - 1;
                        employee = records.get(sel);
                }
            } catch (ClassNotFoundException cnfe) {
                employee = Employee.getNullEmployee();
                System.out.println("ClassNotFoundException occurred while reading employee records...");
                System.out.println("\"" + cnfe.getMessage() + "\"");
                System.out.println("Please refer this to the programming team.");
            } catch (IOException ioe) {
                employee = Employee.getNullEmployee();
                System.out.println(ioe.getClass().getName() + " occurred while reading employee records...");
                System.out.println("\"" + ioe.getMessage() + "\"");
            }
            System.out.println("Pulling up time card for " + employee.getFullName());
            try {
                CurrentTimeCardFetcher fetcher = new CurrentTimeCardFetcher(employee);
                TimeCard card = fetcher.retrieveCard();
                ArrayList<DateTimeRange> blocks = card.getTimeBlocks();
                System.out.println("Completed time blocks (if any):");
                blocks.forEach((block) -> {
                    System.out.println(block.toString());
                });
                System.out.println("Total minutes: " + card.getMinutesSoFar());
                boolean punchStatusChange = false;
                System.out.print("You are currently ");
                if (card.isPunchedIn()) {
                    System.out.println("punched in. ");
                    System.out.print("Would you like to punch out? ");
                    String choice = input.nextLine();
                    if (choice.toLowerCase().startsWith("y")) {
                        card.punchOut();
                        punchStatusChange = true;
                        System.out.println("Completed time blocks:");
                        blocks = card.getTimeBlocks();
                        blocks.forEach((block) -> {
                            System.out.println(block.toString());
                        });
                    }
                } else {
                    System.out.println("punched out.");
                    System.out.print("Would you like to punch in? ");
                    String choice = input.nextLine();
                    if (choice.toLowerCase().startsWith("y")) {
                        card.punchIn();
                        punchStatusChange = true;
                        System.out.println("You are now punched in as of " 
                                + LocalTime.now());
                    }
                }
                if (punchStatusChange) {
                    fetcher.putCardBack(card);
                } else {
                    fetcher.putCardBack();
                }
            } catch (ClassNotFoundException cnfe) {
                System.out.println("ClassNotFoundException occurred while trying to fetch time card...");
                System.out.println("\"" + cnfe.getMessage() + "\"");
                System.out.println("Please refer this to the programming team.");
            } catch (IOException ioe) {
                System.out.println(ioe.getClass().getName() + " occurred while trying to fetch time card...");
                System.out.println("\"" + ioe.getMessage() + "\"");
            }
        }
    }

}

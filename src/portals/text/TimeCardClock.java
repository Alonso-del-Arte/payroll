/*
 * Copyright (C) 2020 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package portals.text;

import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import payroll.TimeCard;
import portals.CurrentTimeCardFetcher;
import portals.EmployeeRecordsProcessor;
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

    // TODO: Break down into smaller units
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Time Card Clock program, version 0.9");
        System.out.println();
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Please enter the last four of your SSN: ");
            String ssnStr = input.nextLine();
            int last4 = Integer.parseInt(ssnStr);
            Employee employee;
            try {
                ArrayList<Employee> records
                        = EmployeeRecordsProcessor.getRecords(last4);
                if (records.isEmpty()) {
                    System.out.println("No matching records found, sorry...");
                    System.exit(0);
                }
                if (records.size() == 1) {
                    employee = records.get(0);
                } else {
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

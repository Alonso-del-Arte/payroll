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

import currency.CurrencyAmount;
import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import portals.EmployeeRecordsProcessor;

import java.io.IOException;
import java.io.NotSerializableException;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

/**
 * Program to add employee records from the command line.
 * @author Alonso del Arte
 */
public class EmployeeRecordAdder {

    /**
     * The default currency, United States dollars. ISO-4217 letter code USD, 
     * number code 840. Locale-specific symbol is "$".
     */
    public static final Currency UNITED_STATES_DOLLARS
            = Currency.getInstance(Locale.US);
    
    /**
     * The default minimum hourly rate, USD$15.00 per hour. This will substitute 
     * for input that can't be parsed as a numerical amount of the currency 
     * unit.
     */
    public static final CurrencyAmount DEFAULT_MINIMUM_HOURLY_RATE 
            = new CurrencyAmount(1500, UNITED_STATES_DOLLARS);

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Employee Record Adder program, version 0.1");
        System.out.println();
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Please enter employee name: ");
            String name = input.nextLine();
            System.out.print("Please enter employee job title: ");
            String title = input.nextLine();
            System.out.print("Please enter employee SSN (with dashes): ");
            String ssnStr = input.nextLine();
            SocialSecurityNumber ssn = SocialSecurityNumber.parseSSN(ssnStr);
            System.out.println("NOTE: For most purposes, the SSN will be redacted.");
            System.out.println("e.g., " + ssn.toRedactedString());
            Employee employee = new Employee(name, ssn);
            employee.setJobTitle(title);
            System.out.print("Please enter employee hourly rate in whole dollars: $");
            String dollarStr = input.nextLine();
            System.out.print("Please enter cents (0 if none): ");
            String centStr = input.nextLine();
            System.out.println();
            CurrencyAmount rate;
            try {
                int dollars = Integer.parseInt(dollarStr) * 100;
                int cents = Integer.parseInt(centStr);
                rate = new CurrencyAmount(dollars + cents,
                        UNITED_STATES_DOLLARS);
            } catch (NumberFormatException nfe) {
                System.out.print("Sorry, didn't catch that: ");
                System.out.println("\"" + nfe.getMessage() + "\"");
                rate = DEFAULT_MINIMUM_HOURLY_RATE;
                System.out.println("Substituting " + rate.toString());
            }
            employee.setHourlyRate(rate);
            try {
                EmployeeRecordsProcessor.addRecord(employee);
                System.out.println("Successfully added record for " + name);
            } catch (NotSerializableException nse) {
                System.out.println("NotSerializableException occurred...");
                System.out.println("\"" + nse.getMessage() + "\"");
                System.out.println("Refer this issue to programming team.");
            } catch (IOException ioe) {
                System.out.println(ioe.getClass().getName() + " occurred...");
                System.out.println("\"" + ioe.getMessage() + "\"");
            }
        }
    }

}

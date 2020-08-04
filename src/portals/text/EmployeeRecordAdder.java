/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public static final Currency UNITED_STATES_DOLLARS
            = Currency.getInstance(Locale.US);

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
                rate = new CurrencyAmount(1500, UNITED_STATES_DOLLARS);
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

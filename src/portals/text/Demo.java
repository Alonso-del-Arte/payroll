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
import java.io.IOException;
import payroll.TimeCard;
import portals.CurrentTimeCardFetcher;

import java.util.Currency;
import java.util.Locale;

/**
 * Demonstration of the Payroll program text-based portal. This creates employee 
 * records and time cards for a dozen fictional employees of a fictional 
 * company. The names, Social Security Numbers, job titles, salary or wage 
 * amounts are either fictitious or used in a fictitious manner.
 * @author Alonso del Arte
 */
public class Demo {
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final String[] NAMES = {"Buck Strickland", "John Lopez", 
        "Hank Hill", "Lando Calrissian Watson", "Theresa Pia\u0142kowski",
        "Adaego Okafor", "Zainab Zaman", "John Redcorn", "Keiko Yamada",
        "Hillary Schrader Whitcher", "Thomas Thomason", "Richard Richardson", 
        "Harry Harrison", "Sally Sallinen"};
    
    private static final int[] NUMBERS = {750015366, 750481729, 750304850, 
        387432083, 752014847, 753012958, 754370964, 755892008, 756086053, 
        78051120, 752014848, 752014849, 752014850, 752014851};
    
    private static final String[] TITLES = {"Founder", "CEO", "Manager", 
        "Assistant Manager", "Treasurer", "Product Manager", "Developer", 
        "Spokesman", "Chemical Engineer", "Secretary", "Associate", "Associate", 
        "Associate", "Associate"};
    
    private static final int[] AMOUNTS = {0, 50000, 10000, 8000, 9000, 8025, 
        7320, 5000, 12000, 4530, 1500, 1500, 1500, 1500};
    
    private static void setRecords() throws ClassNotFoundException, IOException {
        Employee employee;
        SocialSecurityNumber ssn;
        CurrencyAmount amount;
        CurrentTimeCardFetcher fetcher;
        int len = NAMES.length;
        for (int i = 0; i < len; i++) {
            ssn = new SocialSecurityNumber(NUMBERS[i]);
            employee = new Employee(NAMES[i], ssn);
            employee.setJobTitle(TITLES[i]);
            amount = new CurrencyAmount(AMOUNTS[i], DOLLARS);
            employee.setHourlyRate(amount);
            EmployeeRecordAdder.addRecord(employee);
            System.out.println("Added record for " + employee.getJobTitle()
                    + " " + employee.getFullName());
            fetcher = new CurrentTimeCardFetcher(employee);
            TimeCard card = fetcher.retrieveCard();
            card = CurrentTimeCardFetcher.writeNewDemoCard(card);
            fetcher.putCardBack(card);
        }
    }
    
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Demo of Payroll program, version 0.1");
        System.out.println();
        try {
            setRecords();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Sorry, " + e.getClass().getName() + " occurred");
            System.out.println("Exiting demo...");
            System.exit(-1);
        }
        System.out.println();
        System.out.println("Now transferring to Employee Records Lister programm...");
        EmployeeRecordsLister.main(args);
        System.out.println();
        System.out.println("Now transferring to Time Card Clock programm...");
        TimeCardClock.main(args);
        System.out.println();
        System.out.println("The records and time cards for these fictional ");
        System.out.println("employees are available for further examination ");
        System.out.println("using the Employee Records Lister and Time Card ");
        System.out.println("Clock programs.");
        System.out.println();
        System.out.println("This concludes the demonstration.");
    }
    
}

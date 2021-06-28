/*
 * Copyright (C) 2021 Alonso del Arte
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
package portals;

import currency.CurrencyAmount;
import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import payroll.TimeCard;

import java.io.IOException;
import java.util.Currency;
import java.util.Locale;

/**
 * Sets up time cards for fictional employees for the purpose of demonstrating  
 * this program. Any similarity to real persons or companies is entirely 
 * coincidental, except in the case of Hillary Schrader Whitcher, whose name is 
 * used here in a fictitious manner.
 * @author Alonso del Arte
 */
public class DemoCardsSetup {
    
    /**
     * The default currency, United States dollars. ISO-4217 letter code USD, 
     * number code 840. Locale-specific symbol is "$".
     */
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    /**
     * The names for the fictional employees. Buck Strickland, John Lopez, Hank 
     * Hill, Lando Calrissian Watson, Theresa Pia&#x142;kowski, Adaego Okafor, 
     * Zainab Zaman, John Redcorn, Keiko Yamada, Hillary Schrader Whitcher, 
     * Thomas Thomason, Richard Richardson, Harry Harrison, Sally Sallinen.
     */
    private static final String[] NAMES = {"Buck Strickland", "John Lopez", 
        "Hank Hill", "Lando Calrissian Watson", "Theresa Pia\u0142kowski",
        "Adaego Okafor", "Zainab Zaman", "John Redcorn", "Keiko Yamada",
        "Hillary Schrader Whitcher", "Thomas Thomason", "Richard Richardson", 
        "Harry Harrison", "Sally Sallinen"};
    
    /**
     * The numbers for the Social Security numbers as integer primitives. These 
     * numbers will be fed to the {@link SocialSecurityNumber} constructor.
     */
    private static final int[] NUMBERS = {750015366, 750481729, 750304850, 
        387432083, 752014847, 753012958, 754370964, 755892008, 756086053, 
        78051120, 752014848, 752014849, 752014850, 752014851};
    
    /**
     * The job titles of the fictional employees. Founder, CEO, Manager, 
        Assistant Manager, Treasurer, Product Manager, Developer, Spokesman, 
        Chemical Engineer, Secretary, Associates (four).
     */
    private static final String[] TITLES = {"Founder", "CEO", "Manager", 
        "Assistant Manager", "Treasurer", "Product Manager", "Developer", 
        "Spokesman", "Chemical Engineer", "Secretary", "Associate", "Associate", 
        "Associate", "Associate"};
    
    /**
     * The wage or salary amounts for the fictional employees as integer 
     * primitives of cents of a dollar per hour. These will be fed to the {@link 
     * CurrencyAmount} constructor.
     */
    private static final int[] AMOUNTS = {0, 20000, 10000, 8000, 9000, 8025, 
        7320, 5000, 12000, 4530, 1500, 1500, 1500, 1500};
    
    /**
     * Sets the records for the fictional employees using the static data 
     * contained in this class. For real employees, the data should be retrieved 
     * from an appropriately secured database.
     * @throws ClassNotFoundException If there is a problem retrieving time 
     * cards from the files if they were written with a different object format.
     * @throws IOException If there is a problem reading or writing time card 
     * files.
     */
    public static void setRecords() throws ClassNotFoundException, IOException {
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
            EmployeeRecordsProcessor.addRecord(employee);
            System.out.println("Added record for " + employee.getJobTitle()
                    + " " + employee.getFullName());
            fetcher = new CurrentTimeCardFetcher(employee);
            TimeCard card = fetcher.retrieveCard();
            card = CurrentTimeCardFetcher.writeNewDemoCard(card);
            fetcher.putCardBack(card);
        }
    }
    
}

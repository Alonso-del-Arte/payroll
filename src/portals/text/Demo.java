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
package portals.text;

import java.io.IOException;
import portals.DemoCardsSetup;

/**
 * Demonstration of the Payroll program text-based portal. This creates employee 
 * records and time cards for a dozen fictional employees of a fictional 
 * company. The names, Social Security Numbers, job titles, salary or wage 
 * amounts are either fictitious or used in a fictitious manner.
 * @author Alonso del Arte
 */
public class Demo {
    
    /**
     * Demonstrates the program with some fictional employees and their time 
     * cards.
     * @param args The command line parameters. These are simply ignored.
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Demo of Payroll program, version 0.1");
        System.out.println();
        try {
            DemoCardsSetup.setRecords();
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

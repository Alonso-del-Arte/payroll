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

import java.io.IOException;
import java.util.ArrayList;

import static portals.EmployeeRecordsProcessor.getRecords;

/**
 * A program to list all employees with records in the record data directory. 
 * This does not include time cards.
 * @author Alonso del Arte
 */
public class EmployeeRecordsLister {
    
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Employee Records Lister program, version 0.1");
        System.out.println();
        try {
            ArrayList<Employee> records = getRecords();
            System.out.println("Found " + records.size() + " records...");
            System.out.println();
            records.stream().map((record) -> {
                System.out.println("Name: " + record.getFullName());
                return record;
            }).map((record) -> {
                System.out.println("Title: " + record.getJobTitle());
                return record;
            }).map((record) -> { 
                System.out.println("SSN: "
                        + ((SocialSecurityNumber)
                                record.getTIN()).toRedactedString());
                return record; 
            }).forEachOrdered((record) -> {
                System.out.println("Hourly rate: "
                        + record.getHourlyRate().toString());
                System.out.println();
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

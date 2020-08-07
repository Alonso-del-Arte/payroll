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
package portals;

import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Creates and retrieves employee records. The records are written to the 
 * directory specified through {@link DataDirectoryInitializer} and read from 
 * there. If no directory has been set, the default temporary folder will be 
 * used.
 * @since Version 0.1.
 * @author Alonso del Arte
 */
public class EmployeeRecordsProcessor {
    
    public static void addRecord(Employee employee)
            throws IOException {
        if (!DataDirectoryInitializer.hasBeenSet()) {
            DataDirectoryInitializer.setDir();
        }
        String pathStr = DataDirectoryInitializer.getDir().getPath()
                + File.separatorChar + "PersProgempl"
                + employee.getTIN().hashCode()
                + ".dat";
        File file = new File(pathStr);
        FileOutputStream fileStream = new FileOutputStream(file);
        try (ObjectOutputStream objStream
                = new ObjectOutputStream(fileStream)) {
            objStream.writeObject(employee);
        }
    }

    public static ArrayList<Employee> getRecords() 
            throws ClassNotFoundException, IOException {
        if (!DataDirectoryInitializer.hasBeenSet()) {
            DataDirectoryInitializer.setDir();
        }
        File dir = DataDirectoryInitializer.getDir();
        EmployeeRecordFilter filter = new EmployeeRecordFilter();
        File[] matches = dir.listFiles(filter);
        ArrayList<Employee> list = new ArrayList<>();
        for (File file : matches) {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            Employee employee = (Employee) objStream.readObject();
            list.add(employee);
        }
        return list;
    }
    
    /**
     * Retrieves records for employees whose Social Security Numbers (SSNs) 
     * match in their last four digits.
     * @param last4 The last four digits to match. For example, 1729.
     * @return A list of employees with matching last four for their SSNs. It'll 
     * be an empty list if there were no matches. Suppose for example that there 
     * is a record for an employee with SSN 000-00-1729 and a record for an 
     * employee with SSN 752-98-1729, and no others; those two records should be 
     * returned by this function.
     * @throws ClassNotFoundException If there was a deserialization problem 
     * with the employee record files.
     * @throws IOException If there was some problem reading the employee record 
     * files.
     */
    public static ArrayList<Employee> getRecords(int last4) 
            throws ClassNotFoundException, IOException {
        ArrayList<Employee> list = getRecords();
        for (int i = list.size() - 1; i > -1; i--) {
            if (!((SocialSecurityNumber) list.get(i).getTIN()).matchesLastFour(last4)) {
                list.remove(i);
            }
        }
        return list;
    }
     
}

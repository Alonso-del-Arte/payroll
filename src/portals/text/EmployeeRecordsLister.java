/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portals.text;

import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import portals.DataDirectoryInitializer;
import portals.EmployeeRecordFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Alonso del Arte
 */
public class EmployeeRecordsLister {
    
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

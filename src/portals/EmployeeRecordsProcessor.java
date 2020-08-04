/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
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

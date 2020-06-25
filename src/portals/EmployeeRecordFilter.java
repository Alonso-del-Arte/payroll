/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portals;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Alonso del Arte
 */
public class EmployeeRecordFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.startsWith("PersProgempl") && name.endsWith(".dat");
    }
    
}

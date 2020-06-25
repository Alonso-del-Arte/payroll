/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portals;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class TimeCardFilterTest {
    
    private static final String HOME_DIR_PATH = System.getProperty("user.home");
    
    private static final File HOME_DIR = new File(HOME_DIR_PATH);

    /**
     * Test of accept method, of class TimeCardFilter.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        String fileName = "PersProgcard2938370.dat";
        String msg = "Filter should accept file " + fileName + " in directory "
                + HOME_DIR_PATH;
        TimeCardFilter filter = new TimeCardFilter();
        assert filter.accept(HOME_DIR, fileName) : msg;
    }
        
    @Test
    public void testReject() {
        System.out.println("accept");
        String fileName = "PersProgempl1035489.dat";
        String msg = "Filter should reject file " + fileName + " in directory "
                + HOME_DIR_PATH;
        TimeCardFilter filter = new TimeCardFilter();
        assert !filter.accept(HOME_DIR, fileName) : msg;
    }
        
}

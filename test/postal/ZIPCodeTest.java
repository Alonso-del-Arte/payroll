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
package postal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ZIPCode class.
 * @author Alonso del Arte
 */
public class ZIPCodeTest {
    
    @Test
    public void testSuperClass() {
        Object zip = new ZIPCode(94020);
        String msg = "ZIPCode instance should also be PostalCode instance";
        assert zip instanceof PostalCode : msg;
    }
    
    @Test
    public void testGetCountry() {
        ZIPCode zip = new ZIPCode(2111, 1307);
        Locale expected = Locale.US;
        Locale actual = zip.getCountry();
        assertEquals(expected, actual);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        ZIPCode zip = new ZIPCode(90210, 4817);
        String expected = "90210-4817";
        String actual = zip.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringWithoutZIP4() {
        ZIPCode zip = new ZIPCode(90210);
        String expected = "90210";
        String actual = zip.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringMassachusettsZIP() {
        ZIPCode zip = new ZIPCode(2111, 1307);
        String expected = "02111-1307";
        String actual = zip.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringLowZIP4() {
        ZIPCode zip = new ZIPCode(99999, 28);
        String expected = "99999-0028";
        String actual = zip.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        ZIPCode zip = new ZIPCode(90210, 4817);
        String msg = "ZIP code " + zip 
                + " should be found to be equal to itself";
        assert zip.equals(zip) : msg;
    }
    
    @Test
    public void testNotEqualsNull() {
        ZIPCode zip = new ZIPCode(2111, 1307);
        String msg = "ZIP code " + zip 
                + " should not be found to be equal to null";
        assertNotEquals(msg, zip, null);
    }
    
    @Test
    public void testNotEqualsObjectOtherClass() {
        PostalCode realZIP = new ZIPCode(2111, 1307);
        PostalCode mockZIP = new MockPostalCode(21111307, Locale.US);
        String msg = realZIP.toString() + " should not be found to be equal to " 
                + mockZIP.toString();
        assert !realZIP.equals(mockZIP) : msg;
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        ZIPCode someZIP = new ZIPCode(90210, 4817);
        ZIPCode sameZIP = new ZIPCode(90210, 4817);
        String msg = "ZIP codes " + someZIP + " and " + sameZIP 
                + " should be found to be equal";
        assert someZIP.equals(sameZIP) : msg;
    }
    
    @Test
    public void testNotEqualsDiffZIP4() {
        ZIPCode genZIP = new ZIPCode(90210);
        ZIPCode specZIP = new ZIPCode(90210, 4817);
        assertNotEquals(genZIP, specZIP);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ArrayList<ZIPCode> zips = new ArrayList<>();
        HashSet<Integer> hashes = new HashSet<>();
        ZIPCode zip;
        for (int i = 0; i < 10000; i++) {
            zip = new ZIPCode(90209, i);
            zips.add(zip);
            hashes.add(zip.hashCode());
            zip = new ZIPCode(90210, i);
            zips.add(zip);
            hashes.add(zip.hashCode());
        }
        String msg = "Two thousand ZIP codes should have 2,000 distinct hash codes";
        assertEquals(msg, zips.size(), hashes.size());
    }
    
    /**
     * Test of the ZIPCode constructor. Should not be able to create ZIPCode 
     * with a negative number for the ZIP5.
     */
    @Test
    public void testNegativeZIP5NotAllowed() {
        int negNum = -1;
        try {
            ZIPCode badZIP = new ZIPCode(negNum);
            String msg = "Should not have been able to create invalid ZIP " 
                    + badZIP.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + negNum 
                    + " for a ZIP code correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + negNum + " for a ZIP code";
            fail(msg);
        }
    }
    
    /**
     * Test of the ZIPCode constructor. Should not be able to create ZIPCode 
     * with a negative number for the ZIP+4.
     */
    @Test
    public void testNegativeZIP4NotAllowed() {
        int negNum = -1;
        try {
            ZIPCode badZIP = new ZIPCode(90210, negNum);
            String msg = "Should not have been able to create invalid ZIP " 
                    + badZIP.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + negNum 
                    + " for a ZIP+4 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + negNum + " for a ZIP+4";
            fail(msg);
        }
    }
    
    /**
     * Test of the ZIPCode constructor. Should not be able to create ZIPCode 
     * with a number in excess of 99999 for the ZIP5.
     */
    @Test
    public void testExcessiveZIP5NotAllowed() {
        int excess = 100000;
        try {
            ZIPCode badZIP = new ZIPCode(excess);
            String msg = "Should not have been able to create invalid ZIP " 
                    + badZIP.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + excess 
                    + " for a ZIP code correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + excess + " for a ZIP code";
            fail(msg);
        }
    }
    
    /**
     * Test of the ZIPCode constructor. Should not be able to create ZIPCode 
     * with a number in excess of 99999 for the ZIP+4.
     */
    @Test
    public void testExcessiveZIP4NotAllowed() {
        int excess = 10000;
        try {
            ZIPCode badZIP = new ZIPCode(90210, excess);
            String msg = "Should not have been able to create invalid ZIP " 
                    + badZIP.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + excess 
                    + " for a ZIP+4 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + excess + " for a ZIP+4";
            fail(msg);
        }
    }
    
}

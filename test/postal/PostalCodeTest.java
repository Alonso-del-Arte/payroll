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

import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the abstract PostalCode class.
 * @author Alonso del Arte
 */
public class PostalCodeTest {
    
    @Test
    public void testGetCountry() {
        Locale expected = Locale.TRADITIONAL_CHINESE;
        PostalCode code = new PostalCode(0, expected) {
        };
        Locale actual = code.getCountry();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        PostalCode code = new PostalCodeImpl(482012005);
        assertEquals(code, code);
    }
    
    @Test
    public void testNotEqualsNull() {
        PostalCode code = new PostalCodeImpl(21111307);
        assertNotEquals(code, null);
    }
    
    /**
     * Test of equals method, of class PostalCode.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        PostalCode someCode = new PostalCodeImpl(480841564);
        PostalCode sameCode = new PostalCodeImpl(480841564);
        assertEquals(someCode, sameCode);
    }

    @Test
    public void testNotEqualsDiffClass() {
        ZIPCode zip = new ZIPCode(90210, 4817);
        PostalCode zap = new PostalCodeImpl(902104817);
        assertNotEquals(zip, zap);
    }
    
    @Test
    public void testNotEqualsAnonClass() {
        PostalCode zip = new PostalCodeImpl(200041477);
        PostalCode zap = new PostalCode(200041477, Locale.US) {
            
            @Override
            public String toString() {
                return "AnonPostal" + this.postalCodeNumber;
            }
            
        };
        System.out.println(zap.getClass().getName());
        assertNotEquals(zip, zap);
    }
    
    @Test
    public void testNotEqualsDiffPostalCode() {
        PostalCode someCode = new PostalCodeImpl(200041477);
        PostalCode diffCode = new PostalCodeImpl(480841564);
        assertNotEquals(someCode, diffCode);
    }
    
    /**
     * Test of hashCode method, of class PostalCode. Regardless of whether the 
     * <code>Locale</code> field is used in <code>equals()</code>, it should be 
     * used in <code>hashCode()</code>. This test uses the Brookfield, Illinois 
     * ZIP code 60513-0048 as a <code>ZIPCode</code> instance and as a nested 
     * static class instance, and a postal code for a few cities in Newfoundland 
     * and Labrador, A0A 1C0, understood as the base 36 representation of 
     * 605130048, through an anonymous class.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ZIPCode zipBrookfield = new ZIPCode(60513, 48);
        PostalCodeImpl sameNumberZIP = new PostalCodeImpl(605130048);
        String msg = "Brookfield, Illinois ZIP " + zipBrookfield.toString() 
                + " and " + sameNumberZIP.toString() 
                + " should have the same hash code";
        int zipBrookHash = zipBrookfield.hashCode();
        assertEquals(msg, zipBrookHash, sameNumberZIP.hashCode());
        PostalCode stJohnPostCode = new PostalCode(605130048, Locale.CANADA) {
            
            @Override
            public String toString() {
                return this.postalGov.getCountry() + " postal code " 
                        + Long.toString(this.postalCodeNumber, 36).toUpperCase();
            }
            
        };
        msg = "Brookfield, Illinois ZIP " + zipBrookfield.toString() 
                + " and St.John's in Newfoundland and Labrador " 
                + stJohnPostCode.toString() 
                + " should not have the same hash code";
        assertNotEquals(msg, zipBrookHash, stJohnPostCode.hashCode());
    }

    public static class PostalCodeImpl extends PostalCode {
        
        @Override
        public String toString() {
            return "ImplPostal " + this.postalCodeNumber;
        }

        public PostalCodeImpl(int code) {
            super(code, Locale.US);
        }
        
    }
    
}

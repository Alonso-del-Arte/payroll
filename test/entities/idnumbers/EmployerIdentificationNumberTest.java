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
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package entities.idnumbers;

import java.util.HashSet;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the EmployerIdentificationNumber class.
 * @author Alonso del Arte
 */
public class EmployerIdentificationNumberTest {
    
    private static final Random RANDOM = new Random();
    
    /**
     * Test of correctEINDashPlacement function, of class 
     * EmployerIdentificationNumber.
     */
    @Test
    public void testCorrectEINDashPlacement() {
        System.out.println("correctEINDashPlacement");
        String s = "12-3456789";
        String msg = "\"" + s + "\" has correct EIN dash placement";
        assert EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
    }
    
    /**
     * Another test of correctEINDashPlacement function, of class 
     * EmployerIdentificationNumber.
     */
    @Test
    public void testIncorrectEINDashPlacement() {
        String s = "123-456-789";
        String msg = "\"" + s + "\" does not have correct EIN dash placement";
        assert !EmployerIdentificationNumber.correctEINDashPlacement(s) : msg;
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        int number = RANDOM.nextInt(1000000) + 1000000;
        EmployerIdentificationNumber ein 
                = new EmployerIdentificationNumber(980000000 + number);
        String expected = "98-" + number;
        String actual = ein.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        int number = 981000000 + RANDOM.nextInt(1000000);
        EmployerIdentificationNumber ein 
                = new EmployerIdentificationNumber(number);
        assertEquals(ein, ein);
    }
    
    @Test
    public void testNotEqualsNull() {
        int number = 981000000 + RANDOM.nextInt(1000000);
        EmployerIdentificationNumber ein 
                = new EmployerIdentificationNumber(number);
        assertNotEquals(ein, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        int number = 772000000 + RANDOM.nextInt(1000000);
        EmployerIdentificationNumber ein 
                = new EmployerIdentificationNumber(number);
        SocialSecurityNumber ssn = new SocialSecurityNumber(number);
        assertNotEquals(ein, ssn);
    }
    
    @Test
    public void testNotEquals() {
        int number = RANDOM.nextInt(1000000);
        EmployerIdentificationNumber someEIN 
                = new EmployerIdentificationNumber(981000000 + number);
        EmployerIdentificationNumber diffEIN 
                = new EmployerIdentificationNumber(993000000 + number);
        assertNotEquals(someEIN, diffEIN);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        int number = RANDOM.nextInt(1000000);
        EmployerIdentificationNumber someEIN 
                = new EmployerIdentificationNumber(975000000 + number);
        EmployerIdentificationNumber sameEIN 
                = new EmployerIdentificationNumber(975000000 + number);
        assertEquals(someEIN, sameEIN);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int number = RANDOM.nextInt(1000000);
        EmployerIdentificationNumber someEIN 
                = new EmployerIdentificationNumber(976000000 + number);
        EmployerIdentificationNumber sameEIN 
                = new EmployerIdentificationNumber(976000000 + number);
        assertEquals(someEIN.hashCode(), sameEIN.hashCode());
    }
    
    @Test
    public void testHashCodeUniqueness() {
        HashSet<EmployerIdentificationNumber> eins = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        EmployerIdentificationNumber ein;
        int hash, number;
        do {
            number = 964000000 + RANDOM.nextInt(1000000);
            ein = new EmployerIdentificationNumber(number);
            eins.add(ein);
            hash = ein.hashCode();
            hashes.add(hash);
        } while (number % 500 != 0);
        int numberOfEINs = eins.size();
        int numberOfHashes = hashes.size();
        System.out.println("Created " + numberOfEINs + " EIN instances with " 
                + numberOfHashes + " hash codes");
        String msg = "Each EIN instance should have a unique hash code";
        assertEquals(msg, numberOfEINs, numberOfHashes);
    }
    
}

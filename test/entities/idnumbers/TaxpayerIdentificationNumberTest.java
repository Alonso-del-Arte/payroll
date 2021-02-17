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
package entities.idnumbers;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the TaxpayerIdentificationNumber class.
 * @author Alonso del Arte
 */
public class TaxpayerIdentificationNumberTest {
    
    private static final Random RANDOM = new Random();
    
    @Test
    public void testReferentialEquality() {
        TaxpayerIdentificationNumber someNumber 
                = new TaxpayerIdentificationNumberImpl(123456789);
        assertEquals(someNumber, someNumber);
    }
    
    @Test
    public void testNotEqualsNull() {
        TaxpayerIdentificationNumber someNumber 
                = new TaxpayerIdentificationNumberImpl(123456789);
        assertNotEquals(someNumber, null);
    }
    
    @Test
    public void testNotEqualsDiffClass() {
        int number = 772000000 + RANDOM.nextInt(1000000);
        TaxpayerIdentificationNumber tin 
                = new TaxpayerIdentificationNumberImpl(number);
        SocialSecurityNumber ssn = new SocialSecurityNumber(number);
        assertNotEquals(tin, ssn);
    }
    
    @Test
    public void testNotEquals() {
        int number = RANDOM.nextInt(1000000);
        TaxpayerIdentificationNumber someTIN 
                = new TaxpayerIdentificationNumberImpl(981000000 + number);
        TaxpayerIdentificationNumber diffTIN 
                = new TaxpayerIdentificationNumberImpl(993000000 + number);
        assertNotEquals(someTIN, diffTIN);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        int number = 978000000 + RANDOM.nextInt(1000000);
        TaxpayerIdentificationNumber someTIN 
                = new TaxpayerIdentificationNumberImpl(number);
        TaxpayerIdentificationNumber sameTIN 
                = new TaxpayerIdentificationNumberImpl(number);
        assertEquals(someTIN, sameTIN);
    }
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int number = 953000000 + RANDOM.nextInt(1000000);
        TaxpayerIdentificationNumber someTIN 
                = new TaxpayerIdentificationNumberImpl(number);
        TaxpayerIdentificationNumber sameTIN 
                = new TaxpayerIdentificationNumberImpl(number);
        assertEquals(someTIN.hashCode(), sameTIN.hashCode());
    }
    
    @Test
    public void testHashCodeSameNumberDiffClass() {
        int number = 772000000 + RANDOM.nextInt(1000000);
        TaxpayerIdentificationNumber someTIN 
                = new TaxpayerIdentificationNumberImpl(number);
        SocialSecurityNumber someSSN = new SocialSecurityNumber(number);
        assertNotEquals(someTIN.hashCode(), someSSN.hashCode());
    }
    
    @Test
    public void testConstructorRejectsNegativeNumbers() {
        try {
            TaxpayerIdentificationNumber badNumber 
                    = new TaxpayerIdentificationNumberImpl(-1);
            String msg = "Should not have been able to create " 
                    + badNumber.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Correct IllegalArgumentException negative TIN");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for TIN with negative number";
            fail(msg);
        }
    }
    
    /**
     * Implementation class strictly for testing purposes. It should only be 
     * used in Test Packages, and maybe only in the enclosing class.
     */
    private static class TaxpayerIdentificationNumberImpl 
            extends TaxpayerIdentificationNumber {
        
        @Override
        int hashCodeOffset() {
            return 0;
        }
    
        @Override
        public String toString() {
            return "0-" + this.idNum;
        }
    
        public TaxpayerIdentificationNumberImpl(int num) {
            super(num);
        }

    }
    
}

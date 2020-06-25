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
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package entities.idnumbers;

import java.awt.Color;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the SocialSecurityNumber class.
 * @author Alonso del Arte
 */
public class SocialSecurityNumberTest {
    
    // REF: https://www.ssa.gov/history/ssnmyth.html
    
    // REF: https://www.ssa.gov/employer/stateweb.htm
    
    // REF: https://www.ssa.gov/history/ssn/misused.html
    
    // REF: https://www.irs.gov/forms-pubs/about-form-ss-4
    
    @Test
    public void testReferentialEquality() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(5040);
        assertEquals(ssn, ssn);
    }
    
    @Test
    public void testNotEqualsNull() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(726);
        assertNotEquals(ssn, null);
    }
    
    @Test
    public void testNotEqualsObjOtherClass() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(750025000);
        Color color = new Color(750025000, true);
        assertNotEquals(ssn, color);
    }
    
    @Test
    public void testUnequalSSNs() {
        SocialSecurityNumber ssnA = new SocialSecurityNumber(78051120);
        SocialSecurityNumber ssnB = new SocialSecurityNumber(219099999);
        assertNotEquals(ssnA, ssnB);
    }
    
    @Test
    public void testEquals() {
        System.out.println("equals");
        SocialSecurityNumber someSSN = new SocialSecurityNumber(219099999);
        SocialSecurityNumber sameSSN = new SocialSecurityNumber(219099999);
        assertEquals(someSSN, sameSSN);
    }

    /**
     * Test of the toString function, of the class SocialSecurityNumber. The 
     * Social Security Administration asserts that an SSN with area number 000 
     * will never be assigned to anyone.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SocialSecurityNumber ssn = new SocialSecurityNumber(1729);
        String expected = "000-00-1729";
        String actual = ssn.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringArea001() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(1999999);
        String expected = "001-99-9999";
        String actual = ssn.toString();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of toString function of class SocialSecurityNumber. The Social 
     * Security Number 078-05-1120 belonged to Hilda Schrader Whitcher, an 
     * executive secretary at E. H. Ferree, a wallet manufacturer. Then it was 
     * used in an example card in wallets sold at Woolworth and other stores. 
     * Eventually, the Social Security Administration voided the number and 
     * issued the secretary a new number.
     */
    @Test
    public void testToStringArea078() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(78051120);
        String expected = "078-05-1120";
        String actual = ssn.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToStringArea750() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(750025000);
        String expected = "750-02-5000";
        String actual = ssn.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testToRedactedString() {
        System.out.println("toRedactedString");
        SocialSecurityNumber ssn = new SocialSecurityNumber(78051120);
        String expected = "***-**-1120";
        String actual = ssn.toRedactedString();
        assertEquals(expected, actual);
    }
    
    
    /**
     * Test of matchesLastFour
     */
    @Test
    public void testMatchesLastFour() {
        System.out.println("matchesLastFour");
        int num = 1729;
        SocialSecurityNumber socSecNum = new SocialSecurityNumber(num);
        String msg = "Last four of " + socSecNum.toRedactedString() 
                + " should match " + num;
        assert socSecNum.matchesLastFour(num) : msg;
    }
    
    @Test
    public void testDoesNotMatchLastFour() {
        int num = 1729;
        SocialSecurityNumber socSecNum = new SocialSecurityNumber(num);
        int diffNum = (num * 6 + 1) % 1000;
        String msg = "Last four of " + socSecNum.toRedactedString() 
                + " should not match " + diffNum;
        assert !socSecNum.matchesLastFour(diffNum) : msg;
    }
    
    @Test
    public void testCorrectSSNDashPlacement() {
        System.out.println("correctSSNDashPlacement");
        String s = "***-**-1120";
        String assertionMessage = "\"" + s + "\" has correct SSN dash placement";
        assert SocialSecurityNumber.correctSSNDashPlacement(s) : assertionMessage;
    }
    
    @Test
    public void testIncorrectSSNDashPlacement() {
        String s = "12-3456789";
        String assertionMessage = "\"" + s 
                + "\" does not have correct SSN dash placement";
        assert !SocialSecurityNumber.correctSSNDashPlacement(s) : assertionMessage;
    }
    
    /**
     * Test of parseSSN function of SocialSecurityNumber class. The Social 
     * Security Number 219-09-9999 was used as an example in a Social Security 
     * pamphlet in 1940. In 1962, a woman in Utah claimed that was her Social 
     * Security Number and presented the pamphlet as proof.
     */
    @Test
    public void testParseSSN() {
        System.out.println("parseSSN");
        String s = "219-09-9999";
        SocialSecurityNumber expected = new SocialSecurityNumber(219099999);
        SocialSecurityNumber actual = SocialSecurityNumber.parseSSN(s);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testNoParseEIN() {
        String s = "12-3456789";
        try {
            SocialSecurityNumber ein = SocialSecurityNumber.parseSSN(s);
            String failMsg = "EIN \"" + s 
                    + "\" should not have been interpresented as SSN \"" + ein 
                    + "\".";
            fail(failMsg);
        } catch (NumberFormatException nfe) {
            System.out.println("Trying to interpret EIN \"" + s 
                    + "\" as an SSN correctly caused NumberFormatException");
            System.out.println("\"" + nfe.getMessage() + "\"");
            String allCaps = nfe.getMessage().toUpperCase();
            boolean mentionEIN = allCaps.contains("EIN") 
                    || allCaps.contains("EMPLOYER IDENTIFICATION NUMBER");
            String msg = "Message should mention Employer Identification Number (EIN)";
            assert mentionEIN : msg;
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to interpret EIN \""
                    + s + "\" as an SSN";
            fail(failMsg);
        }
    }
    
    @Test
    public void testNoParseBadString() {
        String s = "Not an SSN";
        try {
            SocialSecurityNumber badSSN = SocialSecurityNumber.parseSSN(s);
            String failMsg = "Bad String \"" + s 
                    + "\" should not have been interpresented as SSN \"" + badSSN 
                    + "\".";
            fail(failMsg);
        } catch (NumberFormatException nfe) {
            System.out.println("Trying to interpret bad String \"" + s 
                    + "\" as an SSN correctly caused NumberFormatException");
            System.out.println("\"" + nfe.getMessage() + "\"");
            String allCaps = nfe.getMessage().toUpperCase();
            boolean mentionEIN = allCaps.contains("EIN") 
                    || allCaps.contains("EMPLOYER IDENTIFICATION NUMBER");
            String msg = "Message should not mention Employer Identification Number (EIN)";
            assert !mentionEIN : msg;
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to interpret bad String \""
                    + s + "\" as an SSN";
            fail(failMsg);
        }
    }
    
    @Test
    public void testNoNegativeSSNs() {
        try {
            SocialSecurityNumber badSSN = new SocialSecurityNumber(-1);
            String failMsg = "Should not have been able to create SSN " + badSSN.toString();
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create SSN with negative number correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to create SSN with negative number";
            fail(failMsg);
        }
    }

    @Test
    public void testNoArea773SSNs() {
        try {
            SocialSecurityNumber badSSN = new SocialSecurityNumber(773000000);
            String failMsg = "Should not have been able to create SSN " + badSSN.toString();
            fail(failMsg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create SSN with area 773 correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to create SSN with area 773";
            fail(failMsg);
        }
    }

}

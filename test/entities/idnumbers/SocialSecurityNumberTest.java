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

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the SocialSecurityNumber class. For these tests, I consulted the 
 * following references:
 * <ul>
 *    <li><a href="https://www.ssa.gov/history/ssnmyth.html">Social Security 
 *    History: A Myth About Social Security Numbers</a>: Explains that the group 
 *    number in 1936 referred to the filing cabinet the Social Security record 
 *    was in, not the race of the record's person.</li>
 *    <li><a href="https://www.ssa.gov/employer/stateweb.htm">Social Security 
 *    Number Allocations</a>: Explains how Social Security Numbers were 
 *    allocated prior to 2011.</li>
 *    <li><a href="https://www.ssa.gov/history/ssn/misused.html">Social Security 
 *    Cards Issued by Woolworth</a>: Tells the story of 078-05-1120, the most 
 *    misused Social Security Number of all time.</li>
 *    <li><a href="https://www.irs.gov/forms-pubs/about-form-ss-4">About Form 
 *    SS-4, Application for Employer Identification Number (EIN)</a> (this page 
 *    is from the IRS, not the Social Security Administration).</li>
 * </ul>
 * @author Alonso del Arte
 */
public class SocialSecurityNumberTest {
    
    private static final Random RANDOM = new Random();
    
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
    
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        int number = RANDOM.nextInt(773000000);
        SocialSecurityNumber someSSN = new SocialSecurityNumber(number);
        SocialSecurityNumber sameSSN = new SocialSecurityNumber(number);
        assertEquals(someSSN.hashCode(), sameSSN.hashCode());
    }
    
    @Test
    public void testHashCodeUniqueness() {
        HashSet<SocialSecurityNumber> ssns = new HashSet<>();
        HashSet<Integer> hashes = new HashSet<>();
        SocialSecurityNumber ssn;
        int hash, number;
        do {
            number = 772000000 + RANDOM.nextInt(1000000);
            ssn = new SocialSecurityNumber(number);
            ssns.add(ssn);
            hash = ssn.hashCode();
            hashes.add(hash);
        } while (number % 500 != 0);
        int numberOfSSNs = ssns.size();
        int numberOfHashes = hashes.size();
        System.out.println("Created " + numberOfSSNs + " SSN instances with " 
                + numberOfHashes + " hash codes");
        String msg = "Each SSN instance should have a unique hash code";
        assertEquals(msg, numberOfSSNs, numberOfHashes);
    }

    /**
     * Test of the toString function, of the class SocialSecurityNumber. The 
     * Social Security Administration asserts that an SSN with area number 000 
     * will never be assigned to anyone. However, it's still necessary to test 
     * that SSNs are zero-padded when necessary.
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
     * Tests of getLastFour function of class SocialSecurityNumber.
     */
    @Test
    public void testGetLastFour() {
        System.out.println("getLastFour");
        int areaGroup = 7805;
        int expected = 1120;
        SocialSecurityNumber ssn = new SocialSecurityNumber(areaGroup 
                * 10000 + expected);
        int actual = ssn.getLastFour();
        assertEquals(expected, actual);
        expected += 33;
        ssn = new SocialSecurityNumber(areaGroup * 10000 + expected);
        actual = ssn.getLastFour();
        assertEquals(expected, actual);
    }
    
    /**
     * Tests of matchesLastFour function of class SocialSecurityNumber.
     */
    @Test
    public void testMatchesLastFour() {
        System.out.println("matchesLastFour");
        int num = 1729;
        SocialSecurityNumber socSecNum = new SocialSecurityNumber(num);
        String msg = "Last four of " + socSecNum.toRedactedString() 
                + " should match " + num;
        assert socSecNum.matchesLastFour(num) : msg;
        int diffAreaGroup = 75208;
        SocialSecurityNumber ssnDiffAreaGroup 
                = new SocialSecurityNumber(diffAreaGroup * 10000 + num);
        msg = "Last four of " + socSecNum.toRedactedString() 
                + " should match last four of " 
                + ssnDiffAreaGroup.toRedactedString();
        assert socSecNum.matchesLastFour(ssnDiffAreaGroup) : msg;
    }
    
    @Test
    public void testDoesNotMatchLastFour() {
        int num = 1729;
        SocialSecurityNumber socSecNum = new SocialSecurityNumber(num);
        int diffNum = (num * 6 + 1) % 1000;
        String msg = "Last four of " + socSecNum.toRedactedString() 
                + " should not match " + diffNum;
        assert !socSecNum.matchesLastFour(diffNum) : msg;
        int diffAreaGroup = 75208;
        SocialSecurityNumber ssnDiffAreaGroup 
                = new SocialSecurityNumber(diffAreaGroup * 10000 + diffNum);
        msg = "Last four of " + socSecNum.toRedactedString() 
                + " should not match last four of " 
                + ssnDiffAreaGroup.toRedactedString();
        assert !socSecNum.matchesLastFour(ssnDiffAreaGroup) : msg;
    }
    
    @Test
    public void testMatchLastFourBeyondTenThousand() {
        int num = 752980000 + RANDOM.nextInt(10000);
        SocialSecurityNumber ssn = new SocialSecurityNumber(num);
        int numWithSameLastFour = num - ((RANDOM.nextInt(95) + 1) * 10000);
        String msg = "Last four of " + ssn.toRedactedString() + " should match " 
                + numWithSameLastFour;
        assert ssn.matchesLastFour(numWithSameLastFour) : msg;
    }
    
    @Test
    public void testMatchLastFourNeverNegative() {
        int num = 752980000 + RANDOM.nextInt(10000);
        SocialSecurityNumber ssn = new SocialSecurityNumber(num);
        String msg = "Last four of " + ssn.toRedactedString() 
                + " should NOT match ";
        for (int i = -1; i > -10000; i--) {
            assert !ssn.matchesLastFour(i) : (msg + i);
        }
    }
    
    @Test
    public void testCorrectSSNDashPlacement() {
        System.out.println("correctSSNDashPlacement");
        String s = "***-**-1120";
        String msg = "\"" + s + "\" has correct SSN dash placement";
        assert SocialSecurityNumber.correctSSNDashPlacement(s) : msg;
    }
    
    @Test
    public void testIncorrectSSNDashPlacement() {
        String s = "12-3456789";
        String msg = "\"" + s + "\" does not have correct SSN dash placement";
        assert !SocialSecurityNumber.correctSSNDashPlacement(s) : msg;
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
            String msg = "EIN \"" + s 
                    + "\" should not have been interpresented as SSN \"" + ein 
                    + "\".";
            fail(msg);
        } catch (NumberFormatException nfe) {
            System.out.println("Trying to interpret EIN \"" + s 
                    + "\" as an SSN correctly caused NumberFormatException");
            System.out.println("\"" + nfe.getMessage() + "\"");
            String allCaps = nfe.getMessage().toUpperCase();
            boolean mentionEIN = allCaps.contains("EIN") 
                    || allCaps.contains("EMPLOYER IDENTIFICATION NUMBER");
            String msg = "Exception message should mention EIN";
            assert mentionEIN : msg;
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for intepreting EIN \""
                    + s + "\" as an SSN";
            fail(msg);
        }
    }
    
    @Test
    public void testNoParseBadString() {
        String s = "Not an SSN";
        try {
            SocialSecurityNumber badSSN = SocialSecurityNumber.parseSSN(s);
            String msg = "\"" + s 
                    + "\" should not have been interpresented as SSN \"" 
                    + badSSN ;
            fail(msg);
        } catch (NumberFormatException nfe) {
            System.out.println("Trying to interpret \"" + s 
                    + "\" as an SSN correctly caused NumberFormatException");
            System.out.println("\"" + nfe.getMessage() + "\"");
            String allCaps = nfe.getMessage().toUpperCase();
            boolean mentionEIN = allCaps.contains("EIN") 
                    || allCaps.contains("EMPLOYER IDENTIFICATION NUMBER");
            String msg = "Exception message should not mention EIN";
            assert !mentionEIN : msg;
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for interpreting \""
                    + s + "\" as an SSN";
            fail(msg);
        }
    }
    
    @Test
    public void testNoNegativeSSNs() {
        int badNumber = -RANDOM.nextInt(772989799) - 1;
        try {
            SocialSecurityNumber badSSN = new SocialSecurityNumber(badNumber);
            String msg = "Should not have been able to create SSN " 
                    + badSSN.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to use " + badNumber 
                    + " for SSN correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + badNumber + " to create SSN";
            fail(msg);
        }
    }

    @Test
    public void testNoArea773SSNs() {
        int badNumber = 773000000 + RANDOM.nextInt(1000000);
        try {
            SocialSecurityNumber badSSN = new SocialSecurityNumber(badNumber);
            String msg = "Should not have been able to create SSN " 
                    + badSSN.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create SSN with area 773 (" 
                    + badNumber + ") correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to use " 
                    + badNumber + " (area 773) for an SSN";
            fail(msg);
        }
    }

}

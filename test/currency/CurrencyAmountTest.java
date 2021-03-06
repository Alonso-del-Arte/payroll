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
package currency;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class CurrencyAmountTest {

    static final Currency DOLLARS = Currency.getInstance(Locale.US);
    static final Currency EUROS = Currency.getInstance("EUR");
    static final Currency DINARS
            = Currency.getInstance(Locale.forLanguageTag("ar-LY"));
    static final Currency YEN = Currency.getInstance(Locale.JAPAN);

    @Test
    public void testToString() {
        System.out.println("toString");
        CurrencyAmount amount = new CurrencyAmount(49989, DOLLARS);
        String expected = "$499.89";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringOtherAmount() {
        CurrencyAmount amount = new CurrencyAmount(104250, DOLLARS);
        String expected = "$1042.50";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringCentAmount() {
        CurrencyAmount amount = new CurrencyAmount(5, DOLLARS);
        String expected = "$0.05";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringOtherCentAmount() {
        CurrencyAmount amount = new CurrencyAmount(47, DOLLARS);
        String expected = "$0.47";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringNegativeCentAmount() {
        CurrencyAmount amount = new CurrencyAmount(-8, DOLLARS);
        String expected = "$-0.08";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringOtherNegativeCentAmount() {
        CurrencyAmount amount = new CurrencyAmount(-82, DOLLARS);
        String expected = "$-0.82";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringEuroAmount() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        String expected = "EUR73.20";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringDinarAmount() {
        CurrencyAmount amount = new CurrencyAmount(29505, DINARS);
        String expected = "LYD29.505";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringDirhamAmount() {
        Locale arJordan = Locale.forLanguageTag("ar-JO");
        Currency dinars = Currency.getInstance(arJordan);
        CurrencyAmount amount = new CurrencyAmount(709, dinars);
        String expected = "JOD0.709";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void testToStringYenAmount() {
        CurrencyAmount amount = new CurrencyAmount(20167, YEN);
        String expected = "JPY20167";
        String actual = amount.toString();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testReferentialEquality() {
        System.out.println("equals()");
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        assertEquals(amount, amount);
    }
    
    @Test
    public void testNotEqualsNull() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        assertNotEquals(amount, null);
    }
    
    @Test
    public void testNotEqualsOtherClass() {
        CurrencyAmount amount = new CurrencyAmount(49989, DOLLARS);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Test executed at " + now.toString());
        assertNotEquals(amount, now);
    }
    
    @Test
    public void testDiffCentsSameCurrency() {
        CurrencyAmount amountA = new CurrencyAmount(2989, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(2990, DOLLARS);
        assertNotEquals(amountA, amountB);
    }

    @Test
    public void testSameCentsSameCurrency() {
        CurrencyAmount amountA = new CurrencyAmount(2989, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(2989, DOLLARS);
        assertEquals(amountA, amountB);
    }
    
    @Test
    public void testSameCentsDiffCurrency() {
        CurrencyAmount amountA = new CurrencyAmount(2989, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(2989, YEN);
        assertNotEquals(amountA, amountB);
    }
    
    @Test
    public void testEqualsSubClass() {
        CurrencyAmount amountA = new CurrencyAmount(2989, DOLLARS);
        DollarAmount amountB = new DollarAmount(2989);
        assertEquals(amountA, amountB);
    }

    @Test
    public void testGetAmountInCents() {
        System.out.println("getAmountInCents");
        CurrencyAmount amount = new CurrencyAmount(15347, EUROS);
        long expected = 15347;
        long actual = amount.getAmountInCents();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCurrency() {
        System.out.println("getCurrency");
        CurrencyAmount amount = new CurrencyAmount(15347, EUROS);
        Currency actual = amount.getCurrency();
        assertEquals(EUROS, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testPlusNull() {
        CurrencyAmount addend = new CurrencyAmount(533, EUROS);
        CurrencyAmount result = addend.plus(null);
        System.out.println(addend.toString() + " plus null equals "
                + result.toString() + "???");
    }

    @Test
    public void testPlusDollars() {
        System.out.println("plus");
        CurrencyAmount addendA = new CurrencyAmount(205843, DOLLARS);
        CurrencyAmount addendB = new CurrencyAmount(8953, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(214796, DOLLARS);
        CurrencyAmount actual = addendA.plus(addendB);
        assertEquals(expected, actual);
    }

    @Test
    public void testPlusOtherDollars() {
        CurrencyAmount addendA = new CurrencyAmount(49989, DOLLARS);
        CurrencyAmount addendB = new CurrencyAmount(512, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(50501, DOLLARS);
        CurrencyAmount actual = addendA.plus(addendB);
        assertEquals(expected, actual);
    }

    @Test
    public void testPlusEuros() {
        CurrencyAmount addendA = new CurrencyAmount(8947, EUROS);
        CurrencyAmount addendB = new CurrencyAmount(7320, EUROS);
        CurrencyAmount expected = new CurrencyAmount(16267, EUROS);
        CurrencyAmount actual = addendA.plus(addendB);
        assertEquals(expected, actual);
    }

    @Test(expected = CurrencyConversionNeededException.class)
    public void testPlusDifferentCurrencies() {
        CurrencyAmount dollars = new CurrencyAmount(49989, DOLLARS);
        CurrencyAmount euros = new CurrencyAmount(7320, EUROS);
        CurrencyAmount result = dollars.plus(euros);
        System.out.println("Trying to add " + dollars.toString() + " to "
                + euros.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test(expected = ArithmeticException.class)
    public void testPlusTooMuch() {
        CurrencyAmount amountA = new CurrencyAmount(9000000000000000000L, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(1000000000000000000L, DOLLARS);
        CurrencyAmount result = amountA.plus(amountB);
        System.out.println("Trying to add " + amountA.toString() + " to "
                + amountB.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test
    public void testNegateHundredBucks() {
        System.out.println("negate");
        CurrencyAmount amount = new CurrencyAmount(10000, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(-10000, DOLLARS);
        CurrencyAmount actual = amount.negate();
        assertEquals(expected, actual);
    }

    @Test
    public void testNegateAlreadyNegative() {
        CurrencyAmount amount = new CurrencyAmount(-10000, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(10000, DOLLARS);
        CurrencyAmount actual = amount.negate();
        assertEquals(expected, actual);
    }

    @Test
    public void testNegateEuros() {
        CurrencyAmount amount = new CurrencyAmount(8355, EUROS);
        CurrencyAmount expected = new CurrencyAmount(-8355, EUROS);
        CurrencyAmount actual = amount.negate();
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testMinusNull() {
        CurrencyAmount minuend = new CurrencyAmount(533, EUROS);
        CurrencyAmount result = minuend.minus(null);
        System.out.println(minuend.toString() + " minus null equals "
                + result.toString() + "???");
    }

    @Test
    public void testMinusDollars() {
        System.out.println("minus");
        CurrencyAmount minuend = new CurrencyAmount(205843, DOLLARS);
        CurrencyAmount subtrahend = new CurrencyAmount(8953, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(196890, DOLLARS);
        CurrencyAmount actual = minuend.minus(subtrahend);
        assertEquals(expected, actual);
    }

    @Test
    public void testMinusOtherDollars() {
        CurrencyAmount minuend = new CurrencyAmount(49989, DOLLARS);
        CurrencyAmount subtrahend = new CurrencyAmount(512, DOLLARS);
        CurrencyAmount expected = new CurrencyAmount(49477, DOLLARS);
        CurrencyAmount actual = minuend.minus(subtrahend);
        assertEquals(expected, actual);
    }

    @Test
    public void testMinusEuros() {
        CurrencyAmount minuend = new CurrencyAmount(8947, EUROS);
        CurrencyAmount subtrahend = new CurrencyAmount(7320, EUROS);
        CurrencyAmount expected = new CurrencyAmount(1627, EUROS);
        CurrencyAmount actual = minuend.minus(subtrahend);
        assertEquals(expected, actual);
    }

    @Test(expected = CurrencyConversionNeededException.class)
    public void testMinusDifferentCurrencies() {
        CurrencyAmount dollars = new CurrencyAmount(49989, DOLLARS);
        CurrencyAmount euros = new CurrencyAmount(7320, EUROS);
        CurrencyAmount result = dollars.minus(euros);
        System.out.println("Trying to subtract " + euros.toString() + " from "
                + dollars.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test(expected = ArithmeticException.class)
    public void testMinusTooMuch() {
        CurrencyAmount amountA = new CurrencyAmount(-9000000000000000000L, 
                DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(1000000000000000000L, 
                DOLLARS);
        CurrencyAmount result = amountA.minus(amountB);
        System.out.println("Trying to subtract " + amountB.toString() + " from "
                + amountA.toString()
                + " should have caused an exception, not given result "
                + result.toString());
    }

    @Test
    public void testDollarsTimesInt() {
        System.out.println("times");
        int singleCents = 3995;
        CurrencyAmount amount = new CurrencyAmount(singleCents, DOLLARS);
        int multiplier = 5;
        int multCents = multiplier * singleCents;
        CurrencyAmount expected = new CurrencyAmount(multCents, DOLLARS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

    @Test
    public void testOtherDollarsTimesInt() {
        int singleCents = 24823;
        CurrencyAmount amount = new CurrencyAmount(singleCents, DOLLARS);
        int multiplier = 7;
        int multCents = multiplier * singleCents;
        CurrencyAmount expected = new CurrencyAmount(multCents, DOLLARS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

    @Test
    public void testEurosTimesInt() {
        int singleCents = 4358;
        CurrencyAmount amount = new CurrencyAmount(singleCents, EUROS);
        int multiplier = 12;
        int multCents = multiplier * singleCents;
        CurrencyAmount expected = new CurrencyAmount(multCents, EUROS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

    @Test
    public void testDollarsTimesDouble() {
        CurrencyAmount subTotal = new CurrencyAmount(19975, DOLLARS);
        double salesTax = 0.06;
        CurrencyAmount expected = new CurrencyAmount(1199, DOLLARS);
        CurrencyAmount actual = subTotal.times(salesTax);
        assertEquals(expected, actual);
    }

    @Test
    public void testOtherDollarsTimesDouble() {
        CurrencyAmount amount = new CurrencyAmount(108250, DOLLARS);
        double multiplier = 1.5;
        CurrencyAmount expected = new CurrencyAmount(162375, DOLLARS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

    @Test
    public void testEurosTimesDouble() {
        CurrencyAmount amount = new CurrencyAmount(5989, EUROS);
        double multiplier = 1.09;
        CurrencyAmount expected = new CurrencyAmount(6528, EUROS);
        CurrencyAmount actual = amount.times(multiplier);
        assertEquals(expected, actual);
    }

    @Test(expected = ArithmeticException.class)
    public void testOneDollarTimesPositiveInfinity() {
        CurrencyAmount oneBuck = new CurrencyAmount(100, DOLLARS);
        CurrencyAmount result = oneBuck.times(Double.POSITIVE_INFINITY);
        System.out.println(oneBuck.toString()
                + " times +Infinity is said to be "
                + result.toString() + "???");
    }

    @Test(expected = ArithmeticException.class)
    public void testOneDollarTimesNegativeInfinity() {
        CurrencyAmount oneBuck = new CurrencyAmount(100, DOLLARS);
        CurrencyAmount result = oneBuck.times(Double.NEGATIVE_INFINITY);
        System.out.println(oneBuck.toString()
                + " times -Infinity is said to be "
                + result.toString() + "???");
    }

    @Test(expected = ArithmeticException.class)
    public void testOneDollarTimesNaN() {
        CurrencyAmount oneBuck = new CurrencyAmount(100, DOLLARS);
        CurrencyAmount result = oneBuck.times(Double.NaN);
        System.out.println(oneBuck.toString() + " times NaN is said to be "
                + result.toString() + "???");
    }

    @Test
    public void testDivideByZero() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        try {
            CurrencyAmount result = amount.divides(0);
            System.out.println(amount.toString() 
                    + " divided by zero is said to be " + result.toString() 
                    + "???");
            fail("Trying to divide by zero should have caused an exception");
        } catch (IllegalArgumentException | ArithmeticException iae) {
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (Exception e) {
            String failMsg = e.getClass().getName() 
                    + " is the wrong exception to throw for division by zero";
            fail(failMsg);
        }
    }

    @Test
    public void testDivides() {
        System.out.println("divides");
        CurrencyAmount amount = new CurrencyAmount(30000000, DOLLARS);
        int divisor = 7;
        CurrencyAmount expected = new CurrencyAmount(4285714, DOLLARS);
        CurrencyAmount actual = amount.divides(divisor);
        assertEquals(expected, actual);
    }

    @Test
    public void testDivideOtherCurrency() {
        CurrencyAmount amount = new CurrencyAmount(7320, EUROS);
        int divisor = 3;
        CurrencyAmount expected = new CurrencyAmount(2440, EUROS);
        CurrencyAmount actual = amount.divides(divisor);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCompareToLesser() {
        CurrencyAmount amountA = new CurrencyAmount(49899, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(104250, DOLLARS);
        String assertionMessage = amountA.toString() + " is less than " 
                + amountB.toString();
        assertTrue(assertionMessage, amountA.compareTo(amountB) < 0);
    }
    
    @Test
    public void testCompareToEqual() {
        CurrencyAmount someAmount = new CurrencyAmount(49899, DOLLARS);
        CurrencyAmount sameAmount = new CurrencyAmount(49899, DOLLARS);
        String assertionMessage = someAmount.toString() + " is equal to " 
                + sameAmount.toString();
        assertEquals(assertionMessage, 0, someAmount.compareTo(sameAmount));
    }

    @Test
    public void testCompareToGreater() {
        CurrencyAmount amountA = new CurrencyAmount(49899, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(104250, DOLLARS);
        String assertionMessage = amountB.toString() + " is greater than " 
                + amountA.toString();
        assertTrue(assertionMessage, amountB.compareTo(amountA) > 0);
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        CurrencyAmount negBal = new CurrencyAmount(-372, DOLLARS);
        CurrencyAmount zero = new CurrencyAmount(0, DOLLARS);
        CurrencyAmount amountA = new CurrencyAmount(49989, DOLLARS);
        CurrencyAmount amountB = new CurrencyAmount(104250, DOLLARS);
        CurrencyAmount amountC = new CurrencyAmount(583047758, DOLLARS);
        ArrayList<CurrencyAmount> expected = new ArrayList<>();
        expected.add(negBal);
        expected.add(zero);
        expected.add(amountA);
        expected.add(amountB);
        expected.add(amountC);
        ArrayList<CurrencyAmount> actual = new ArrayList<>();
        actual.add(amountB);
        actual.add(amountC);
        actual.add(zero);
        actual.add(negBal);
        actual.add(amountA);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCompareDifferentCurrencies() {
        CurrencyAmount dollarsAmount = new CurrencyAmount(57380, DOLLARS);
        CurrencyAmount eurosAmount = new CurrencyAmount(57380, EUROS);
        try {
            int result = dollarsAmount.compareTo(eurosAmount);
            String failMsg = "Trying to compare " + dollarsAmount.toString() 
                    + " to " + eurosAmount.toString()
                    + " should have caused an exception, not given result " 
                    + result;
            fail(failMsg);
        } catch (CurrencyConversionNeededException curConvNeedExc) {
            System.out.println("Trying to compare " + dollarsAmount.toString() 
                    + " to " + eurosAmount.toString()
                    + " correctly caused CurrencyConversionNeededException");
            System.out.println("\"" + curConvNeedExc.getMessage() + "\"");
        } catch (RuntimeException re) {
            String failMsg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to compare " 
                    + dollarsAmount.toString() + " to " 
                    + eurosAmount.toString();
            fail(failMsg);
        }
    }
    
    @Test
    public void testParseAmount() {
        System.out.println("parseAmount");
        String s = "$198.97";
        CurrencyAmount expected = new CurrencyAmount(19897, DOLLARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseOtherDollarAmount() {
        String s = "$20";
        CurrencyAmount expected = new CurrencyAmount(2000, DOLLARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }

    @Test
    public void testParseDinarAmount() {
        String s = "LYD7063.255";
        CurrencyAmount expected = new CurrencyAmount(7063255, DINARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testParseRandomDinarAmount() {
        int dinars = (int) Math.floor(Math.random() * 10000);
        int darahim = (int) Math.floor(Math.random() * 950) + 100;
        String s = "LYD" + dinars + "." + darahim;
        int cents = dinars * 1000 + darahim;
        CurrencyAmount expected = new CurrencyAmount(cents, DINARS);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testParseRandomKronerAmount() {
        Locale swedishLoc = Locale.forLanguageTag("sv-SE");
        Currency kroner = Currency.getInstance(swedishLoc);
        int kronor = (int) Math.floor(Math.random() * 10000);
        int cent = (int) Math.floor(Math.random() * 85) + 10;
        String s = "SEK" + kronor + "." + cent;
        int cents = kronor * 100 + cent;
        CurrencyAmount expected = new CurrencyAmount(cents, kroner);
        CurrencyAmount actual = CurrencyAmount.parseAmount(s);
        assertEquals(expected, actual);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNoParseWithoutSymbol() {
        String s = "x435.80";
        CurrencyAmount amount = CurrencyAmount.parseAmount(s);
        System.out.println("Should not have parsed \"" + s + "\" as " 
                + amount.toString());
    }
    
    @Test(expected = NullPointerException.class)
    public void testConstructorRefuseNullCurrency() {
        CurrencyAmount badAmount = new CurrencyAmount(0, null);
        System.out.println("Should not have been able to create CurrencyAmount@"
                + badAmount.hashCode() + " with null currency");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRefuseMetals() {
        Currency platinum = Currency.getInstance("XPT");
        CurrencyAmount ptAmount = new CurrencyAmount(102153, platinum);
        System.out.println("Should not have been able to create "
                + ptAmount.toString());
    }

}

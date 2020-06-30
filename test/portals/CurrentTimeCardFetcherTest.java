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
package portals;

import currency.CurrencyAmount;
import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import payroll.TimeCard;
import time.DateTimeRange;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the CurrentTimeCardFetcher class.
 * @author Alonso del Arte
 */
public class CurrentTimeCardFetcherTest {
    
    /**
     * Test of writeNewDemoCard method, of class CurrentTimeCardFetcher.
     */
    @Test
    public void testWriteNewDemoCard() {
        System.out.println("writeNewDemoCard");
        TimeCard card = null;
        TimeCard expResult = null;
//        TimeCard result = CurrentTimeCardFetcher.writeNewDemoCard(card);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retrieveCard method, of class CurrentTimeCardFetcher.
     */
    @Test
    public void testRetrieveCard() {
        System.out.println("retrieveCard");
        CurrentTimeCardFetcher instance = null;
        TimeCard expResult = null;
//        TimeCard result = instance.retrieveCard();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putCardBack method, of class CurrentTimeCardFetcher.
     */
    @Test
    public void testPutCardBack_0args() {
        System.out.println("putCardBack");
        CurrentTimeCardFetcher instance = null;
//        instance.putCardBack();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putCardBack method, of class CurrentTimeCardFetcher.
     */
    @Test
    public void testPutCardBack_TimeCard() {
        System.out.println("putCardBack");
        TimeCard card = null;
        CurrentTimeCardFetcher instance = null;
//        instance.putCardBack(card);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmployee method, of class CurrentTimeCardFetcher.
     */
    @Test
    public void testGetEmployee() {
        System.out.println("getEmployee");
        CurrentTimeCardFetcher instance = null;
        Employee expResult = null;
//        Employee result = instance.getEmployee();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

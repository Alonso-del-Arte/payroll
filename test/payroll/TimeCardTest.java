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
package payroll;

import currency.CurrencyAmount;
import entities.Employee;
import entities.idnumbers.SocialSecurityNumber;
import time.DateTimeRange;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Currency;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the TimeCard class.
 * @author Alonso del Arte
 */
public class TimeCardTest {
    
    private static final SocialSecurityNumber SOC_SEC_NUM 
            = new SocialSecurityNumber(750101729);
    
    private static final Employee TEST_EMPLOYEE 
            = new Employee("John Lopez", SOC_SEC_NUM);
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    /**
     * The test employee's regular hourly wage rate, $40.00 per hour.
     */
    private static final CurrencyAmount TEST_HOURLY_WAGE 
            = new CurrencyAmount(4000, DOLLARS);
    
    /**
     * The beginning of the first test period, at least two weeks before today, 
     * on a Monday at midnight.
     */
    private static final LocalDateTime TEST_PRIOR_PERIOD_START;
    
    /**
     * The end of the first test period, at least a week before today, on a 
     * Sunday at 11:59 p.m.
     */
    private static final LocalDateTime TEST_PRIOR_PERIOD_END;
    
    /**
     * The beginning of the first test period, at least two weeks before today, 
     * on a Monday at midnight.
     */
    private static final LocalDateTime TEST_CURRENT_PERIOD_START;
    
    /**
     * The end of the first test period, at least a week before today, on a 
     * Sunday at 11:59 p.m.
     */
    private static final LocalDateTime TEST_CURRENT_PERIOD_END;
    
    static {
        LocalDate current = LocalDate.now();
        System.out.println("Today is " 
                + current.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        DayOfWeek dayOfWeek = current.getDayOfWeek();
        int adjustToMonday = dayOfWeek.ordinal();
        current = current.minusDays(adjustToMonday);
        TEST_CURRENT_PERIOD_START = current.atTime(LocalTime.MIDNIGHT);
        TEST_CURRENT_PERIOD_END = current.plusDays(13).atTime(LocalTime.MAX);
        TEST_PRIOR_PERIOD_START = TEST_CURRENT_PERIOD_START.minusDays(14);
        TEST_PRIOR_PERIOD_END = TEST_CURRENT_PERIOD_END.minusDays(14);
    }
    
    private static TimeCard testPriorPeriodCard, testCurrentPeriodCard;
    
    @BeforeClass
    public static void setUpClass() {
        TEST_EMPLOYEE.setHourlyRate(TEST_HOURLY_WAGE);
    }
    
    @Before
    public void setUp() {
        testPriorPeriodCard = new TimeCard(TEST_EMPLOYEE, TEST_PRIOR_PERIOD_START, 
                TEST_PRIOR_PERIOD_END);
        testCurrentPeriodCard = new TimeCard(TEST_EMPLOYEE, 
                TEST_CURRENT_PERIOD_START, TEST_CURRENT_PERIOD_END);
    }
    
    /**
     * Test of getEmployee method, of class TimeCard.
     */
    @Test
    public void testGetEmployee() {
        System.out.println("getEmployee");
        assertEquals(TEST_EMPLOYEE, testPriorPeriodCard.getEmployee());
    }

    /**
     * Test of getStartTime method, of class TimeCard.
     */
    @Test
    public void testGetStartTime() {
        System.out.println("getStartTime");
        assertEquals(TEST_PRIOR_PERIOD_START, testPriorPeriodCard.getStartTime());
        assertEquals(TEST_CURRENT_PERIOD_START, testCurrentPeriodCard.getStartTime());
    }

    /**
     * Test of getEndTime method, of class TimeCard.
     */
    @Test
    public void testGetEndTime() {
        System.out.println("getEndTime");
        assertEquals(TEST_PRIOR_PERIOD_END, testPriorPeriodCard.getEndTime());
        assertEquals(TEST_CURRENT_PERIOD_END, testCurrentPeriodCard.getEndTime());
    }
    
    @Test
    public void testGetMinutesSoFar() {
        System.out.println("getMinutesSoFar");
        assertEquals(0, testCurrentPeriodCard.getMinutesSoFar());
    }
    
    @Test
    public void testGetPreTaxTotal() {
        System.out.println("getPreTaxTotal");
        LocalDateTime blockBegin = TEST_CURRENT_PERIOD_START.plusHours(8);
        LocalDateTime blockFinish = blockBegin.plusHours(4);
        DateTimeRange block = new DateTimeRange(blockBegin, blockFinish);
        testCurrentPeriodCard.addTimeBlock(block);
        CurrencyAmount expected = TEST_HOURLY_WAGE.times(4);
        CurrencyAmount actual = testCurrentPeriodCard.getPreTaxTotal();
        assertEquals(expected, actual);
    }
    
    /**
     * Test of isCurrent method, of class TimeCard.
     */
    @Test
    public void testIsCurrent() {
        System.out.println("isCurrent");
        String msg = "Card that started on " + TEST_CURRENT_PERIOD_START.toString() 
                + " and is slated to end on " + TEST_CURRENT_PERIOD_END.toString() 
                + " should be considered current";
        assert testCurrentPeriodCard.isCurrent() : msg;
    }
    
    @Test
    public void testCurrentCardCanNotBeVerified() {
        try {
            testCurrentPeriodCard.markVerified();
            String msg = "Should not have been able to mark current card as verified";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to verify a current card correctly " 
                    + "caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to verify an"
                    + " ongoing card";
            fail(msg);
        }
    }

    /**
     * Another test of isCurrent method, of class TimeCard.
     */
    @Test
    public void testIsNotCurrent() {
        String msg = "Card that started on " 
                + TEST_PRIOR_PERIOD_START.toString() 
                + " and ended on " + TEST_PRIOR_PERIOD_END.toString() 
                + " should not be considered current";
        assert !testPriorPeriodCard.isCurrent() : msg;
    }

    /**
     * Test of isActive method, of class TimeCard.
     */
    @Test
    public void testIsActive() {
        System.out.println("isActive");
        String msg = "Newly initialized time card should be active";
        assert testPriorPeriodCard.isActive() : msg;
    }
    
    /**
     * Test of addTimeBlock method, of class TimeCard.
     */
    @Test
    public void testAddTimeBlock() {
        System.out.println("addTimeBlock");
        LocalDateTime blockBegin = TEST_CURRENT_PERIOD_START.plusHours(8);
        LocalDateTime blockFinish = blockBegin.plusHours(4);
        DateTimeRange block = new DateTimeRange(blockBegin, blockFinish);
        testCurrentPeriodCard.addTimeBlock(block);
        assertEquals(240, testCurrentPeriodCard.getMinutesSoFar());
    }
    
    @Test
    public void testCanNotAddOutsideTimeBlock() {
        LocalDateTime blockBegin = TEST_PRIOR_PERIOD_START.plusHours(8);
        LocalDateTime blockFinish = blockBegin.plusHours(4);
        DateTimeRange block = new DateTimeRange(blockBegin, blockFinish);
        try {
            testCurrentPeriodCard.addTimeBlock(block);
            String msg = "Should not have been able to add time block starting on "
                    + blockBegin.toString() + " to card starting on " 
                    + TEST_CURRENT_PERIOD_START.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to add time block starting on " 
                    + blockBegin.toString() + " to card starting on " 
                    + TEST_CURRENT_PERIOD_START.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage());
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add time block starting on "
                    + blockBegin.toString() + " to card starting on " 
                    + TEST_CURRENT_PERIOD_START.toString();
            fail(msg);
        }
    }
    
    @Test
    public void testCanNotAddTimeBlockToInactiveCard() {
        LocalDateTime blockBegin = TEST_PRIOR_PERIOD_START.plusHours(8);
        LocalDateTime blockFinish = blockBegin.plusHours(4);
        DateTimeRange block = new DateTimeRange(blockBegin, blockFinish);
        testPriorPeriodCard.markInactive();
        try {
            testPriorPeriodCard.addTimeBlock(block);
            String msg 
                    = "Should not have been able to add time block to inactive card";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to add time block to inactive card " 
                    + "correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage());
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to add time block "
                    + "to inactive card";
            fail(msg);
        }
    }
    
    @Test
    public void testIsNotPunchedIn() {
        String msg = "Card should not be initialized as punched in";
        assert !testCurrentPeriodCard.isPunchedIn() : msg;
    }
    
    @Test
    public void testIsPunchedIn() {
        System.out.println("isPunchedIn");
        testCurrentPeriodCard.punchIn();
        String msg = "Card should be recognized as punched in after punching in";
        assert testCurrentPeriodCard.isPunchedIn() : msg;
    }
    
    @Test
    public void testPunchOut() {
        System.out.println("punchOut");
        testCurrentPeriodCard.punchIn();
        System.out.println("Punched in at " + LocalDateTime.now().toString());
        testCurrentPeriodCard.punchOut();
        System.out.println("Punched out at " + LocalDateTime.now().toString());
        String msg = "Card should not be punched in after punching out";
        assert !testCurrentPeriodCard.isPunchedIn() : msg;
    }

    /**
     * Test of hasBeenVerified method, of class TimeCard.
     */
    @Test
    public void testHasBeenVerified() {
        System.out.println("hasBeenVerified");
        String msg = "Newly initialized time card should not be verified yet";
        assert !testPriorPeriodCard.hasBeenVerified() : msg;
        testPriorPeriodCard.markVerified();
        msg = "Verified time card should be marked as such";
        assert testPriorPeriodCard.hasBeenVerified() : msg;
    }
    
    @Test
    public void testUnverifiedCardCanNotBePaid() {
        String msg = "Time card should not be verified yet";
        assert !testPriorPeriodCard.hasBeenVerified() : msg;
        try {
            testPriorPeriodCard.markPaid();
            msg = "Should not have been able to mark an unverified card as paid";
            fail(msg);
        } catch (IllegalStateException ise) {
            System.out.println("Trying to mark as paid an unverified card " 
                    + "correctly caused IllegalStateException");
            System.out.println("\"" + ise.getMessage() + "\"");
        } catch (RuntimeException re) {
            msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to mark " 
                    + "unverified card as paid";
            fail(msg);
        }
    }

    /**
     * Test of hasBeenPaid method, of class TimeCard.
     */
    @Test
    public void testHasBeenPaid() {
        System.out.println("hasBeenPaid");
        testPriorPeriodCard.markVerified();
        String msg = "Verified time card should not be marked paid just yet";
        assert !testPriorPeriodCard.hasBeenPaid() : msg;
        testPriorPeriodCard.markPaid();
        msg = "Paid card should be marked as such";
        assert testPriorPeriodCard.hasBeenPaid() : msg;
    }
    
    @Test
    public void testPaidCardShouldBeInactive() {
        testPriorPeriodCard.markVerified();
        testPriorPeriodCard.markPaid();
        String msg = "Paid card should be inactive";
        assert !testPriorPeriodCard.isActive() : msg;
    }
    
    @Test
    public void testBadTimesForCard() {
        try {
            TimeCard card = new TimeCard(TEST_EMPLOYEE, TEST_PRIOR_PERIOD_END, TEST_PRIOR_PERIOD_START);
            String msg = "Should not have been able to create card " 
                    + card.toString() + " that starts on " 
                    + TEST_PRIOR_PERIOD_END.toString() + " and ends on " 
                    + TEST_PRIOR_PERIOD_START.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to make time card that starts on " 
                    + TEST_PRIOR_PERIOD_END.toString() + " and ends on " 
                    + TEST_PRIOR_PERIOD_START.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for time card that starts on "
                    + TEST_PRIOR_PERIOD_END.toString() + " and ends on " 
                    + TEST_PRIOR_PERIOD_START.toString();
            fail(msg);
        }
    }

    @After
    public void tearDown() {
        //
    }
    
    @AfterClass
    public static void tearDownClass() {
        //
    }
    
}

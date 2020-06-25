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
package time;

import currency.CurrencyAmount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class DateTimeRangeTest {
    
    private static final LocalDateTime TEST_PERIOD_START 
            = LocalDateTime.now().minusYears(2).minusHours(22);
    
    private static final LocalDateTime TEST_PERIOD_END 
            = LocalDateTime.now().minusMinutes(30);
    
    /**
     * Test of getStart method, of class DateTimeRange.
     */
    @Test
    public void testGetStart() {
        System.out.println("getStart");
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        assertEquals(TEST_PERIOD_START, range.getStart());
    }

    /**
     * Test of getEnd method, of class DateTimeRange.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getEnd");
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        assertEquals(TEST_PERIOD_END, range.getEnd());
    }

    /**
     * Test of getDuration method, of class DateTimeRange.
     */
    @Test
    public void testGetDuration() {
        System.out.println("getDuration");
        LocalTime eightAM = LocalTime.of(8, 0);
        LocalTime fourPM = LocalTime.of(16, 0);
        LocalDate today = LocalDate.now();
        LocalDateTime eightHoursBegin = LocalDateTime.of(today, eightAM);
        LocalDateTime eightHoursEnd = LocalDateTime.of(today, fourPM);
        DateTimeRange range = new DateTimeRange(eightHoursBegin, eightHoursEnd);
        long expected = 8L;
        long actual = range.getDuration(ChronoUnit.HOURS);
        assertEquals(expected, actual);
    }
    
    /**
     * Test of contains method, of class DateTimeRange.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        LocalDateTime time = TEST_PERIOD_START.plusDays(3);
        String msg = range.toString() + " should be found to contain " 
                + time.toString();
        assert range.contains(time) : msg;
    }
    
    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testDoesNotContain() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        LocalDateTime time = TEST_PERIOD_START.minusYears(7);
        String msg = range.toString() + " should not be found to contain " 
                + time.toString();
        assert !range.contains(time) : msg;
    }

    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testContainsItself() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        String msg = "Range " + range.toString() 
                + " should be found to contain itself";
        assert range.contains(range) : msg;
    }
    
    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testDoesNotContainEarlierOrLaterRange() {
        DateTimeRange longAgoRange 
                = new DateTimeRange(TEST_PERIOD_START.minusYears(30), 
                        TEST_PERIOD_END.minusYears(20));
        DateTimeRange recentRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        String msg = "Range " + longAgoRange.toString() 
                + " should not be found to contain " 
                + recentRange.toString();
        assert !longAgoRange.contains(recentRange) : msg;
        msg = "Range " + recentRange.toString() 
                + " should not be found to contain with " 
                + longAgoRange.toString();
        assert !recentRange.contains(longAgoRange) : msg;
    }

    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testContainProperSubrange() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange other = new DateTimeRange(TEST_PERIOD_START.plusDays(3), 
                TEST_PERIOD_END.minusDays(4));
        String msg = range.toString() + " should be found to contain " 
                + other.toString();
        assert range.contains(other) : msg;
    }

    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testContainSameStartSubrange() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange other = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END.minusDays(3));
        String msg = range.toString() + " should be found to contain " 
                + other.toString();
        assert range.contains(other) : msg;
    }

    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testDoesNotContainSameStartLaterFinish() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange other = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END.plusDays(3));
        String msg = range.toString() + " should not be found to contain " 
                + other.toString();
        assert !range.contains(other) : msg;
    }

    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testContainSameFinishSubrange() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange other = new DateTimeRange(TEST_PERIOD_START.plusDays(3), 
                TEST_PERIOD_END);
        String msg = range.toString() + " should be found to contain " 
                + other.toString();
        assert range.contains(other) : msg;
    }

    /**
     * Another test of contains method, of class DateTimeRange.
     */
    @Test
    public void testDoesNotContainSameFinishEarlierStart() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange other = new DateTimeRange(TEST_PERIOD_START.minusDays(3), 
                TEST_PERIOD_END);
        String msg = range.toString() + " should not be found to contain " 
                + other.toString();
        assert !range.contains(other) : msg;
    }

    /**
     * Test of overlaps method, of class DateTimeRange.
     */
    @Test
    public void testOverlaps() {
        System.out.println("overlaps");
        DateTimeRange earlierStartRange 
                = new DateTimeRange(TEST_PERIOD_START.minusWeeks(7), 
                        TEST_PERIOD_END);
        DateTimeRange laterEndRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END.plusWeeks(8));
        String msg = "Range " + earlierStartRange.toString() 
                + " should be found to overlap with " + laterEndRange.toString();
        assert earlierStartRange.overlaps(laterEndRange) : msg;
    }
    
    @Test
    public void testDoesNotOverlap() {
        DateTimeRange longAgoRange 
                = new DateTimeRange(TEST_PERIOD_START.minusYears(30), 
                        TEST_PERIOD_END.minusYears(20));
        DateTimeRange recentRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        String msg = "Range " + longAgoRange.toString() 
                + " should not be found to overlap with " 
                + recentRange.toString();
        assert !longAgoRange.overlaps(recentRange) : msg;
        msg = "Range " + recentRange.toString() 
                + " should not be found to overlap with " 
                + longAgoRange.toString();
        assert !recentRange.overlaps(longAgoRange) : msg;
    }

    /**
     * Test of merge method, of class DateTimeRange.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        LocalDateTime secondRangeStart = TEST_PERIOD_END.minusDays(20);
        LocalDateTime secondRangeEnd = TEST_PERIOD_END.plusMonths(3);
        DateTimeRange firstRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange secondRange = new DateTimeRange(secondRangeStart, 
                secondRangeEnd);
        DateTimeRange expected = new DateTimeRange(TEST_PERIOD_START, 
                secondRangeEnd);
        DateTimeRange actual = firstRange.merge(secondRange);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDoNotMergeNonOverlapping() {
        DateTimeRange longAgoRange 
                = new DateTimeRange(TEST_PERIOD_START.minusYears(30), 
                        TEST_PERIOD_END.minusYears(20));
        DateTimeRange recentRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        try {
            DateTimeRange badMerge = longAgoRange.merge(recentRange);
            String msg = "Should not have been able to merge " 
                    + longAgoRange.toString() + " and " + recentRange.toString() 
                    + " to create " + badMerge.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to merge non-overlapping ranges correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception for trying to merge " 
                    + longAgoRange.toString() + " and " + recentRange.toString();
            fail(msg);
        }
    }

    /**
     * Test of split method, of class DateTimeRange.
     */
    @Test
    public void testSplit() {
        System.out.println("split");
        LocalTime workDayEnter = LocalTime.of(8, 0);
        LocalTime lunchBegin = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime workDayExit = LocalTime.of(17, 0);
        LocalDate today = LocalDate.now();
        LocalDateTime workDayBegin = LocalDateTime.of(today, workDayEnter);
        LocalDateTime workDayFinish = LocalDateTime.of(today, workDayExit);
        DateTimeRange workDayWithLunch = new DateTimeRange(workDayBegin, 
                workDayFinish);
        LocalDateTime lunchHourBegin = LocalDateTime.of(today, lunchBegin);
        LocalDateTime lunchHourEnd = LocalDateTime.of(today, lunchEnd);
        DateTimeRange lunchHour = new DateTimeRange(lunchHourBegin, 
                lunchHourEnd);
        DateTimeRange morningShift = new DateTimeRange(workDayBegin, 
                lunchHourBegin);
        DateTimeRange afternoonShift = new DateTimeRange(lunchHourEnd, 
                workDayFinish);
        DateTimeRange[] expected = {morningShift, afternoonShift};
        DateTimeRange[] alternative = {afternoonShift, morningShift};
        DateTimeRange[] actual = workDayWithLunch.split(lunchHour);
        if (!Arrays.equals(expected, actual)) {
            String msg 
                    = "Arrays should contain same elements, irrespective of order";
            assertArrayEquals(msg, alternative, actual);
        }
    }

    /**
     * Another test of split method, of class DateTimeRange.
     */
    @Test
    public void testDoNotSplitByNotContained() {
        DateTimeRange recentRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange oldRange
                = new DateTimeRange(TEST_PERIOD_START.minusYears(30), 
                        TEST_PERIOD_END.minusYears(20));
        try {
            DateTimeRange[] badSplit = recentRange.split(oldRange);
            for (DateTimeRange splitoff : badSplit) {
                System.out.println("Split range: " + splitoff.toString());
            }
            String msg = "Should not have been able to split " 
                    + recentRange.toString() + " by " + oldRange.toString();
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to split " + recentRange.toString() 
                    + " by " + oldRange.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw by trying to split " 
                    + recentRange.toString() + " by " + oldRange.toString();
            fail(msg);
        }
    }

    /**
     * Test of toString method, of class DateTimeRange.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        String expResult = TEST_PERIOD_START.toString() + " to " 
                + TEST_PERIOD_END.toString();
        String result = range.toString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testReferentialEquality() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        assertEquals(range, range);
    }
    
    @Test
    public void testNotEqualsNull() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        assertNotEquals(range, null);
    }

    /**
     * Test of equals method, of class DateTimeRange.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        DateTimeRange someRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        DateTimeRange sameRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END);
        assertEquals(someRange, sameRange);
    }
    
    @Test
    public void testNotEqualsObjectOtherClass() {
        Currency currency = Currency.getInstance(Locale.ITALY);
        CurrencyAmount amount = new CurrencyAmount(10000, currency);
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        assertNotEquals(range, amount);
    }
    
    @Test
    public void testNotEqualDifferentStart() {
        DateTimeRange earlierStartRange 
                = new DateTimeRange(TEST_PERIOD_START.minusWeeks(7), 
                        TEST_PERIOD_END);
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        assertNotEquals(earlierStartRange, range);
    }
    
    @Test
    public void testNotEqualsDifferentEnd() {
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        DateTimeRange laterEndRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END.plusWeeks(8));
        assertNotEquals(range, laterEndRange);
    }
    
    private ArrayList<DateTimeRange> workDayRanges() {
        LocalTime workDayEnter = LocalTime.of(8, 0);
        LocalTime lunchBegin = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime workDayExit = LocalTime.of(17, 0);
        LocalDate today = LocalDate.now();
        LocalDate todayYearAgo = today.minusYears(1);
        DateTimeRange preLunchShift, postLunchShift;
        LocalDateTime punchIn, punchOut;
        ArrayList<DateTimeRange> list = new ArrayList<>();
        for (LocalDate day = todayYearAgo; day.isBefore(today); 
                day = day.plusDays(1)) {
            switch (day.getDayOfWeek()) {
                case SATURDAY:
                case SUNDAY: 
                    break;
                default:
                    punchIn = LocalDateTime.of(day, workDayEnter);
                    punchOut = LocalDateTime.of(day, lunchBegin);
                    preLunchShift = new DateTimeRange(punchIn, punchOut);
                    punchIn = LocalDateTime.of(day, lunchEnd);
                    punchOut = LocalDateTime.of(day, workDayExit);
                    postLunchShift = new DateTimeRange(punchIn, punchOut);
                    list.add(preLunchShift);
                    list.add(postLunchShift);
            }
        }
        return list;
    }

    /**
     * Test of hashCode method, of class DateTimeRange.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ArrayList<DateTimeRange> list = workDayRanges();
        HashSet<Integer> hashes = new HashSet<>();
        list.forEach((range) -> {
            hashes.add(range.hashCode());
        });
        String msg = "List of ranges should have as many elements as set of hashes";
        assertEquals(msg, list.size(), hashes.size());
    }
    
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        DateTimeRange longAgoRange 
                = new DateTimeRange(TEST_PERIOD_START.minusYears(30), 
                        TEST_PERIOD_END.minusYears(20));
        DateTimeRange oneYearBackRange 
                = new DateTimeRange(TEST_PERIOD_START.minusYears(1), 
                        TEST_PERIOD_END.minusYears(1));
        DateTimeRange range = new DateTimeRange(TEST_PERIOD_START, TEST_PERIOD_END);
        DateTimeRange laterEndRange = new DateTimeRange(TEST_PERIOD_START, 
                TEST_PERIOD_END.plusWeeks(8));
        ArrayList<DateTimeRange> expected = new ArrayList<>();
        expected.add(longAgoRange);
        expected.add(oneYearBackRange);
        expected.add(range);
        expected.add(laterEndRange);
        ArrayList<DateTimeRange> actual = new ArrayList<>();
        actual.add(range);
        actual.add(oneYearBackRange);
        actual.add(longAgoRange);
        actual.add(laterEndRange);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testBadParamConstructor() {
        try {
            DateTimeRange badRange = new DateTimeRange(TEST_PERIOD_END, 
                    TEST_PERIOD_START);
            String msg = "Should not have been able to create range " 
                    + badRange.toString() + " (ends before it begins)";
            fail(msg);
        } catch (IllegalArgumentException iae) {
            System.out.println("Trying to create range the starts on " 
                    + TEST_PERIOD_END.toString() + " and ends on " 
                    + TEST_PERIOD_START.toString() 
                    + " correctly caused IllegalArgumentException");
            System.out.println("\"" + iae.getMessage() + "\"");
        } catch (RuntimeException re) {
            String msg = re.getClass().getName() 
                    + " is the wrong exception to throw for trying to create range that starts on "
                    + TEST_PERIOD_END.toString() + " and ends on " 
                    + TEST_PERIOD_START.toString();
            fail(msg);
        }
    }
    
}

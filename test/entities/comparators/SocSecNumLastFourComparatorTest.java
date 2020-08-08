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
package entities.comparators;

import entities.Person;
import entities.idnumbers.SocialSecurityNumber;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the SocSecNumLastFourComparator class.
 * @author Alonso del Arte
 */
public class SocSecNumLastFourComparatorTest {
    
    /**
     * Fictional SSN for Alice Pieszecki, a fictional character. This SSN should 
     * not be assigned to anyone born prior to 2011.
     */
    private static final SocialSecurityNumber ALICES_SSN 
            = new SocialSecurityNumber(751981729);
    
    /**
     * Fictional SSN for Roberto "Bob" Mu&ntilde;&oacute;z, a fictional 
     * character. This SSN should not be assigned to any real person born prior 
     * to 2011.
     */
    private static final SocialSecurityNumber BOBS_SSN 
            = new SocialSecurityNumber(753543089);
    
    /**
     * Fictional SSN for Carol Hussein, a fictional character. This SSN should 
     * not be assigned to any real person born prior to 2011.
     */
    private static final SocialSecurityNumber CAROLS_SSN 
            = new SocialSecurityNumber(752267048);
    
    /**
     * Fictional SSN for David W&#x142;odkowski, a fictional character. This SSN 
     * should not be assigned to any real person born prior to 2011.
     */
    private static final SocialSecurityNumber DAVIDS_SSN 
            = new SocialSecurityNumber(750609598);

    /**
     * Alice Pieszecki has SSN 751-98-1729. She is a fictional character, and 
     * her SSN should not match that of any actor who has played her in a stage 
     * play, TV show or movie.
     */
    private static final Person ALICE = new Person("Alice Pieszecki", 
            ALICES_SSN);
    
    /**
     * Roberto "Bob" Mu&ntilde;&oacute;z has SSN 753-54-3089. He is a fictional 
     * character, and his SSN should not match that of any actor who has played 
     * him in a stage play, TV show or movie.
     */
    private static final Person BOB = new Person("Roberto Mu\u00F1\u00F3z", 
            BOBS_SSN);
    
    /**
     * Carol Hussein has SSN 752-26-7048. She is a fictional character, and her 
     * SSN should not match that of any actor who has played her in a stage 
     * play, TV show or movie.
     */
    private static final Person CAROL = new Person("Carol Hussein", CAROLS_SSN);
    
    /**
     * David W&#x142;odkowski has SSN 750-60-9598. He is a fictional character, 
     * and his SSN should not match that of any actor who has played him in a 
     * stage play, TV show or movie.
     */
    private static final Person DAVID = new Person("David W\u0142odkowski", 
            DAVIDS_SSN);
    
    private static void printPeopleList(ArrayList<Person> list) {
        list.forEach((person) -> {
            System.out.println(person.getFullName() + ", SSN: " 
                    + ((SocialSecurityNumber) person.getTIN()).toRedactedString());
        });
    }
    
    /**
     * Test of compare method, of class SocSecNumLastFourComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        ArrayList<Person> expected = new ArrayList<>();
        expected.add(ALICE);
        expected.add(BOB);
        expected.add(CAROL);
        expected.add(DAVID);
        System.out.println("Expected list");
        printPeopleList(expected);
        System.out.println();
        ArrayList<Person> actual = new ArrayList<>();
        actual.add(DAVID);
        actual.add(ALICE);
        actual.add(CAROL);
        actual.add(BOB);
        System.out.println("Unsorted list");
        printPeopleList(actual);
        System.out.println();
        Comparator comparator = new SocSecNumLastFourComparator();
        actual.sort(comparator);
        System.out.println("The unsorted list after sorting should match expected");
        printPeopleList(actual);
        assertEquals(expected, actual);
    }
    
}

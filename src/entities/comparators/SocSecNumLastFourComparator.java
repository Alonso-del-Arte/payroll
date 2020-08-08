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

import java.util.Comparator;

/**
 * Compares persons according to the last four numbers (the serial number) of 
 * their Social Security Numbers (SSNs). This is provided so that a list of 
 * people can be sorted by SSN without disclosing which persons have SSNs with 
 * lower or higher area and group numbers.
 * @author Alonso del Arte
 */
public class SocSecNumLastFourComparator implements Comparator<Person> {

    /**
     * Compares two persons according to the last four (serial numbers) of their 
     * Social Security Numbers (SSNs). The area and group numbers are ignored 
     * for this comparison.
     * @param personA The first person to compare. For example, a person with 
     * SSN 750-03-5082.
     * @param personB The second person to compare. Three examples: a person 
     * with SSN 753-20-0350, another with SSN 000-21-5082 and another with SSN 
     * 750-01-7768.
     * @return A negative number, preferably &minus;1, if <code>personA</code>'s 
     * SSN last four is less than <code>personB</code>'s; zero if 
     * <code>personA</code> and <code>personB</code> have the same SSN last 
     * four; and a positive number, preferably 1, if <code>personA</code>'s SSN 
     * last four is more than <code>personB</code>'s. Given the example for 
     * <code>personA</code> and the three examples for <code>personB</code>, 
     * this function should return &minus;1 (or some other negative number), 0 
     * and 1 (or some other positive number), respectively.
     */
    @Override
    public int compare(Person personA, Person personB) {
        int personALast4 = ((SocialSecurityNumber) personA.getTIN()).getLastFour();
        int personBLast4 = ((SocialSecurityNumber) personB.getTIN()).getLastFour();
        return Integer.compare(personALast4, personBLast4);
    }
    
}

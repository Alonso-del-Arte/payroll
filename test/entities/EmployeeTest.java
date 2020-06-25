/*
 * Copyright (C) 2020 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package entities;

import currency.CurrencyAmount;
import entities.idnumbers.SocialSecurityNumber;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alonso del Arte
 */
public class EmployeeTest {
    
    private static final SocialSecurityNumber SOC_SEC_NUM 
            = new SocialSecurityNumber(78051120);
    
    private static final Employee TEST_EMPLOYEE 
            = new Employee("Hilda Schrader Whitcher", SOC_SEC_NUM);
    
    /**
     * Test of getHourlyRate method, of class Employee.
     */
    @Test
    public void testGetHourlyRate() {
        System.out.println("getHourlyRate");
        Currency dollars = Currency.getInstance(Locale.US);
        CurrencyAmount expected = new CurrencyAmount(3500, dollars);
        TEST_EMPLOYEE.setHourlyRate(expected);
        CurrencyAmount actual = TEST_EMPLOYEE.getHourlyRate();
        assertEquals(expected, actual);
    }

    /**
     * Test of setHourlyRate method, of class Employee.
     */
    @Test
    public void testSetHourlyRate() {
        System.out.println("setHourlyRate");
        CurrencyAmount rate = null;
        Employee instance = null;
//        instance.setHourlyRate(rate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

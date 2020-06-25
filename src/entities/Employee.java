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
package entities;

import currency.CurrencyAmount;
import entities.idnumbers.SocialSecurityNumber;

import java.util.Currency;
import java.util.Locale;

/**
 * A class to represent an employee.
 * @author Alonso del Arte
 */
public class Employee extends Person {
    
    private static final long serialVersionUID = 4993762592960754194L;
    
    private String rank = "Employee";
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final CurrencyAmount ZERO_DOLLARS = new CurrencyAmount(0, DOLLARS);
    
    private CurrencyAmount hourlyPayRate;
    
    public String getJobTitle() {
        return this.rank;
    }
    
    public void setJobTitle(String title) {
        this.rank = title;
    }
    
    public CurrencyAmount getHourlyRate() {
        return this.hourlyPayRate;
    }
    
    public void setHourlyRate(CurrencyAmount rate) {
        this.hourlyPayRate = rate;
    }
    
    
    // TODO: Eliminate need for this "null employee" 
    public static Employee getNullEmployee() {
        SocialSecurityNumber ssn = new SocialSecurityNumber(0);
        return new Employee("None", ssn);
    }
    
    public Employee(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
        this.hourlyPayRate = ZERO_DOLLARS;
    }
    
}

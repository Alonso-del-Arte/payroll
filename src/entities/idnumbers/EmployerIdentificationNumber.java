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

/**
 * Represents an Employer Identification Numbers (EIN). Maybe this class isn't 
 * as useful in the payroll project as it is in the banking account project. It 
 * is included in the payroll project more for the sake of completeness.
 * @author Alonso del Arte
 */
public class EmployerIdentificationNumber extends TaxpayerIdentificationNumber {
    
    private static final long serialVersionUID = 4549583355422715492L;
    
    static boolean correctEINDashPlacement(String s) {
        return (s.indexOf('-') == 2) && (s.indexOf('-', 3) == -1);
    }
    
    @Override
    int hashCodeOffset() {
        return -1073741824;
    }
    
    @Override
    public String toString() {
        String digits = Integer.toString(this.idNum);
        return digits.substring(0, 2) + "-" + digits.substring(2);
    }
    
    // TODO: Determine what validation is necessary
    public EmployerIdentificationNumber(int number) {
        super(number);
    }
    
}

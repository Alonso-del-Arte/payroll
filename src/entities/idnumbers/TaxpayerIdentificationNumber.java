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

import java.io.Serializable;
import java.text.DecimalFormatSymbols;

/**
 * A class to represent taxpayer identification numbers. We take it on faith 
 * that the correct numbers are provided. There is no checking for uniqueness. 
 * This class is immutable, so no setters are provided. No getters are provided 
 * either, those are left to the subclasses.
 * @since Version 0.1.
 * @author Alonso del Arte
 */
public abstract class TaxpayerIdentificationNumber implements Serializable {
    
    private static final long serialVersionUID = 4549583342537813604L;
    
    protected static final DecimalFormatSymbols DASHES 
            = new DecimalFormatSymbols();
    
    static {
        DASHES.setGroupingSeparator('-');
    }
    
    final int idNum;
    
    /**
     * Provides an offset for the hash code function. The idea here is that a 
     * subclass will provide the same offset for all its instances, to separate 
     * its instances' hash codes from the hash codes for a different subclass. 
     * However, a more nuanced approach may be workable.
     * @return A number, preferably not 0, and hopefully not the same as other 
     * subclasses.
     */
    abstract int hashCodeOffset();
    
    /**
     * Obfuscates the identification number for the hash code. Generally 
     * subclasses should override. The idea here is that the hash code will not 
     * reveal the identification number.
     * @return A number related to the identification number, preferably through 
     * a one-way function. If not overridden, this simply gives the 
     * identification number plus {@link #hashCodeOffset()} (which, unlike this 
     * function, must be overridden by subclasses if they're not abstract). This 
     * may or may not provide sufficient obfuscation.
     */
    int hashCodeObscurant() {
        return this.hashCodeOffset() + this.idNum;
    }
    
    /**
     * Determines whether an object is equal to this one.
     * @param obj The object to compare. Examples: two 
     * <code>SocialSecurityNumber</code> instances, one for 000-00-1729 and one 
     * for 078-05-1120; an <code>EmployerIdentificationNumber</code> instance 
     * for 07-8051120; and the <code>Integer</code> 78051120.
     * @return True if <code>obj</code> is of the same runtime class as this 
     * object and they hold the exact same identification number, false in any 
     * other case. Suppose this is a <code>SocialSecurityNumber</code> instance 
     * for 078-05-1120. Compared to another <code>SocialSecurityNumber</code> 
     * instance that's also for 078-05-1120, this function will return true. 
     * Compared to the <code>SocialSecurityNumber</code> instance for 
     * 000-00-1729, this will return false, as the classes match but the 
     * identification numbers don't. And compared to the 
     * <code>EmployerIdentificationNumber</code> instance for 07-8051120, this 
     * function will return false because the identification numbers match but 
     * the classes don't. And of course also false for the <code>Integer</code> 
     * 78051120.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        return this.idNum == ((TaxpayerIdentificationNumber) obj).idNum;
    }
    
    /**
     * Gives a hash code for this taxpayer identification number. The easiest 
     * way to generate unique hash codes would be to simply use the 
     * identification numbers as hash codes. Theoretically anyone who can access 
     * the hash codes can also access the unredacted numbers. But, with the hope 
     * that this precaution is unnecessary, while at the same time making no 
     * guarantees on the suitability of this program for real people's 
     * personally identifiable information, this program uses the identification 
     * number to come up with a different number that is almost always 
     * different, and from which it would not be trivially easy to recover the 
     * identification number. This sacrifices some uniqueness, but hopefully not 
     * enough to impair the working of hash-based data structures.
     * @return A number that is likely to be different from the identification 
     * number. For example, the hash code for the 
     * <code>SocialSecurityNumber</code> instance for 478-00-5040 might be a 
     * number such as 504038850 rather than 478005040. If this function is not 
     * overridden, the formula is {@link #hashCodeObscurant()} plus {@link 
     * #hashCodeOffset()}.
     */
    @Override
    public int hashCode() {
        return this.hashCodeObscurant() + this.hashCodeOffset();
    }
    
    /**
     * Constructor. Provides only minimal validation.
     * @param number The identification number. The only constraint this 
     * abstract classes places on the identification number is that it must not 
     * be negative. Subclasses and probably should add additional constraints.
     * @throws IllegalArgumentException If <code>number</code> is negative. As a 
     * perhaps unnecessary data privacy precaution, the exception message will 
     * not include that or any other number.
     */
    public TaxpayerIdentificationNumber(int number) {
        if (number < 0) {
            String excMsg = "Negative TIN is not allowed";
            throw new IllegalArgumentException(excMsg);
        }
        this.idNum = number;
    }
    
}

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

import java.text.DecimalFormat;

/**
 * Represents a Social Security Number (SSN). Provides means to redact SSNs to 
 * their last four digits, and for parsing SSNs from <code>String</code> 
 * instances.
 * <p>For much of this documentation I use the SSN 752-98-1729 as an example. To 
 * my knowledge, this SSN has never been assigned to anyone born prior to 
 * 2011.</p>
 * @since Version 0.1.
 * @author Alonso del Arte
 */
public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

    private static final long serialVersionUID = 4549583346832780900L;
    
    public static final String AREA_NUMBER_FORMAT = "000";
    public static final String GROUP_NUMBER_FORMAT = "00";
    public static final String SERIAL_NUMBER_FORMAT = "0000";

    private static final DecimalFormat AREA_NUMBER_FORMATTER 
            = new DecimalFormat(AREA_NUMBER_FORMAT);
    private static final DecimalFormat GROUP_NUMBER_FORMATTER 
            = new DecimalFormat(GROUP_NUMBER_FORMAT);
    private static final DecimalFormat SERIAL_NUMBER_FORMATTER 
            = new DecimalFormat(SERIAL_NUMBER_FORMAT);

    private final int areaNumber;
    private final int groupNumber;
    private final int serialNumber;

    private final String ssnWDashes;
    private final String lastFour;
    
    @Override
    int hashCodeOffset() {
        return 16384;
    }

    @Override
    int hashCodeObscurant() {
        int hash = 239 * this.groupNumber;
        hash += 47 * this.areaNumber;
        hash += 100000 * this.serialNumber;
        return hash;
    }

    /**
     * Gives a textual representation of this Social Security Number (SSN) with 
     * dashes. If a textual representation with the first five digits redacted 
     * is needed, use {@link #toRedactedString()}.
     * @return The SSN with dashes. For example, for 752-98-1729, this would 
     * give "752-98-1729".
     */
    @Override
    public String toString() {
        return this.ssnWDashes;
    }

    /**
     * Redacts a Social Security Number (SSN) to only show the last four digits.
     * @return The SSN with the first five digits replaced by asterisks. For 
     * example, 752-98-1729 would be given as "***-**-1729".
     */
    public String toRedactedString() {
        return "***-**-" + this.lastFour;
    }
    
    /**
     * Gives the last four digits of this Social Security Number (SSN). Use 
     * {@link #toRedactedString()} if you need the SSN formatted with asterisks 
     * for the first five digits and zero-padding for the last four if needed.
     * @return The last four digits. Two examples: if this SSN is 752-98-1729, 
     * this function would return 1729; if this SSN is 753-25-0064, this 
     * function would return 64.
     */
    public int getLastFour() {
        return this.serialNumber;
    }
    
    /**
     * Determines whether the given integer matches the last four digits of this 
     * Social Security Number (SSN).
     * @param num The number to match. For example, 1729. Preferably a number 
     * between 0 and 9999, but greater numbers will also work. Not recommend for 
     * use with negative numbers (current behavior with negative numbers is not 
     * guaranteed for later versions).
     * @return True if the last four match, false otherwise. For example, if 
     * this SSN is 752-98-1729, then 1729 will match. But if this SSN is 
     * 753-25-0064, then 1729 will not match.
     */
    public boolean matchesLastFour(int num) {
        return this.serialNumber == (num % 10000);
    }
    
    /**
     * Determines whether two Social Security Numbers (SSNs) match in their last 
     * four digits. This should of course be the case if they're the exact same 
     * SSN.
     * @param other The SSN to compare. For example, 089-22-1729.
     * @return True if the last four digits match, false otherwise. For example, 
     * 752-98-1729 will match with 089-22-1729. 089-22-1730 will not.
     * @throws NullPointerException If <code>other</code> is null.
     */
    public boolean matchesLastFour(SocialSecurityNumber other) {
        return this.serialNumber == other.serialNumber;
    }
    
    static boolean correctSSNDashPlacement(String s) {
        return (s.indexOf('-') == 3) && (s.indexOf('-', 4) == 6) 
                && (s.indexOf('-', 7) == -1);
    }

    /**
     * Parses a Social Security Number (SSN) from text.
     * @param s The text to parse. For example, "752-98-1729".
     * @return A <code>SocialSecurityNumber</code> object. For example, 
     * 752-98-1729.
     * @throws IllegalArgumentException If <code>s</code> contains a validly 
     * formatted SSN with area number 773 or greater (e.g., 774-05-1729).
     * @throws NullPointerException If <code>s</code> is null.
     * @throws NumberFormatException If <code>s</code> does not contain a number 
     * with the proper dash placement. If <code>s</code> can be understood as an 
     * Employer Identification Number (EIN), the exception message will say so.
     */
    public static SocialSecurityNumber parseSSN(String s) {
        if (!correctSSNDashPlacement(s)) {
            String excMsg;
            if (EmployerIdentificationNumber.correctEINDashPlacement(s)) {
                excMsg = "Input \"" + s 
                        + "\" is an Employer Identification Number, not an SSN";
            } else {
                excMsg = "Input \"" + s + "\" is not a properly formatted SSN";
            }
            throw new NumberFormatException(excMsg);
        }
        String dashesOff = s.replace("-", "");
        int num = Integer.parseInt(dashesOff);
        return new SocialSecurityNumber(num);
    }

    /**
     * Constructor.
     * @param number The number. Should be at least 0 but no greater than 
     * 772999999 (which corresponds to 772-99-9999).
     * @throws IllegalArgumentException If <code>number</code> is negative or 
     * greater than 772999999.
     */
    public SocialSecurityNumber(int number) {
        super(number);
        if (number > 772999999) {
            String excMsg = "Area 773 or higher is not valid for SSN";
            throw new IllegalArgumentException(excMsg);
        }
        int intermediate = number / 10000;
        this.areaNumber = intermediate / 100;
        this.groupNumber = intermediate % 100;
        this.serialNumber = number % 10000;
        String areaNumStr = AREA_NUMBER_FORMATTER.format(this.areaNumber);
        String groupNumStr = GROUP_NUMBER_FORMATTER.format(this.groupNumber);
        this.lastFour = SERIAL_NUMBER_FORMATTER.format(this.serialNumber);
        this.ssnWDashes = areaNumStr + "-" + groupNumStr + "-" + this.lastFour;
    }

}

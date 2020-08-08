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
package entities.idnumbers;

import java.text.DecimalFormat;

/**
 * Represents a Social Security Number (SSN).
 * @author Alonso del Arte
 */
public class SocialSecurityNumber extends TaxpayerIdentificationNumber {

    private static final long serialVersionUID = -6723595580071797793L;
    
    public static final String AREA_NUMBER_FORMAT = "000";
    public static final String GROUP_NUMBER_FORMAT = "00";
    public static final String SERIAL_NUMBER_FORMAT = "0000";

    private static final DecimalFormat AREA_NUMBER_FORMATTER = new DecimalFormat(AREA_NUMBER_FORMAT);
    private static final DecimalFormat GROUP_NUMBER_FORMATTER = new DecimalFormat(GROUP_NUMBER_FORMAT);
    private static final DecimalFormat SERIAL_NUMBER_FORMATTER = new DecimalFormat(SERIAL_NUMBER_FORMAT);

    private final int areaNumber;
    private final int groupNumber;
    private final int serialNumber;

    private final String ssnWDashes;
    private final String lastFour;

    // TODO: Remove this equals() override once TIN equals() override is done
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
        return this.idNum == ((SocialSecurityNumber) obj).idNum;
    }

    @Override
    public int hashCode() {
        int hash = 239 * this.groupNumber;
        hash += 47 * this.areaNumber;
        hash += 1000000 * this.serialNumber;
        return hash;
    }

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
    
    // TODO: Write tests for this
    public int getLastFour() {
        return this.serialNumber;
    }
    
    public boolean matchesLastFour(int num) {
        return this.serialNumber == num;
    }
    
    public boolean matchesLastFour(SocialSecurityNumber other) {
        return this.serialNumber == other.serialNumber;
    }
    
    static boolean correctSSNDashPlacement(String s) {
        return (s.indexOf('-') == 3) && (s.indexOf('-', 4) == 6) 
                && (s.indexOf('-', 7) == -1);
    }

    public static SocialSecurityNumber parseSSN(String s) {
        if (!correctSSNDashPlacement(s)) {
            String excMsg;
            if (EmployerIdentificationNumber.correctEINDashPlacement(s)) {
                excMsg = "Input \"" + s + "\" is an Employer Identification Number (EIN), not SSN";
            } else {
                excMsg = "Input \"" + s + "\" is not a properly formatted SSN";
            }
            throw new NumberFormatException(excMsg);
        }
        String dashesOff = s.replace("-", "");
        int num = Integer.parseInt(dashesOff);
        return new SocialSecurityNumber(num);
    }

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

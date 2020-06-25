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
package entities.idnumbers;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * A class to represent taxpayer identification numbers. We take it on faith 
 * that the correct numbers are provided. There is no checking for uniqueness.
 * @author Alonso del Arte
 */
public abstract class TaxpayerIdentificationNumber implements Serializable {
    
    private static final long serialVersionUID = -5855511363530100806L;
    
    protected static final DecimalFormatSymbols DASHES = new DecimalFormatSymbols();
    
    static {
        DASHES.setGroupingSeparator('-');
    }
    
    private static final DecimalFormat FORMATTER = new DecimalFormat();
    
    protected final int idNum;
    
    public TaxpayerIdentificationNumber(int number) {
        if (number < 0) {
            String excMsg = "Negative taxpayer identification number not allowed";
            throw new IllegalArgumentException(excMsg);
        }
        this.idNum = number;
    }
    
}

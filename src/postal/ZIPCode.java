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
package postal;

import java.util.Locale;

/**
 * Holds a ZIP code for a mailing address in the United States.
 *
 * @author Alonso del Arte
 */
public class ZIPCode extends PostalCode {

    private final String numStr;

    @Override
    public String toString() {
        return this.numStr;
    }

    public ZIPCode(int zip5) {
        this(zip5, 0);
    }

    public ZIPCode(int zip5, int zip4) {
        super(zip5 * 10000 + zip4, Locale.US);
        if (zip5 < 0 || zip4 < 0 || zip5 > 99999 || zip4 > 9999) {
            String excMsg = "The numbers " + zip5 + ", " + zip4 
                    + " can't be used for a ZIP code";
            throw new IllegalArgumentException(excMsg);
        }
        String zip5Str = String.format("%05d", zip5);
        String zip4Str = String.format("%04d", zip4);
        if (zip4 == 0) {
            this.numStr = zip5Str;
        } else {
            this.numStr = zip5Str + "-" + zip4Str;
        }
    }

}

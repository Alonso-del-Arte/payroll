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

import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @author Alonso del Arte
 */
public abstract class PostalCode implements Serializable {
    
    private static final long serialVersionUID = 4549586271705512755L;
    
    protected final long postalCodeNumber;
    
    protected final Locale postalGov;
    
    public Locale getCountry() {
        return this.postalGov;
    }
    
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
        return this.postalCodeNumber == ((PostalCode) obj).postalCodeNumber;
    }
    
    @Override
    public int hashCode() {
        int hash = this.postalGov.hashCode() << 16;
        hash += this.postalCodeNumber;
        return hash;
    }
    
    public PostalCode(long number, Locale loc) {
        this.postalCodeNumber = number;
        this.postalGov = loc;
    }
    
}

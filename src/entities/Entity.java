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

import entities.idnumbers.SocialSecurityNumber;
import entities.idnumbers.TaxpayerIdentificationNumber;

import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract class to represent a legally recognized entity for the purposes 
 * of paying taxes or being exempt from paying taxes. The entity may be a 
 * person, a corporation, a non-profit organization, a government agency, etc.
 * @author Alonso del Arte
 */
public abstract class Entity implements Serializable {
    
    private static final long serialVersionUID = -8556912508988878973L;
    
    protected String fullName;
    
    protected final TaxpayerIdentificationNumber tinNumber;
    
    public String getFullName() {
        return this.fullName;
    }
    
    public TaxpayerIdentificationNumber getTIN() {
        return this.tinNumber;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.fullName);
        hash = 37 * hash + Objects.hashCode(this.tinNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        return Objects.equals(this.tinNumber, other.tinNumber);
    }
    
    protected Entity() {
        this.fullName = "For serialization purposes only";
        this.tinNumber = new SocialSecurityNumber(0);
    }
    
    public Entity(String name, TaxpayerIdentificationNumber tin) {
        this.fullName = name;
        this.tinNumber = tin;
    }
    
}

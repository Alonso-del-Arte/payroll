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
import postal.MailingAddress;

/**
 *
 * @author Alonso del Arte
 */
public class Person extends Entity {
    
    private static final long serialVersionUID = 8249435259963369371L;
    
    private String prefix;
    private String firstName;
    private char middleInitial;
    private String lastName;
    private String suffix;
    
    private MailingAddress addressHome = null;
    
    public String getPrefix() {
        return "Not implemented yet";
    }
    
    public void setPrefix(String prefix) {
        //
    }
    
    public String getFirstName() {
        return "Not implemented yet";
    }
    
    public void setFirstName(String name) {
        //
    }
    
    public char getMiddleInitial() {
        return 'x';
    }
    
    public void setMiddleInitial(char initial) {
        //
    }
    
    public String getLastName() {
        return "Not implemented yet";
    }
    
    public void setLastName(String name) {
        //
    }
    
    public String getSuffix() {
        return "Not implemented yet";
    }
    
    public void setSuffix(String suffix) {
        //
    }
    
    // TODO: Write test for this
    public MailingAddress getHomeAddress() {
        return null; // STUB TO FAIL THE FIRST TEST
    }
    
    public void setHomeAddress(MailingAddress address) {
        this.addressHome = address;
    }
    
    public Person(String name, SocialSecurityNumber ssn) {
        super(name, ssn);
    }
    
}

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
package postal;

/**
 * Holds a mailing address.
 * @author Alonso del Arte
 */
public class MailingAddress {
    
    // TODO: Add fields for street, city, state/province, etc.
    
    private final PostalCode postalCode;
    
    public MailingAddress(PostalCode postCode) {
        this.postalCode = postCode;
    }
    
}

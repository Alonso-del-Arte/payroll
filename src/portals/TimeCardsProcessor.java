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
package portals;

import payroll.TimeCard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author Alonso del Arte
 */
public class TimeCardsProcessor {

    // TODO: Move from portals.text the functionality pertaining to time cards 
    // that will be common to portals.text and portals.gui
    
    public static ArrayList<TimeCard> getCards() 
            throws ClassNotFoundException, IOException {
        if (!DataDirectoryInitializer.hasBeenSet()) {
            DataDirectoryInitializer.setDir();
        }
        File dir = DataDirectoryInitializer.getDir();
        TimeCardFilter filter = new TimeCardFilter();
        File[] matches = dir.listFiles(filter);
        ArrayList<TimeCard> list = new ArrayList<>();
        for (File file : matches) {
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            TimeCard card = (TimeCard) objStream.readObject();
            list.add(card);
        }
        return list;
    }
    
}

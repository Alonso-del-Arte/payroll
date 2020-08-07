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
package portals;

import java.io.File;
import java.io.IOException;

/**
 * Initializes a directory to store employee records and time cards in.
 * @author Alonso del Arte
 */
public class DataDirectoryInitializer {

    private static final String TEMP_DIR_PATH
            = System.getProperty("java.io.tmpdir");
    
    private static final String DEFAULT_DATA_DIR_PATH = TEMP_DIR_PATH 
            + File.separatorChar + "PersProg" + File.separatorChar;
    
    private static final File DEFAULT_DATA_DIR 
            = new File(DEFAULT_DATA_DIR_PATH);
    
    static {
        DEFAULT_DATA_DIR.mkdir();
    }
    
    private static File dataDir = DEFAULT_DATA_DIR;
    
    private static boolean setFlag = false;
    
    /**
     * Gets the directory where the employee records and time cards are kept. 
     * This should be the default temporary folder if it has not been 
     * specifically set to another folder.
     * @return The directory where the employee records and time cards are kept. 
     * On a Windows system, this might be something like C:\Temp\PersProg.
     */
    public static File getDir() {
        return dataDir;
    }
    
    /**
     * Sets the default temporary folder as the directory to store employee 
     * records and time cards.
     * @throws IOException If an input/output error occurs. This exception is 
     * unlikely to occur.
     */
    public static void setDir() throws IOException {
        setDir(DEFAULT_DATA_DIR);
    }

    /**
     * Sets the directory where employee records and time cards will be stored.
     * @param dir The directory to use. This must be a directory, not a file. If 
     * the directory does not already exist, this procedure will try to create 
     * it.
     * @throws IllegalArgumentException If <code>dir</code> is a file rather 
     * than a directory. For example, on a Windows system, setting 
     * <code>dir</code> to the user's Documents folder would work, but setting 
     * it to document.doc in the Documents folder would trigger this exception.
     * @throws IOException If the folder did not already exist and there was a 
     * problem trying to create it.
     */
    public static void setDir(File dir) throws IOException {
        if (!dir.isDirectory()) {
            String excMsg = dir.getPath() + " is not a directory";
            throw new IllegalArgumentException(excMsg);
        }
        if (!dir.exists()) {
            dir.createNewFile();
        }
        dataDir = dir;
        setFlag = true;
    }
    
    /**
     * Tells whether a directory for storing employee records and time cards has 
     * been set. Use <code>setDir()</code> to set the directory.
     * @return True if the directory has been set, false otherwise.
     */
    public static boolean hasBeenSet() {
        return setFlag;
    }
    
}

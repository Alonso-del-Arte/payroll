/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portals;

import java.io.File;
import java.io.IOException;

/**
 *
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
    
    public static File getDir() {
        return dataDir;
    }
    
    public static void setDir() throws IOException {
        setDir(DEFAULT_DATA_DIR);
    }

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
    
    public static boolean hasBeenSet() {
        return setFlag;
    }
    
}

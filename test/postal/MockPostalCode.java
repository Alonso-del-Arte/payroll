/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postal;

import java.util.Locale;

/**
 * A mock implementation of {@link PostalCode} for use in tests.
 * @deprecated Will be removed on the next commit. Recommend instead using 
 * either a local anonymous class or a nested static class.
 * @author Alonso del Arte
 */
@Deprecated
public class MockPostalCode extends PostalCode {

    public MockPostalCode(int i, Locale loc) {
        super(i, loc);
    }
    
}

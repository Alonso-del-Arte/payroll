/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.idnumbers;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the TaxpayerIdentificationNumber class.
 * @author Alonso del Arte
 */
public class TaxpayerIdentificationNumberTest {
    
    @Test
    public void testReferentialEquality() {
        TaxpayerIdentificationNumber someNumber 
                = new TaxpayerIdentificationNumberImpl(123456789);
        assertEquals(someNumber, someNumber);
    }
    
    public static class TaxpayerIdentificationNumberImpl 
            extends TaxpayerIdentificationNumber {
        
        @Override
        public String toString() {
            return "0-" + this.idNum;
        }
    
        public TaxpayerIdentificationNumberImpl(int num) {
            super(num);
        }
    
    }
    
}

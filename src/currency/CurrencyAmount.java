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
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple 
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package currency;

import java.io.Serializable;
import java.util.Currency;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Represents amounts of money in different currencies.
 * @author Alonso del Arte
 */
public class CurrencyAmount implements Comparable<CurrencyAmount>, 
        Serializable {
    
    private static final long serialVersionUID = 5023489862247404324L;
    
    private final long amountInCents;
    private final Currency currencyID;
    
    private static final Set<Currency> CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static Currency parsedCurrency;
    
    @Override
    public String toString() {
        final int centPlaces = this.currencyID.getDefaultFractionDigits();
        if (centPlaces == 0) {
            return this.currencyID.getSymbol() + this.amountInCents;
        }
        String numStr = Long.toString(Math.abs(this.amountInCents));
        while (numStr.length() <= centPlaces) {
            numStr = "0" + numStr;
        }
        if (this.amountInCents < 0) {
            numStr = "-" + numStr;
        }
        int decPointPlace = numStr.length() - centPlaces;
        return this.currencyID.getSymbol() + numStr.substring(0, decPointPlace) 
                + "." + numStr.substring(decPointPlace);
    }

    @Override
    public int hashCode() {
//        return 0;
        int hash = 5;
        hash = 37 * hash + (int) (this.amountInCents 
                ^ (this.amountInCents >>> 32));
        hash = 37 * hash + Objects.hashCode(this.currencyID);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CurrencyAmount)) {
            return false;
        }
        final CurrencyAmount other = (CurrencyAmount) obj;
        if (this.amountInCents != other.amountInCents) {
            return false;
        }
        return this.currencyID.equals(other.currencyID);
    }

    public long getAmountInCents() {
        return this.amountInCents;
    }
    
    public Currency getCurrency() {
        return this.currencyID;
    }
    
    /**
     * Adds a currency amount to this currency amount. For example, $2,058.43.
     * @param addend The currencyID amount to add. For example, $89.53.
     * @return A new <code>CurrencyAmount</code> object with the result. For 
     * example, $2,147.96. There is no overflow checking.
     * @throws CurrencyConversionNeededException If this currencyID amount and the 
 addend are of different currencies, this exception will be thrown. For 
 example, if this amount is $499.89 and the addend is &euro;73.20.
     */
    public CurrencyAmount plus(CurrencyAmount addend) {
        if (this.currencyID != addend.currencyID) {
            throw new CurrencyConversionNeededException("Convert before adding", 
                    this, addend);
        }
        long sum = Math.addExact(this.amountInCents, addend.amountInCents);
        return new CurrencyAmount(sum, this.currencyID);
    }
    
    public CurrencyAmount negate() {
        return new CurrencyAmount(-this.amountInCents, this.currencyID);
    }
    
    public CurrencyAmount minus(CurrencyAmount subtrahend) {
        return this.plus(subtrahend.negate());
    }
    
    public CurrencyAmount times(int multiplier) {
        return new CurrencyAmount(multiplier * this.amountInCents, this.currencyID);
    }
    
    public CurrencyAmount times(double multiplier) {
        if (Double.isFinite(multiplier)) {
            double product = multiplier * this.amountInCents;
            return new CurrencyAmount(Math.round(product), this.currencyID);
        } else {
            String excMsg = "The number " + multiplier + " is not finite";
            throw new ArithmeticException(excMsg);
        }
    }
    
    public CurrencyAmount divides(int divisor) {
        return new CurrencyAmount(this.amountInCents / divisor, this.currencyID);
    }
    
    @Override
    public int compareTo(CurrencyAmount other) {
        CurrencyAmount diff = this.minus(other);
        return Long.signum(diff.amountInCents);
    }
    
    private static String removeSymbol(String s) {
        boolean notFound = true;
        Iterator<Currency> iter = CURRENCIES.iterator();
        Currency currency;
        String symbol;
        while (notFound && iter.hasNext()) {
            currency = iter.next();
            symbol = currency.getSymbol();
            if (s.startsWith(symbol)) {
                s = s.substring(symbol.length());
                parsedCurrency = currency;
            }
        }
        return s;
    }
    
    private static int centSeparation() {
        switch (parsedCurrency.getDefaultFractionDigits()) {
            case 0:
                return 1;
            case 1:
                return 10;
            case 2:
                return 100;
            case 3:
                return 1000;
            default:
                String excMsg = "Default fraction digits of " 
                        + parsedCurrency.getDisplayName() + " not recognized";
                throw new RuntimeException(excMsg);
        }
    }
    
    public static CurrencyAmount parseAmount(String s) {
        s = removeSymbol(s);
        int units;
        int cents = 0;
        int decPointIndex = s.indexOf('.');
        if (decPointIndex > -1) {
            cents = Integer.parseInt(s.substring(decPointIndex + 1));
            units = Integer.parseInt(s.substring(0, decPointIndex));
        } else {
            units = Integer.parseInt(s.substring(0));
        }
        units *= centSeparation();
        return new CurrencyAmount(units + cents, parsedCurrency);
    }
    
    public CurrencyAmount(long cents, Currency currency) {
        if (currency.getDefaultFractionDigits() == -1) {
            String excMsg = "Can't use currency " + currency.toString() 
                    + " with default fraction digits -1";
            throw new IllegalArgumentException(excMsg);
        }
        this.amountInCents = cents;
        this.currencyID = currency;
    }
    
}

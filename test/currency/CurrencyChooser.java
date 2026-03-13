/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Alonso del Arte
 */
public class CurrencyChooser {
    
    // TODO: Write tests for this
    public static Set<Currency> getSuitableCurrencies() {
        return new HashSet<>();
    }

    // TODO: Write tests for this
    public static boolean isSuitableCurrency(Currency currency) {
        return false;
    }
    
    // TODO: Write tests for this
    public static boolean isHistoricalCurrency(Currency currency) {
        return false;
    }

    // TODO: Write tests for this
    public static boolean isEuroReplacedCurrency(Currency currency) {
        return false;
    }

    // TODO: Write tests for this
    public static boolean isPseudocurrency(Currency currency) {
        return false;
    }
    
    // TODO: Write tests for this
    public static Currency choosePseudocurrency() {
        return Currency.getInstance("EUR");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency() {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency(int fractionDigits) {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency(Predicate<Currency> predicate) {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrency(Set<Currency> set) {
        return Currency.getInstance("XTS");
    }

    // TODO: Write tests for this
    public static Currency chooseCurrencyOtherThan(Currency currency) {
        return currency;
    }

    // TODO: Write tests for this
    public static Currency chooseCurrencyOtherThan(Currency currency, 
            Set<Currency> set) {
        return currency;
    }

}

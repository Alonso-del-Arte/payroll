/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of the CurrencyChooser class.
 * @author Alonso del Arte
 */
public class CurrencyChooserTest {
        
    private static final Set<Currency> CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();
    
    private static final Map<Integer, Set<Currency>> FRACT_DIGITS_MAP 
            = new HashMap<>();
    
    private static final Currency DOLLARS = Currency.getInstance(Locale.US);
    
    private static final int TOTAL_NUMBER_OF_CURRENCIES;
    
    private static final int NUMBER_OF_CALLS_MULTIPLIER_FOR_EXCLUSION_SEARCH 
            = 4;
    
    private static final int NUMBER_OF_CALLS_FOR_EXCLUSION_SEARCH 
            = NUMBER_OF_CALLS_MULTIPLIER_FOR_EXCLUSION_SEARCH 
            * CURRENCIES.size();
    
    private static final String[] EURO_REPLACED_EXCLUSION_CODES = {"ADP", "ATS", 
        "BEF", "BGN", "CYP", "DEM", "EEK", "ESP", "FIM", "FRF", "GRD", "IEP", 
        "ITL", "LUF", "MTL", "NLG", "PTE", "SIT"};
    
    private static final String[] OTHER_EXCLUSION_CODES = {"AYM", "BGL", "BOV", 
        "CHE", "CHW", "COU", "GWP", "MGF", "MXV", "SRG", "STN", "TPE", "USN", 
        "USS", "UYI", "VED", "ZWN"};
    
    static {
        for (Currency currency : CURRENCIES) {
            int fractDigits = currency.getDefaultFractionDigits();
            if (fractDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                Set<Currency> digitGroupedSet;
                if (FRACT_DIGITS_MAP.containsKey(fractDigits)) {
                    digitGroupedSet = FRACT_DIGITS_MAP.get(fractDigits);
                } else {
                    digitGroupedSet = new HashSet<>();
                    FRACT_DIGITS_MAP.put(fractDigits, digitGroupedSet);
                }
                digitGroupedSet.add(currency);
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
        TOTAL_NUMBER_OF_CURRENCIES = CURRENCIES.size();
    }
    
    private static boolean hasYearSpanIndicated(Currency currency) {
        String displayName = currency.getDisplayName();
        return displayName.contains("\u002818") 
                || displayName.contains("\u002819") 
                || displayName.contains("\u002820");
    }
    
    private static boolean isEuroReplacedCurrency(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(EURO_REPLACED_EXCLUSION_CODES, key) > -1;
    }

    private static boolean isHistoricalCurrency(Currency currency) {
        return hasYearSpanIndicated(currency) 
                || isEuroReplacedCurrency(currency);
    }
    
    private static boolean isPseudoCurrency(Currency currency) {
        return currency.getDefaultFractionDigits() < 0;
    } 
    
    private static boolean shouldOtherwiseBeExcluded(Currency currency) {
        String key = currency.getCurrencyCode();
        return Arrays.binarySearch(OTHER_EXCLUSION_CODES, key) > -1;
    } 
    
    private static boolean accept(Currency currency) {
        return !isHistoricalCurrency(currency) && !isPseudoCurrency(currency) 
                && !shouldOtherwiseBeExcluded(currency);
    } 
    
    @Test
    public void testGetSuitableCurrencies() {
        System.out.println("getSuitableCurrencies");
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        Set<Currency> expected = currencies.stream()
                .filter(currency -> accept(currency))
                .collect(Collectors.toSet());
        Set<Currency> actual = CurrencyChooser.getSuitableCurrencies();
        assertEquals(expected, actual);
    }
    
}

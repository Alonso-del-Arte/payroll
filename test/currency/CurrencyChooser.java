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
    
    static final Random RANDOM = new Random();
    
    private static final Set<Currency> ALL_CURRENCIES 
            = Currency.getAvailableCurrencies();
    
    private static final int MAX_NUMBER_OF_PREDICATE_MATCH_ATTEMPTS 
            = 3 * ALL_CURRENCIES.size();
    
    private static final List<Currency> CURRENCIES 
            = new ArrayList<>(ALL_CURRENCIES);
    
    private static final Set<Currency> PSEUDO_CURRENCIES = new HashSet<>();
    
    private static final List<Currency> PSEUDO_CURRENCIES_LIST;

    private static final String[] EURO_REPLACED_EXCLUSION_CODES = {"ADP", "ATS", 
        "BEF", "BGN", "CYP", "DEM", "EEK", "ESP", "FIM", "FRF", "GRD", "IEP", 
        "ITL", "LUF", "MTL", "NLG", "PTE", "SIT"};
    
    private static final Set<Currency> EURO_REPLACED_CURRENCIES 
            = new HashSet<>();
    
    static {
        for (String currencyCode : EURO_REPLACED_EXCLUSION_CODES) {
            EURO_REPLACED_CURRENCIES.add(Currency.getInstance(currencyCode));
        }
    }
        
    private static final Set<Currency> HISTORICAL_CURRENCIES = new HashSet<>();

    private static final String[] OTHER_EXCLUSION_CODES = {"AYM", "BGL", "BOV", 
        "CHE", "CHW", "COU", "GWP", "MGF", "MXV", "SRG", "STN", "TPE", 
        "USN", "USS", "UYI", "ZWN"};

    private static final Set<Currency> OTHER_EXCLUSIONS = new HashSet<>();
    
    static {
        for (String currencyCode : OTHER_EXCLUSION_CODES) {
            OTHER_EXCLUSIONS.add(Currency.getInstance(currencyCode));
        }
    }
    
    private static final Map<Integer, Set<Currency>> CURRENCIES_DIGITS_MAP 
            = new HashMap<>();
    
    static {
        CURRENCIES.removeAll(EURO_REPLACED_CURRENCIES);
        CURRENCIES.removeAll(OTHER_EXCLUSIONS);
        final String nineteenthCenturyYearIndicator = "\u002818";
        final String twentiethCenturyYearIndicator = "\u002819";
        final String twentyFirstCenturyYearIndicator = "\u002820";
        for (Currency currency : CURRENCIES) {
            int fractionDigits = currency.getDefaultFractionDigits(); 
            if (fractionDigits < 0) {
                PSEUDO_CURRENCIES.add(currency);
            } else {
                String dispName = currency.getDisplayName();
                if (dispName.contains(nineteenthCenturyYearIndicator)
                        || dispName.contains(twentiethCenturyYearIndicator) 
                        || dispName.contains(twentyFirstCenturyYearIndicator)) 
                {
                    HISTORICAL_CURRENCIES.add(currency);
                } else {
                    Set<Currency> digitGroupedSet;
                    if (CURRENCIES_DIGITS_MAP.containsKey(fractionDigits)) {
                        digitGroupedSet = CURRENCIES_DIGITS_MAP
                                .get(fractionDigits);
                    } else {
                        digitGroupedSet = new HashSet<>();
                        CURRENCIES_DIGITS_MAP.put(fractionDigits, 
                                digitGroupedSet);
                    }
                    digitGroupedSet.add(currency);
                }
            }
        }
        for (String exclusionCode : OTHER_EXCLUSION_CODES) {
            try {
                Currency currency = Currency.getInstance(exclusionCode);
                OTHER_EXCLUSIONS.add(currency);
            } catch (IllegalArgumentException iae) {
                System.err.println("\"" + iae.getMessage() + "\"");
            }
        }
        CURRENCIES.removeAll(PSEUDO_CURRENCIES);
        CURRENCIES.removeAll(HISTORICAL_CURRENCIES);
        HISTORICAL_CURRENCIES.addAll(EURO_REPLACED_CURRENCIES);
        CURRENCIES.removeAll(OTHER_EXCLUSIONS);
        PSEUDO_CURRENCIES_LIST = new ArrayList<>(PSEUDO_CURRENCIES);
    }
    
    // TODO: Write tests for this
    public static Set<Currency> getSuitableCurrencies() {
        return new HashSet<>(CURRENCIES);
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

package cz.fi.muni.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CurrencyConvertorImplTest {

    @Test
    public void testConvert() {
        // Don't forget to test border values and proper rounding.
               
        CurrencyConvertor cc = new CurrencyConvertorImpl(mock(ExchangeRateTable.class));
        
        when(cc.convert(Currency.getInstance("USD"), Currency.getInstance("EUR"),
                new BigDecimal("100"))).thenReturn(new BigDecimal("89.21"));
        
        when(cc.convert(Currency.getInstance("USD"), Currency.getInstance("EUR"),
                new BigDecimal("0"))).thenReturn(new BigDecimal("0"));
        
        when(cc.convert(Currency.getInstance("USD"), Currency.getInstance("EUR"),
                new BigDecimal("81.5"))).thenReturn(new BigDecimal("72.71"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        CurrencyConvertor cc = new CurrencyConvertorImpl(mock(ExchangeRateTable.class));
        
        cc.convert(null, Currency.getInstance("EUR"),
                new BigDecimal("100"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        CurrencyConvertor cc = new CurrencyConvertorImpl(mock(ExchangeRateTable.class));
        
        cc.convert(Currency.getInstance("EUR"), null,
                new BigDecimal("100"));
    }

    @Test (expected= UnknownExchangeRateException.class)
    public void testConvertWithNullSourceAmount() {
        CurrencyConvertor cc = new CurrencyConvertorImpl(mock(ExchangeRateTable.class));
        
        cc.convert(Currency.getInstance("USD"), Currency.getInstance("EUR"),
                null);
    }

    @Test (expected= UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() {
        CurrencyConvertor cc = new CurrencyConvertorImpl(mock(ExchangeRateTable.class));
        
        cc.convert(Currency.getInstance("YOLO"), Currency.getInstance("TROLLO"),
                new BigDecimal("100"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConvertWithExternalServiceFailure() {
        CurrencyConvertor cc = new CurrencyConvertorImpl(mock(ExchangeRateTable.class));
        
        cc.convert(null,null,null);
    }
}

package cz.fi.muni.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {
    private static Currency USD = Currency.getInstance("USD");
    private static Currency EUR = Currency.getInstance("EUR");
    
    
    @Mock
    private ExchangeRateTable xTable;
    
    private CurrencyConvertor cc;
    
    @Before
    public void init() {
        cc = new CurrencyConvertorImpl(xTable);
    }
    
    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(xTable.getExchangeRate(USD, EUR)).thenReturn(new BigDecimal("0.89"));
        
        
        assertEquals(new BigDecimal("89.00"), cc.convert(USD, EUR, new BigDecimal("100.00")));
        assertEquals(new BigDecimal("0.00"), cc.convert(USD, EUR, new BigDecimal("0.00")));
        assertEquals(new BigDecimal("72.54"), cc.convert(USD, EUR, new BigDecimal("81.5")));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        cc.convert(null, EUR,
                new BigDecimal("100"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {        
        cc.convert(EUR, null,
                new BigDecimal("100"));
    }

    @Test (expected= IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {        
        cc.convert(USD, EUR, null);
    }

    @Test (expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(xTable.getExchangeRate(USD, EUR)).thenReturn(null);
        cc.convert(USD, EUR, new BigDecimal("100"));
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {  
        doThrow(ExternalServiceFailureException.class).when(xTable).getExchangeRate(USD, EUR);
        assertThatThrownBy(() -> cc.convert(USD, EUR, BigDecimal.ONE)).isInstanceOf(UnknownExchangeRateException.class);
    }
}


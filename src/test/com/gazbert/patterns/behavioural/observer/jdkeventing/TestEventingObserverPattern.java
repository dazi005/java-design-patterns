package com.gazbert.patterns.behavioural.observer.jdkeventing;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.gazbert.patterns.behavioural.observer.jdkeventing.DollarEuroMarket;
import com.gazbert.patterns.behavioural.observer.jdkeventing.DollarStirlingMarket;
import com.gazbert.patterns.behavioural.observer.jdkeventing.MarketType;
import com.gazbert.patterns.behavioural.observer.jdkeventing.bots.BoATradingBot;
import com.gazbert.patterns.behavioural.observer.jdkeventing.bots.GoldmanTradingBot;
import com.gazbert.patterns.behavioural.observer.jdkeventing.bots.HsbcTradingBot;


/**
 * Demonstrates use of Observer pattern using java.util.EventObject.
 * <p>
 * Use when you cannot use java.util.Observerable because your Observable/Subject needs to extend another class.
 * <p>
 * @author gazbert
 *
 */
public class TestEventingObserverPattern 
{    
    /**
     * Tests USD GBP market observer.
     */
    @Test
    public void testUsdGbpMarketObserver() {
    	
    	// Different banks create their bots, and register with exchange for latest market bid price
        final GoldmanTradingBot goldman = new GoldmanTradingBot();
       	DollarStirlingMarket.getInstance().registerPriceOberver(goldman);
        
        final HsbcTradingBot hsbc = new HsbcTradingBot();
       	DollarStirlingMarket.getInstance().registerPriceOberver(hsbc);        
       	
        final BoATradingBot boa = new BoATradingBot();
        // BoA bot is taking the day off... ;-)
       	//DollarStirlingMarketTradePrice.getInstance().registerPriceOberver(boa);                       

        // Assert current price from previous day's close
        final BigDecimal yesterdaysBidPrice = new BigDecimal(0.60);
        assertTrue(yesterdaysBidPrice.compareTo(goldman.getLatestDollarStirlingMarketBidPrice()) == 0);
        assertTrue(yesterdaysBidPrice.compareTo(hsbc.getLatestDollarStirlingMarketBidPrice()) == 0);
        assertTrue(yesterdaysBidPrice.compareTo(boa.getLatestDollarStirlingMarketBidPrice()) == 0);
        
        // A trade occurs on the exchange, so it notifies any observers of latest market bid price...
        final BigDecimal newBidPrice = new BigDecimal(0.61);
        
        // In real life, this would come from a proper buy order - I'm just using a bid price to keep it simple!
    	DollarStirlingMarket.getInstance().createNewBuyOrder(newBidPrice, MarketType.USD_GBP);
        
        // Did they get price update? 
        assertTrue(newBidPrice.compareTo(goldman.getLatestDollarStirlingMarketBidPrice()) == 0);
        assertTrue(newBidPrice.compareTo(hsbc.getLatestDollarStirlingMarketBidPrice()) == 0);
        
        // No change from yesterday
        assertTrue(yesterdaysBidPrice.compareTo(boa.getLatestDollarStirlingMarketBidPrice()) == 0);
        
        // Markets close for the day; bots head out for some much needed Krug...
    	DollarStirlingMarket.getInstance().unregisterPriceOberver(goldman);    	
    	DollarStirlingMarket.getInstance().unregisterPriceOberver(hsbc);    	
    }		
    
    /**
     * Tests USD EUR market observer.
     */
    @Test
    public void testUsdEurMarketObserver() {
    	
    	// Different banks create their bots, and register with exchange for latest market bid price
        final GoldmanTradingBot goldman = new GoldmanTradingBot();
       	DollarEuroMarket.getInstance().registerPriceOberver(goldman);
        
        final HsbcTradingBot hsbc = new HsbcTradingBot();
        DollarEuroMarket.getInstance().registerPriceOberver(hsbc);        
       	
        final BoATradingBot boa = new BoATradingBot();
        // BoA bot is taking the day off... ;-)
       	//DollarStirlingMarketTradePrice.getInstance().registerPriceOberver(boa);                       

        // Assert current price from previous day's close
        final BigDecimal yesterdaysBidPrice = new BigDecimal(0.73);
        assertTrue(yesterdaysBidPrice.compareTo(goldman.getLatestDollarEuroMarketBidPrice()) == 0);
        assertTrue(yesterdaysBidPrice.compareTo(hsbc.getLatestDollarEuroMarketBidPrice()) == 0);
        assertTrue(yesterdaysBidPrice.compareTo(boa.getLatestDollarEuroMarketBidPrice()) == 0);
        
        // A trade occurs on the exchange, so it notifies any observers of latest market bid price...
        final BigDecimal newBidPrice = new BigDecimal(0.74);
        
        // In real life, this would come from a proper buy order - I'm just using a bid price to keep it simple!
        DollarEuroMarket.getInstance().createNewBuyOrder(newBidPrice, MarketType.USD_EUR);
        
        // Did they get price update? 
        assertTrue(newBidPrice.compareTo(goldman.getLatestDollarEuroMarketBidPrice()) == 0);
        assertTrue(newBidPrice.compareTo(hsbc.getLatestDollarEuroMarketBidPrice()) == 0);
        
        // No change from yesterday
        assertTrue(yesterdaysBidPrice.compareTo(boa.getLatestDollarEuroMarketBidPrice()) == 0);
        
        // Markets close for the day; bots head out for some much needed Krug...
        DollarEuroMarket.getInstance().unregisterPriceOberver(goldman);    	
        DollarEuroMarket.getInstance().unregisterPriceOberver(hsbc);    	
    }	
}

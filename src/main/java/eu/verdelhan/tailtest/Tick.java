package eu.verdelhan.tailtest;

import java.math.BigDecimal;
import org.joda.time.DateTime;

/**
 * End tick of a period.
 */
public interface Tick {
    DateTime getDate();

    String getDateName();

    String getSimpleDateName();

    BigDecimal getClosePrice();

    BigDecimal getOpenPrice();

    int getTrades();

    BigDecimal getMaxPrice();

    BigDecimal getAmount();

    BigDecimal getVolume();

    BigDecimal getVariation();

    BigDecimal getMinPrice();

    BigDecimal getPreviousPrice();
}

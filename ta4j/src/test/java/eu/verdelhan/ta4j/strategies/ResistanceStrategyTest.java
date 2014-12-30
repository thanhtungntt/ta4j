/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Marc de Verdelhan & respective authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.verdelhan.ta4j.strategies;

import eu.verdelhan.ta4j.Operation;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.mocks.MockDecimalIndicator;
import eu.verdelhan.ta4j.mocks.MockStrategy;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ResistanceStrategyTest {

    private MockDecimalIndicator indicator;

    @Before
    public void setUp() {
        indicator = new MockDecimalIndicator(95d, 96d, 95d, 94d, 97d, 95d, 110d);
    }

    @Test
    public void resistanceShouldSell() {
        Operation[] enter = new Operation[] { null, null, null, null, null, null, null };

        Strategy neverSell = new MockStrategy(enter, enter);

        Trade trade = new Trade();

        Strategy resistance = new ResistanceStrategy(indicator, neverSell, 96);

        trade.operate(0);
        assertTrue(resistance.shouldOperate(trade, 1));

        trade = new Trade();
        trade.operate(2);

        assertFalse(resistance.shouldEnter(2));
        assertFalse(resistance.shouldOperate(trade, 2));
        assertFalse(resistance.shouldOperate(trade, 3));
        assertTrue(resistance.shouldOperate(trade, 4));

        trade = new Trade();
        trade.operate(5);

        assertFalse(resistance.shouldOperate(trade, 5));
        assertTrue(resistance.shouldOperate(trade, 6));
    }
}

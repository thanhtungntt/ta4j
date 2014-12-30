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
package eu.verdelhan.ta4j.analysis.criteria;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.Trade;
import eu.verdelhan.ta4j.analysis.CashFlow;
import java.util.ArrayList;
import java.util.List;

/**
 * Maximum drawdown criterion.
 * <p>
 * @see <a href="http://en.wikipedia.org/wiki/Drawdown_%28economics%29">http://en.wikipedia.org/wiki/Drawdown_%28economics%29</a>
 */
public class MaximumDrawdownCriterion extends AbstractAnalysisCriterion {

    @Override
    public double calculate(TimeSeries series, List<Trade> trades) {
        TADecimal maximumDrawdown = TADecimal.ZERO;
        TADecimal maxPeak = TADecimal.ZERO;
        CashFlow cashFlow = new CashFlow(series, trades);

        for (int i = series.getBegin(); i <= series.getEnd(); i++) {
            TADecimal value = cashFlow.getValue(i);
            if (value.isGreaterThan(maxPeak)) {
                maxPeak = value;
            }

            TADecimal drawdown = maxPeak.minus(value).dividedBy(maxPeak);
            if (drawdown.isGreaterThan(maximumDrawdown)) {
                maximumDrawdown = drawdown;
                // absolute maximumDrawdown.
                // should it be maximumDrawdown = drawDown/maxPeak ?
            }
        }
        return maximumDrawdown.toDouble();
    }

    @Override
    public double calculate(TimeSeries series, Trade trade) {
        List<Trade> trades = new ArrayList<Trade>();
        trades.add(trade);
        return calculate(series, trades);
    }

    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1 < criterionValue2;
    }
}

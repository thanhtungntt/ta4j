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
package eu.verdelhan.ta4j.indicators.helpers;

import eu.verdelhan.ta4j.TADecimal;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.CachedIndicator;

/**
 * Directional movement down indicator.
 * <p>
 */
public class DirectionalMovementDownIndicator extends CachedIndicator<TADecimal>{

    private TimeSeries series;

    public DirectionalMovementDownIndicator(TimeSeries series) {
        super(series);
        this.series = series;
    }

    @Override
    protected TADecimal calculate(int index) {
        if (index == 0) {
            return TADecimal.ZERO;
        }
        TADecimal prevMaxPrice = series.getTick(index - 1).getMaxPrice();
        TADecimal maxPrice = series.getTick(index).getMaxPrice();
        TADecimal prevMinPrice = series.getTick(index - 1).getMinPrice();
        TADecimal minPrice = series.getTick(index).getMinPrice();
        
        if ((prevMaxPrice.isGreaterThanOrEqual(maxPrice) && prevMinPrice.isLessThanOrEqual(minPrice))
                || maxPrice.minus(prevMaxPrice).isGreaterThanOrEqual(prevMinPrice.minus(minPrice))) {
            return TADecimal.ZERO;
        }
        return prevMinPrice.minus(minPrice);
    }
}

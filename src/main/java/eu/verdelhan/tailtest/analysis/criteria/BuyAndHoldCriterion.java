package eu.verdelhan.tailtest.analysis.criteria;

import eu.verdelhan.tailtest.AnalysisCriterion;
import eu.verdelhan.tailtest.OperationType;
import eu.verdelhan.tailtest.TimeSeries;
import eu.verdelhan.tailtest.Trade;
import eu.verdelhan.tailtest.analysis.evaluator.Decision;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BuyAndHoldCriterion implements AnalysisCriterion {

    @Override
    public double calculate(TimeSeries series, List<Trade> trades) {
        return series.getTick(series.getEnd()).getClosePrice().divide(series.getTick(series.getBegin()).getClosePrice(), RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public double summarize(TimeSeries series, List<Decision> decisions) {

        return calculate(series, new ArrayList<Trade>());
    }

    @Override
    public double calculate(TimeSeries series, Trade trade) {
		int entryIndex = trade.getEntry().getIndex();
		int exitIndex = trade.getExit().getIndex();

        if (trade.getEntry().getType() == OperationType.BUY) {
            return series.getTick(exitIndex).getClosePrice().divide(series.getTick(entryIndex).getClosePrice(), RoundingMode.HALF_UP).doubleValue();
        } else {
            return series.getTick(entryIndex).getClosePrice().divide(series.getTick(exitIndex).getClosePrice(), RoundingMode.HALF_UP).doubleValue();
        }
    }

    @Override
    public String getName() {
        return "Buy and Hold";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (this.getClass().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}

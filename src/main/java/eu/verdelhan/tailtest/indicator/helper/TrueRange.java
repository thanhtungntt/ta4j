package eu.verdelhan.tailtest.indicator.helper;

import eu.verdelhan.tailtest.Indicator;
import eu.verdelhan.tailtest.TimeSeries;

public class TrueRange implements Indicator<Double>{

	private TimeSeries series;

	public TrueRange(TimeSeries series) {
		this.series = series;
	}
	
	@Override
	public Double getValue(int index) {
		double ts = series.getTick(index).getMaxPrice().subtract(series.getTick(index).getMinPrice()).doubleValue();
		double ys = index == 0? 0 : series.getTick(index).getMaxPrice().subtract(series.getTick(index - 1).getClosePrice()).doubleValue();
		double yst = index == 0? 0 : series.getTick(index - 1).getClosePrice().subtract(series.getTick(index).getMinPrice()).doubleValue();
		double max = Math.max(Math.abs(ts), Math.abs(ys));
		
		return Math.max(max, Math.abs(yst));
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}
}

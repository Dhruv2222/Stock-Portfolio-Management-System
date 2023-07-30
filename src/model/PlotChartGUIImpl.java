package model;

import java.util.ArrayList;
import java.util.List;

class PlotChartGUIImpl extends PlotChartBarImpl {

  private double increment;

  @Override
  public List<Integer> getStarCount(List<Double> portfolioValues) {
    List<Integer> starCount = new ArrayList<>();
    double minValue = Double.MAX_VALUE;
    double maxValue = 0;
    double increment;
    //calculating the min and max value in the portfolioValues.
    for (double value : portfolioValues) {
      if (value != 0 && value < minValue) {
        minValue = value;
      }
      if (value > maxValue) {
        maxValue = value;
      }
    }

    //if there is only 1 non-zero value in the list.
    if (minValue == maxValue) {
      increment = maxValue;
    } else {
      increment = (maxValue - minValue) / 49.0;
    }


    starCount.add((int) minValue);
    this.increment = increment;
    for (double value : portfolioValues) {
      if (value == 0) {
        starCount.add(0);
        continue;
      }

      starCount.add((int) ((value - minValue) / increment) + 1);
    }

    return starCount;
  }

  public double getIncrement() {
    return increment;
  }
}

package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the PlotChart interface and implements all the methods which are required
 * to plot the bar chart for analyzing the performance of a portfolio over a period of time.
 */
class PlotChartBarImpl implements PlotChart {

  private double increment;

  @Override
  public List<String> getTimestamp(String fromDate, String toDate) {

    List<String> timestamp = new ArrayList<>();
    LocalDate fromDateLocalDate = LocalDate.parse(fromDate);
    LocalDate toDateLocalDate = LocalDate.parse(toDate);
    long intervalDays = ChronoUnit.DAYS.between(fromDateLocalDate, toDateLocalDate);
    //error if the interval is less than 5 days.
    if (intervalDays < 5) {
      throw new IllegalArgumentException("Interval entered is too small.");
    }

    //return list of days if interval is less than 30.
    if (intervalDays <= 30) {
      //a flag indicating the type of timestamps ahead.
      timestamp.add("day");
      for (int i = 0; i < intervalDays + 1; i++) {
        timestamp.add(fromDateLocalDate.toString());
        fromDateLocalDate = fromDateLocalDate.plusDays(1);
      }

    }

    //return list of days with interval of 6 days.
    if (intervalDays > 30 && intervalDays <= (31 * 5)) {
      //a flag indicating the type of timestamps ahead.
      timestamp.add("day");
      for (int i = 0; i < intervalDays; i++) {
        timestamp.add(fromDateLocalDate.toString());
        fromDateLocalDate = fromDateLocalDate.plusDays(6);
        if (fromDateLocalDate.isAfter(toDateLocalDate)) {
          break;
        }
      }
    }

    //return list of months if interval is greater than 5 months and less than 30 months.
    if (intervalDays > (31 * 5) && intervalDays <= (31 * 30)) {
      //a flag indicating the type of timestamps ahead.
      timestamp.add("month");
      for (int i = 0; i < intervalDays / 30; i++) {
        timestamp.add(fromDateLocalDate.getMonth().toString() + " " + fromDateLocalDate.getYear());
        fromDateLocalDate = fromDateLocalDate.plusMonths(1);
      }
    }


    //return list of years if interval is greater than 30 months (2.5 years) and less than 5 years.
    if (intervalDays > (30 * 31) && intervalDays <= (60 * 31)) {
      //a flag indicating the type of timestamps ahead.
      timestamp.add("month");
      for (int i = 0; i < intervalDays / 30; i++) {
        timestamp.add(fromDateLocalDate.getMonth().toString() + " " + fromDateLocalDate.getYear());
        fromDateLocalDate = fromDateLocalDate.plusMonths(2);
        if (fromDateLocalDate.isAfter(toDateLocalDate)) {
          break;
        }
      }
    }

    //return list of years if interval is greater than 60 months (5 years) and less than 30 years.
    if (intervalDays > (60 * 31) && intervalDays <= (30 * 12 * 31)) {
      //a flag indicating the type of timestamps ahead.
      timestamp.add("year");
      for (int i = 0; i < intervalDays / (30 * 12); i++) {
        timestamp.add(fromDateLocalDate.getYear() + "");
        fromDateLocalDate = fromDateLocalDate.plusYears(1);
      }
    }

    if (intervalDays > (30 * 12 * 31)) {
      throw new IllegalArgumentException();
    }


    return timestamp;
  }

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


    starCount.add((int) increment);
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

  @Override
  public double getIncrement() {
    return this.increment;
  }


}
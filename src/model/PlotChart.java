package model;

import java.util.List;

/**
 * This interface represents the methods required to plot a barchart for analyzing
 * the performance of a portfolio over a period of time.
 */
public interface PlotChart {

  /**
   * Method to get the timestamp from the start date to the end date.
   *
   * @param fromDate start date of the time range when the performance of the portfolio is to be
   *                 analyzed.
   * @param toDate   end date of the time range when the performance of the portfolio is to be
   *                 analyzed.
   * @return a list of timestamps.
   */


  List<String> getTimestamp(String fromDate, String toDate);

  /**
   * Method to get the count of stars which represents the performance of a portfolio at a
   * particular period in time.
   *
   * @param portfolioValues value of the portfolio.
   * @return the list of integers containing star counts.
   */
  List<Integer> getStarCount(List<Double> portfolioValues);

  double getIncrement();


}
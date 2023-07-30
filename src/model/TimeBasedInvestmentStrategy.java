package model;

/**
 * This interface represents the high level time-based long-term investment strategies which are
 * primarily based on a fixed amount given to be invested periodically during a time interval.
 */
public interface TimeBasedInvestmentStrategy {

  /**
   * This method is used to buy multiple stocks at a time given the required parameters.
   * @param amount the total amount that must be invested after certain intervals.
   * @param intervalNum the quantity of the interval duration given.
   * @param intervalDuration the interval duration (eg. days, months, years).
   * @param startDate the date at which investment has to be started.
   * @param endDate the date at which investment has to be ended.
   * @param ongoing boolean denoting if the investment is ongoing.
   */
  void buyStocks(String amount, String intervalNum, String intervalDuration,
                 String startDate, String endDate, boolean ongoing);

}

package model;

import java.time.LocalDate;

/**
 * This class implements the TimeBasedInvestmentStrategy interface and provides start-to-finish
 * investment strategy where a fixed amount is invested periodically in the given duration.
 * The investment can also be ongoing.
 */
class DollarCostAvgStartToFinishStrategy implements TimeBasedInvestmentStrategy {

  Model model;

  public DollarCostAvgStartToFinishStrategy(Model model) {
    this.model = model;
  }

  @Override
  public void buyStocks(String amount, String intervalNum, String intervalDuration,
                        String startDate, String endDate, boolean ongoing) {
    validateStartToEndInvest(amount, intervalNum, intervalDuration, startDate, endDate, ongoing);

    // get interval in days.
    int intervalDays = getIntervalInDays(intervalNum, intervalDuration);

    LocalDate localStartDate = LocalDate.parse(startDate);
    LocalDate localEndDate = LocalDate.parse(endDate);

    if (ongoing) {
      localEndDate = LocalDate.now().minusDays(1);
    }

    int i = 0;
    LocalDate buyingDate = localStartDate;
    while (localEndDate.isAfter(buyingDate)) {
      model.investFixedAmount(amount, buyingDate.toString());

      buyingDate = buyingDate.plusDays(intervalDays);

      i++;
    }
  }

  private void validateStartToEndInvest(String amount, String intervalNum, String intervalDuration,
                                        String startDate, String endDate, boolean ongoing) {
    //validate amount
    try {
      if (Double.parseDouble(amount) < 0) {
        throw new IllegalArgumentException("Amount cannot be negative.");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid amount entered.");
    }


    // validate interval

    try {
      if (Integer.parseInt(intervalNum) <= 0) {
        throw new IllegalArgumentException("Interval cannot be negative.");
      }
      if (getIntervalInDays(intervalNum, intervalDuration) > (365 * 5)) {
        throw new IllegalArgumentException("Interval cannot be more than 5 years.");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Only natural numbers allowed.");
    }


    // validate start date
    LocalDate localStartDate;
    try {
      localStartDate = LocalDate.parse(startDate);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid start date entered.");
    }
    if (localStartDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Date cannot be in future.");
    }

    // validate end date
    LocalDate localEndDate;
    try {
      localEndDate = LocalDate.parse(endDate);
      if (ongoing) {
        localEndDate = LocalDate.now().minusDays(1);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid end date entered.");
    }

    // validate interval between start date and end date
    if (localEndDate.isBefore(localStartDate)) {
      throw new IllegalArgumentException("End date cannot be before start date.");
    }
  }

  private int getIntervalInDays(String intervalNum, String intervalDuration) {
    switch (intervalDuration) {
      case "days":
        return Integer.parseInt(intervalNum);
      case "weeks":
        return Integer.parseInt(intervalNum) * 7;
      case "months":
        return Integer.parseInt(intervalNum) * 30;
      case "years":
        return Integer.parseInt(intervalNum) * 365;
      default:
        //pass
    }
    throw new IllegalArgumentException("Invalid interval entered");
  }


}

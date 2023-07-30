package model;

import java.util.List;

/**
 * This interface represents a User of a Portfolio Management system.
 */
public interface User {


  List<String> showPortfolios();

  /**
   * Given the portfolio name, this method returns the corresponding portfolio object.
   *
   * @param portfolioName the name of the portfolio that is required.
   * @return the Portfolio object containing the details of the required portfolio.
   */
  Portfolio getPortfolio(String portfolioName);

  /**
   * Enables user to upload in the project directory in their folder of portfolios
   * a file given the filepath.
   *
   * @param filepath the location of the file to be uploaded.
   */
  void uploadPortfolio(String filepath);

  /**
   * Get the first name of the user.
   *
   * @return first name of the user as a String.
   */
  String getFirstName();

  /**
   * Get the last name of the user.
   *
   * @return last name of the user as a String.
   */
  String getLastName();

  /**
   * Get the middle name of the user.
   *
   * @return middle name of the user as a String.
   */
  String getMiddleName();

  /**
   * Get the userID of the user.
   *
   * @return userID name of the user as a String.
   */
  String getUserID();

  /**
   * Get the password of the user.
   *
   * @return password of the user as int (since it will be saved as hashcode).
   */
  int getPassword();

  /**
   * Get the commission fee of an advanced user.
   *
   * @return the value of commission fee.
   */
  double getCommissionFee();

}

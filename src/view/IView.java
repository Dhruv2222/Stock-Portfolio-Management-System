package view;

import java.time.LocalDate;
import java.util.List;

import controller.IControllerGUI;
import model.Portfolio;

/**
 * This interface represents the view section of the Portfolio Management system.
 * It represents all the methods required to display outputs and results to the user through the
 * Graphical User interface.
 */
public interface IView {

  /**
   * This method supports the action listeners for various methods and functionalities of a
   * portfolio.
   * @param features the features object.
   */
  void addFeatures(IControllerGUI features);

  /**
   * This method is for displaying the user interface for the Observing (Inflexible) portfolios.
   */
  void createObservingUserInterface();

  /**
   * This method is for displaying the user interface for the Flexible portfolios.
   */
  void createFlexibleUserInterface();

  /**
   * This method displays the welcome message to the user along with the options to perform
   * operations on the portfolio.
   * @param firstName first name of the user.
   */
  void showWelcomeMessage(String firstName);

  /**
   * This method is for displaying the error dialog box in case of an invalid input.
   * @param errorMessage relevant error message on invalid input.
   */
  void showErrorDialogBox(String errorMessage);

  /**
   * This method is for displaying the interface for adding new stocks to flexible portfolio.
   */
  void showAddNewStockInFlexible();

  /**
   * This method is for displaying the interface for adding new stocks to inflexible portfolio.
   */
  void showAddNewStockInObserving();

  /**
   * This method is for resetting the values of stock fields.
   */
  void clearAddNewStockInputFields();

  /**
   * This method is for displaying the portfolios to the specific user.
   * @param portfolioNames names of the portfolios.
   */
  void showPortfolios(List<String> portfolioNames);

  /**
   * This method is for displaying the portfolios for selection to the specific user.
   * @param portfolioNames names of the portfolios.
   */
  void showPortfoliosForSelection(List<String> portfolioNames);

  /**
   * This method is for displaying the date field to the user.
   * @param displayDateField to decide whether the date field is supposed to be shown or not.
   */
  void showEnterDate(boolean displayDateField);

  /**
   * This method is for displaying the portfolio contents to the user.
   * @param portfolio the portfolio object.
   * @param prices the stock prices on a particular date.
   * @param date the date for which the portfolio details need to be retrieved.
   */
  void showPortfolioContents(Portfolio portfolio, List<Double> prices, LocalDate date);

  /**
   * This method is for displaying the flexible portfolio menu to the user.
   */
  void showFlexiblePortfolioMenu();

  /**
   * This method is for displaying the interface for selling stocks.
   */
  void sellPortfolioInterface();

  /**
   * This method is for displaying the success dialog box to the user.
   * @param title the title of the success dialog box.
   * @param message the message that needs to be displayed to the user.
   */
  void showSuccessDialogBox(String title, String message);

  /**
   * This method is for displaying the dialog box to the user for loading a file into the system.
   */
  void showUploadPortfolio();

  /**
   * This method is for displaying the date field to get the value of the flexible portfolio on a
   * particular date entered by the user.
   */
  void enterDateForFlexValueOnDate();

  /**
   * This method is for displaying the date field to get the cost basis of the flexible portfolio
   * on a particular date entered by the user.
   */
  void enterDateForFlexCostBasis();

  /**
   * This method is for displaying the value of the flexible portfolio in a panel.
   * @param date the date when the value needs to be shown.
   * @param value the value of the portfolio.
   */
  void showValueOfFlexPortfolioInPanel(String date, double value);

  /**
   * This method is for displaying the cost basis of the flexible portfolio in a panel.
   * @param date the date when the cost basis needs to be shown.
   * @param value the value of the portfolio.
   */
  void showCostBasisOfFlexPortfolioInPanel(String date, double value);

  /**
   * This method is for displaying the date field to find out the composition of the
   * Flexible Portfolio.
   */
  void enterDateForFlexShowComposition();

  /**
   * This method is for displaying the contents of the flexible portfolio to the user.
   * @param portfolio the portfolio object.
   */
  void showFlexPortfolioContents(Portfolio portfolio);

  /**
   * This method is for displaying the fixed amount strategy interface to the user.
   */
  void showInvestFixedAmount();

  /**
   * This method is for displaying the dollar-cost average strategy interface to the user.
   * @param hideEndDate checks if the end date needs to be disabled or not.
   */
  void showDollarCostAverage(boolean hideEndDate);

  /**
   * This method is for displaying the interface for adding more stocks to the fixed amount
   * strategy.
   * @param initial to check whether the stock entered was the initial stock.
   */
  void addMoreInvStockFields(boolean initial);

  /**
   * This method is for displaying the interface for adding more stocks to the dollar-cost average
   * strategy.
   * @param initial to check whether the stock entered was the initial stock.
   */
  void addMoreSTFInvStockFields(boolean initial);

  /**
   * This method is for displaying the line chart to the user.
   * @param portfolioName name of the portfolio.
   * @param fromDate the start date of graph.
   * @param toDate the end date of graph.
   * @param timeStamps the list of timestamps to be displayed.
   * @param height scale of the graph in the form of days, months, years.
   * @param increment the graph intervals.
   */
  void graphAnalysisLine(String portfolioName, String fromDate, String toDate,
                         List<String> timeStamps, List<Integer> height, double increment);

  /**
   * This method is for displaying the bar chart to the user.
   * @param portfolioName name of the portfolio.
   * @param fromDate the start date of graph.
   * @param toDate the end date of graph.
   * @param timeStamps the list of timestamps to be displayed.
   * @param height scale of the graph in the form of days, months, years.
   * @param increment the graph intervals.
   */
  void graphAnalysisBar(String portfolioName, String fromDate, String toDate,
                        List<String> timeStamps, List<Integer> height, double increment);

  /**
   * This method is for displaying the date input interface for the graph to the user.
   */
  void graphAnalysisDateInput();

  /**
   * This method is to go back to the list of portfolios for a user.
   */
  void goBackToListOfPortfolios();
}

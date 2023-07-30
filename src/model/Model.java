package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class states the model of the system. All the calculations and functionality implementation
 * is defined in this class.
 */
public class Model implements PortfolioModel {

  private static final List<List<String>> records = new ArrayList<>();
  private User currentUser;
  private Portfolio newPortfolio;
  private Portfolio consPortfolio;

  StockMarketAPI api;

  private Map<String, String> tickerRatio;
  private HashMap<String, String> tickerName;
  private double graphIncrement;

  /**
   * Constructor to create a Model object. It assigns the list of stock tickers to records
   * variable.
   */
  public Model() {
    try (Scanner scanner = new Scanner(new File("./data/listOfStockTickers.csv"))) {
      while (scanner.hasNextLine()) {
        records.add(getRecordFromLine(scanner.nextLine()));
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    api = new APIAlphaVantage();
  }

  private List<String> getRecordFromLine(String line) {
    List<String> values = new ArrayList<>();
    try (Scanner rowScanner = new Scanner(line)) {
      rowScanner.useDelimiter(",");
      while (rowScanner.hasNext()) {
        values.add(rowScanner.next());
      }
    }
    return values;
  }


  @Override
  public void createUser(String firstName, String lastName, String middleName, String userID,
                         String password, int portfolioType, double commissionFee) throws
          IllegalArgumentException {

    if (password.isEmpty() || userID.isEmpty()) {
      throw new IllegalArgumentException("Password or UserID cannot be empty. Try again.");
    }

    //if observing portfolio object is required.
    if (portfolioType == 1) {
      currentUser = UserImpl.getBuilder()
              .setFirstName(firstName)
              .setLastName(lastName)
              .setMiddleName(middleName)
              .setUserID(userID)
              .setPassword(password).build();
    }

    //if trading portfolio object is required.
    else {
      currentUser = AdvUserImpl.getBuilder()
              .setFirstName(firstName)
              .setLastName(lastName)
              .setMiddleName(middleName)
              .setUserID(userID)
              .setCommissionFee(commissionFee)
              .setPassword(password).build();
    }
  }

  @Override
  public void login(String userID, String password, int portfolioType) throws
          IllegalArgumentException {

    //validate existing user existence.
    User currentUser;

    SaveUserDetails retrieveUserDetails = new SaveUserDetailsImpl();
    this.currentUser = retrieveUserDetails.getUserDetails(userID, password, portfolioType);

  }

  @Override
  public void createPortfolio(String portfolioName, User currentUser, int portfolioType)
          throws IllegalArgumentException {
    if (showPortfolios(currentUser).contains(portfolioName)) {
      throw new IllegalArgumentException("Portfolio name already exists. Enter unique portfolio"
              + " name");
    }
    if (portfolioName.isEmpty()) {
      throw new IllegalArgumentException("Portfolio name invalid.");
    }
    //if observing portfolio object is required.
    if (portfolioType == 1) {
      this.newPortfolio = PortfolioImpl.getBuilder().setPortfolioName(portfolioName,
              currentUser).build();
    } else {
      //if trading portfolio object is required.
      this.newPortfolio = new AdvPortfolioImpl(portfolioName);
    }


  }

  //check if the given ticker is in the list of tickers.
  protected static boolean checkTickerExistence(String value) {
    for (List<String> ls : records) {
      for (String s : ls) {
        if (s.equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void addStockInPortfolio(String key, String value, Portfolio newPortfolio)
          throws IllegalArgumentException {
    newPortfolio.addStock(key, value);
  }

  @Override
  public void investFixedAmount(String amount, String date) {
    int noOfTransactions = 0;
    LocalDate localDate;
    double amountDouble;

    // validate the amount given.
    try {
      if (Double.parseDouble(amount) <= 0) {
        throw new IllegalArgumentException("Amount cannot be negative.");
      }
      amountDouble = Double.parseDouble(amount);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid amount entered.");
    }

    // validate date given
    try {
      localDate = LocalDate.parse(date);
    } catch (Exception e) {
      throw new IllegalArgumentException("Incorrect date entered.");
    }
    if (localDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Date cannot be in future.");
    }

    // validate and convert the ratios to double.
    Map<String, Double> convertedRatios = new HashMap<>();
    for (String ticker : this.tickerRatio.keySet()) {
      try {
        if (Double.parseDouble(this.tickerRatio.get(ticker)) < 0) {
          throw new IllegalArgumentException("Ratio cannot be negative.");
        }


        convertedRatios.put(ticker, Double.parseDouble(this.tickerRatio.get(ticker)));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid ratio entered.");
      }
    }

    // validate ratios
    double totalRatio = convertedRatios.values().stream().reduce(Double::sum).get();
    if (totalRatio != 100) {
      throw new IllegalArgumentException("Sum of ratios should add up to 100.");
    }


    // count the number of transactions that will be needed.
    for (String ticker : convertedRatios.keySet()) {
      if (convertedRatios.get(ticker) < 0) {
        throw new IllegalArgumentException("Ratio cannot be negative");
      }
      if (convertedRatios.get(ticker) == 0) {
        continue;
      }
      noOfTransactions++;
    }

    // deduct the commission fee from the amount.
    amountDouble = amountDouble - currentUser.getCommissionFee() * noOfTransactions;


    int i = 0;
    // buy the stocks
    for (String ticker : convertedRatios.keySet()) {

      double price = api.getPrice(ticker, LocalDate.parse(date));
      newPortfolio.addStock("stockID", ticker);
      newPortfolio.addStock("stockName", this.tickerName.get(ticker));
      newPortfolio.addStock("fractionalStockQuantity",
              String.valueOf((amountDouble * (convertedRatios.get(ticker) / 100)) / price));
      newPortfolio.addStock("transactionDate", date);
      newPortfolio.addStock("setBuyingPriceAndValueForInvFixedAmt", price + "");
      i++;
    }
    savePortfolio(newPortfolio, currentUser);

  }

  @Override
  public void startToEndInvest(String amount, String intervalNum, String intervalDuration,
                               String startDate, String endDate, boolean ongoing) {

    TimeBasedInvestmentStrategy tis = new DollarCostAvgStartToFinishStrategy(this);

    tis.buyStocks(amount, intervalNum, intervalDuration, startDate, endDate, ongoing);

  }


  @Override
  public void collectTickerRatios(String ticker, String ratio) {
    if (!checkTickerExistence(ticker)) {
      throw new IllegalArgumentException("Invalid ticker symbol entered.");
    }
    this.tickerRatio.put(ticker, ratio);


  }


  @Override
  public void collectTickerNames(String ticker, String stockName) {
    if (!checkTickerExistence(ticker)) {
      throw new IllegalArgumentException("Invalid ticker symbol entered.");
    }
    this.tickerName.put(ticker, stockName);
  }

  @Override
  public void startNewStrategy() {
    this.tickerName = new HashMap<>();
    this.tickerRatio = new HashMap<>();
  }

  protected static Double convertQuantityInput(String value) throws IllegalArgumentException {
    double qty;
    try {
      qty = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Stock quantity entered is invalid.");
    }
    //returning the floor value of the quantity if it is fractional.
    return Math.floor(qty);
  }

  @Override
  public void savePortfolio(Portfolio newPortfolio, User currentUser) {
    newPortfolio.savePortfolio(currentUser);
  }

  @Override
  public List<String> showPortfolios(User currentUser) {
    return currentUser.showPortfolios();
  }

  @Override
  public double showTotalInvestment(Portfolio newPortfolio, List<Double> prices) {

    List<Double> temp = new ArrayList<>();
    List<String> qty = newPortfolio.getStockQuantity();
    for (int i = 0; i < newPortfolio.getStockQuantity().size(); i++) {
      temp.add(prices.get(i) * Double.parseDouble(qty.get(i)));
    }

    return temp.stream().reduce((x, y) -> x + y).get();
  }

  @Override
  public void selectPortfolio(User currentUser, String portfolioName) {
    this.newPortfolio = currentUser.getPortfolio(portfolioName);
  }

  @Override
  public void uploadPortfolio(User currentUser, String filepath) {
    currentUser.uploadPortfolio(filepath);
  }

  @Override
  public List<Double> getStockPricesOnDate(LocalDate date, Portfolio newPortfolio) {

    List<Double> result = new ArrayList<>();
    for (String stockID : newPortfolio.getStockID()) {
      result.add(api.getPrice(stockID, date));
    }

    return result;
  }

  @Override
  public double getTotalValueOnDate(LocalDate date, Portfolio newPortfolio) {
    List<String> ids = newPortfolio.getStockID();
    Set<String> setIds = new HashSet<>(ids);
    List<String> uniqueStockIDs = new ArrayList<>(setIds);
    List<String> stockIDs = newPortfolio.getStockID();

    double stockValues = 0;

    for (String stockID : uniqueStockIDs) {
      double totalQty = 0;
      List<Integer> indexes = new ArrayList<>();

      //collect indexes of the duplicate entries of stockID.
      for (int i = 0; i < stockIDs.size(); i++) {
        if (stockIDs.get(i).equals(stockID)) {
          indexes.add(i);
        }
      }

      //calculate resultant quantity of positive and negative stocks available.
      for (int index : indexes) {
        if (newPortfolio.getTransactionDate().get(index).isBefore(date)) {
          totalQty += Double.parseDouble(newPortfolio.getStockQuantity().get(index));
        }

      }
      stockValues += (totalQty * api.getPrice(stockID, date));
    }
    return stockValues;
  }

  @Override
  public double getCostBasis(LocalDate date, Portfolio newPortfolio) {

    double costBasis = 0;
    int transactionCount = 0;

    for (int i = 0; i < newPortfolio.getTransactionDate().size(); i++) {

      if (newPortfolio.getTransactionDate().get(i).isBefore(date)) {
        transactionCount++;
        if (newPortfolio.getStockValue().get(i) > 0) {
          costBasis += newPortfolio.getStockValue().get(i);
        }
      }

    }

    //add commission fee for each transaction.
    return costBasis + (transactionCount * currentUser.getCommissionFee());
  }

  @Override
  public void showComposition(Portfolio newPortfolio, LocalDate date) {
    List<String> ids = newPortfolio.getStockID();
    Set<String> setIds = new HashSet<>(ids);
    List<String> stockIDs = newPortfolio.getStockID();
    Portfolio consolidatedPortfolio = new AdvPortfolioImpl("temp");


    for (String stockID : setIds) {
      double totalQty = 0;
      List<Integer> indexes = new ArrayList<>();
      String latestTransactionDate = "";


      //collect indexes of the duplicate entries of stockID.
      for (int i = 0; i < stockIDs.size(); i++) {
        if (stockIDs.get(i).equals(stockID)) {
          indexes.add(i);
        }
      }

      //calculate resultant quantity of positive and negative stocks available.
      for (int index : indexes) {
        if (date.isAfter(newPortfolio.getTransactionDate().get(index).minusDays(1))) {
          totalQty += Double.parseDouble(newPortfolio.getStockQuantity().get(index));
        }
      }

      //check if quantity is zero. If it is, then don't show stock.
      if (totalQty <= 0) {
        continue;
      }

      //add stock to temporary portfolio object.
      consolidatedPortfolio.setStockID(stockID);
      consolidatedPortfolio.setStockName(newPortfolio.getStockName().get(indexes.get(0)));
      consolidatedPortfolio.setStockQuantity(String.valueOf(totalQty));
    }

    this.consPortfolio = consolidatedPortfolio;
  }


  @Override
  public void sellStock(Portfolio newPortfolio, String stockID, double sellQty, String date) {
    newPortfolio.sellStock(stockID, sellQty, date);
    savePortfolio(newPortfolio, currentUser);
  }


  @Override
  public List<String> getGraphTimestamp(String fromDate, String toDate) {
    PlotChart chart = new PlotChartBarImpl();
    return chart.getTimestamp(fromDate, toDate);
  }

  @Override
  public List<Integer> getGraphStarCount(Portfolio newPortfolio, List<String> timestamp) {
    PlotChart chart = new PlotChartBarImpl();
    List<Double> portfolioValues = new ArrayList<>();

    if (timestamp.get(0).equals("day")) {
      for (int i = 1; i < timestamp.size(); i++) {
        LocalDate localDate = LocalDate.parse(timestamp.get(i));
        portfolioValues.add(getTotalValueOnDate(localDate, newPortfolio));
      }
    }

    if (timestamp.get(0).equals("month")) {
      for (int i = 1; i < timestamp.size(); i++) {
        LocalDate localDate = getLastWorkingDayOfMonth(timestamp.get(i));
        portfolioValues.add(getTotalValueOnDate(localDate, newPortfolio));
      }
    }

    if (timestamp.get(0).equals("year")) {
      for (int i = 1; i < timestamp.size(); i++) {
        LocalDate localDate = getLastWorkingDayOfMonth("DEC " + timestamp.get(i));
        portfolioValues.add(getTotalValueOnDate(localDate, newPortfolio));
      }
    }

    return chart.getStarCount(portfolioValues);
  }

  @Override
  public double getGraphIncrement() {
    return this.graphIncrement;
  }

  @Override
  public List<Integer> getGraphHeight(Portfolio newPortfolio, List<String> timestamp) {
    PlotChart chart = new PlotChartGUIImpl();
    List<Double> portfolioValues = new ArrayList<>();

    if (timestamp.get(0).equals("day")) {
      for (int i = 1; i < timestamp.size(); i++) {
        LocalDate localDate = LocalDate.parse(timestamp.get(i));
        portfolioValues.add(getTotalValueOnDate(localDate, newPortfolio));
      }
    }

    if (timestamp.get(0).equals("month")) {
      for (int i = 1; i < timestamp.size(); i++) {
        LocalDate localDate = getLastWorkingDayOfMonth(timestamp.get(i));
        portfolioValues.add(getTotalValueOnDate(localDate, newPortfolio));
      }
    }

    if (timestamp.get(0).equals("year")) {
      for (int i = 1; i < timestamp.size(); i++) {
        LocalDate localDate = getLastWorkingDayOfMonth("DEC " + timestamp.get(i));
        portfolioValues.add(getTotalValueOnDate(localDate, newPortfolio));
      }
    }
    List<Integer> count = chart.getStarCount(portfolioValues);
    this.graphIncrement = chart.getIncrement();
    return count;
  }


  private LocalDate handleWeekend(LocalDate yest) {

    if (yest.getDayOfWeek().toString().equals("SUNDAY")) {
      yest = yest.minusDays(2);
    }
    if (yest.getDayOfWeek().toString().equals("SATURDAY")) {
      yest = yest.minusDays(1);
    }
    return yest;
  }

  private LocalDate getLastWorkingDayOfMonth(String monthYear) {
    List<String> monthAndYear = new ArrayList<>(List.of(monthYear.split(" ")));


    DateTimeFormatter inputDtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
    String date = "01 " + Character.toUpperCase(monthAndYear.get(0).charAt(0)) +
            monthAndYear.get(0).substring(1).toLowerCase().substring(0, 2) + " " +
            monthAndYear.get(1);
    LocalDate ld = LocalDate.parse(date, inputDtf);

    ld = ld.withDayOfMonth(
            ld.getMonth().length(ld.isLeapYear()));

    ld = handleWeekend(ld);

    return ld;
  }

  @Override
  public User getCurrentUser() {
    return this.currentUser;
  }

  @Override
  public Portfolio getWorkingPortfolio() {
    return this.newPortfolio;
  }

  @Override
  public Portfolio getConsolidatedPortfolio() {
    return this.consPortfolio;
  }


  @Override
  public LocalDate validateDateInput(String date) {
    LocalDate localDate;
    try {
      localDate = LocalDate.parse(date);
      return localDate;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid date entered.");
    }
  }


}

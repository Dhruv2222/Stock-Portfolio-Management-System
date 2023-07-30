package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.IControllerGUI;
import model.Portfolio;

/**
 * This class extends JFrame and implements the IView interface.
 * It implements all the operations required to display results of the Portfolio Management system
 * through the Graphical User Interface.
 */
public class JFrameView extends JFrame implements IView {

  private final JTextField loginUserIDField;
  private final JTextField passwordField;
  private JTextField firstNameField;
  private JTextField lastNameField;
  private JTextField middleNameField;
  private JTextField createUserIDField;
  private JTextField createUserPasswordField;
  private JTextField commissionFeeField;
  private JTextField portfolioNameField;
  private JTextField stockTickerField;
  private JTextField stockNameField;
  private JTextField stockQuantityField;
  private JTextField amountField;
  private JTextField intervalNumField;
  private JTextField ratioField;
  private final JLabel selectAnOption;
  private final JLabel enterUserID;
  private final JLabel enterPassword;
  private JLabel enterFirstName;
  private JLabel enterLastName;
  private JLabel enterMiddleName;
  private JLabel enterStockTicker;
  private JLabel enterStockName;
  private JLabel enterStockQuantity;
  private JLabel viewPortfoliosHeading;
  private JLabel enterDate;
  private JLabel result;
  private JLabel amount;
  private JLabel setRatios;
  private JLabel startDate;
  private JLabel endDate;
  private JLabel enterRatio;

  private JRadioButton[] radioButtons;

  private final ButtonGroup portfolioButtonGroup;
  private final ButtonGroup flexMenuButtonGroup;

  private final JCheckBox selectPrevDate;
  private final JCheckBox selectOngoing;

  private JComboBox selectDay;
  private JComboBox selectMonth;
  private JComboBox selectYear;
  private JComboBox intervalDuration;
  private JComboBox selectEndDay;
  private JComboBox selectEndMonth;
  private JComboBox selectEndYear;

  private JButton okPortfolioType;
  private final JButton loginButton;
  private final JButton createUserButton;
  private final JButton createUserActionButton;
  private final JButton chooseCreatePortfolioButton;
  private final JButton viewPortfolioButton;
  private final JButton selectPortfolioButton;
  private final JButton uploadPortfolioButton;
  private final JButton createPortfolioButton;
  private final JButton savePortfolioButton;
  private final JButton addMoreStocksButton;
  private final JButton okSelectPortfolio;
  private final JButton getPricesButton;
  private final JButton okSelectFlexMenuOption;
  private final JButton sellStockButton;
  private final JButton saveFlexPortfolioButton;
  private final JButton fileSaveButton;
  private final JButton getFlexValue;
  private final JButton goToFlexPortfolioMenu;
  private final JButton getFlexCostBasis;
  private final JButton getShowComposition;
  private final JButton investFixedAmtButton;
  private final JButton dollarCostAverageButton;
  private final JButton addMoreStockForInvButton;
  private final JButton addMoreStockForSTFInvButton;
  private final JButton addStocksUsingDollarCostAvg;
  private final JButton showLineChartButton;
  private final JButton showBarChartButton;
  private final JButton goBackToLogin;
  private final JButton goBackToSelectPortfolio;
  private final JButton exitSystem;

  private JPanel selectPortfolioTypeRadioPanel;
  private JPanel startUpMenuPanel;
  private JPanel createUserPanel;
  private JPanel portfolioMenuPanel;
  private JPanel portfolioNameEntryPanel;
  private JPanel addNewStockPanel;
  private JPanel showPortfoliosPanel;
  private JPanel enterDatePanel;
  private JPanel showObservingPortfolioContentPanel;
  private JPanel flexiblePortfolioMenuPanel;
  private JPanel sellPortfolioPanel;
  private JPanel showFlexPortfolioContentPanel;
  private JPanel investFixedAmountPanel;
  private JPanel dollarCostAveragePanel;
  private JPanel graphAnalysisDateInputPanel;
  private JPanel endDateRowPanel;

  private boolean initial;

  private final JFileChooser fileChooser;
  private boolean disableEndDate = false;

  /**
   * Constructor for initializing the components of the graphical user interface and adding message
   * fields.
   */
  public JFrameView() {

    this.setSize(1000, 300);
    this.setLocation(200, 200);
    this.setTitle("Portfolio Manager");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout());
    this.setBackground(Color.cyan);

    //initialize components
    //add message field
    selectAnOption = new JLabel("Login Page:");
    enterUserID = new JLabel("Enter username");
    loginUserIDField = new JTextField(10);
    enterPassword = new JLabel("Enter password");
    passwordField = new JTextField(10);
    loginButton = new JButton("Login");
    createUserButton = new JButton("Create New User");
    createUserActionButton = new JButton("Create User");
    chooseCreatePortfolioButton = new JButton("Create portfolio");
    viewPortfolioButton = new JButton("View list of portfolios");
    selectPortfolioButton = new JButton("Select a portfolio");
    uploadPortfolioButton = new JButton("Upload a portfolio");
    createPortfolioButton = new JButton("Create");
    savePortfolioButton = new JButton("Save");
    addMoreStocksButton = new JButton("Add more stocks");
    okSelectPortfolio = new JButton("Select portfolio");
    selectPrevDate = new JCheckBox("Get prices for previous day");
    selectPrevDate.setSelected(false);
    getPricesButton = new JButton("Get prices");
    okSelectFlexMenuOption = new JButton("OK");
    sellStockButton = new JButton("Sell");
    saveFlexPortfolioButton = new JButton("SAVE");
    fileSaveButton = new JButton("Load");
    getFlexValue = new JButton("Get value");
    goToFlexPortfolioMenu = new JButton("Go to menu");
    goBackToLogin = new JButton("Go back to Login");
    getFlexCostBasis = new JButton("Get cost-basis");

    fileChooser = new JFileChooser(".");
    getShowComposition = new JButton("Get composition");

    investFixedAmtButton = new JButton("Buy");
    dollarCostAverageButton = new JButton("Create");

    selectOngoing = new JCheckBox("Ongoing");
    selectPrevDate.setSelected(false);

    addMoreStockForInvButton = new JButton("Add more stocks");
    addMoreStockForSTFInvButton = new JButton("Add more stocks");

    addStocksUsingDollarCostAvg = new JButton("Use Dollar-cost average");

    showLineChartButton = new JButton("Plot Line chart");
    showBarChartButton = new JButton("Plot Bar Chart");

    goBackToSelectPortfolio = new JButton("Go Back");

    exitSystem = new JButton("Exit");

    flexMenuButtonGroup = new ButtonGroup();
    portfolioButtonGroup = new ButtonGroup();

    showSelectTypeOfPortfolio();

  }


  private void showSelectTypeOfPortfolio() {

    if (startUpMenuPanel != null) {
      this.remove(startUpMenuPanel);
    }
    //radio buttons
    selectPortfolioTypeRadioPanel = new JPanel();
    selectPortfolioTypeRadioPanel.setBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30));

    selectPortfolioTypeRadioPanel.setLayout(new BoxLayout(selectPortfolioTypeRadioPanel,
            BoxLayout.PAGE_AXIS));

    radioButtons = new JRadioButton[2];

    JLabel radioDisplay = new JLabel("Select the type of portfolio:");
    selectPortfolioTypeRadioPanel.add(radioDisplay);

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();

    radioButtons[0] = new JRadioButton("Observing Portfolio");
    radioButtons[1] = new JRadioButton("Flexible Portfolio");

    for (int i = 0; i < radioButtons.length; i++) {

      rGroup1.add(radioButtons[i]);
      selectPortfolioTypeRadioPanel.add(radioButtons[i]);

    }

    okPortfolioType = new JButton("OK");
    selectPortfolioTypeRadioPanel.add(okPortfolioType);

    this.add(selectPortfolioTypeRadioPanel);

    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(IControllerGUI features) {
    //add action listener to radio buttons for selecting portfolio type
    radioButtons[0].addActionListener(evt -> features.selectedPortfolioType(1));
    radioButtons[1].addActionListener(evt -> features.selectedPortfolioType(2));
    okPortfolioType.addActionListener(evt -> startUpMenuInterface());

    //action listener for selecting create user or login
    createUserButton.addActionListener(evt -> features.decideUserType());
    loginButton.addActionListener(evt -> features.login(loginUserIDField.getText(),
            passwordField.getText()));

    //action listener for creating new user
    createUserActionButton.addActionListener(evt -> features.createUser(firstNameField.getText(),
            lastNameField.getText(), middleNameField.getText(),
            createUserIDField.getText(), createUserPasswordField.getText(),
            commissionFeeField.getText()));

    //portfolio menu action listeners
    chooseCreatePortfolioButton.addActionListener(evt -> showPortfolioNameTextField());
    createPortfolioButton.addActionListener(evt ->
            features.createPortfolio(portfolioNameField.getText()));
    viewPortfolioButton.addActionListener(evt -> features.showPortfolios("display"));
    selectPortfolioButton.addActionListener(evt -> features.showPortfolios("select"));
    okSelectPortfolio.addActionListener(evt ->
            features.selectPortfolio(portfolioButtonGroup.getSelection().getActionCommand(),
                    selectPrevDate.isSelected()));
    selectPrevDate.addActionListener(evt -> showEnterDate(selectPrevDate.isSelected()));
    getPricesButton.addActionListener(evt -> features.showPricesForADate(selectPrevDate
            .isSelected(), concatDate()));


    //add new stock listeners
    savePortfolioButton.addActionListener(evt -> features.savePortfolio(stockTickerField.getText(),
            stockNameField.getText(), stockQuantityField.getText(), concatDate()));
    savePortfolioButton.addActionListener(evt -> removeAddNewStockPanel());

    addMoreStocksButton.addActionListener(evt ->
            features.addNewStockInPortfolio(stockTickerField.getText(),
                    stockNameField.getText(), stockQuantityField.getText(), concatDate()));

    //flexible portfolio menu
    okSelectFlexMenuOption.addActionListener(evt -> features.flexiblePortfolioFeatures(
            flexMenuButtonGroup.getSelection().getActionCommand()));
    sellStockButton.addActionListener(evt -> features.sellStock(stockTickerField.getText(),
            stockQuantityField.getText(), concatDate()));
    sellStockButton.addActionListener(evt -> clearSellStockPage());
    saveFlexPortfolioButton.addActionListener(evt -> features.saveFlexPortfolio());

    //upload portfolio
    uploadPortfolioButton.addActionListener(evt -> showUploadPortfolio());
    fileChooser.addActionListener(evt -> features.uploadPortfolio(getFilepath()));

    //get value on a date for flex portfolio
    getFlexValue.addActionListener(evt -> features.getValueOnDate(concatDate()));

    //go back to flex portfolio menu button
    goToFlexPortfolioMenu.addActionListener(evt -> showFlexiblePortfolioMenu());

    //get cost basis for flexible portfolio
    getFlexCostBasis.addActionListener(evt -> features.getCostBasis(concatDate()));

    //get composition of the portfolio
    getShowComposition.addActionListener(evt -> features.showComposition(concatDate()));

    //fixed investment strategy
    addMoreStockForInvButton.addActionListener(evt ->
            features.addMoreStocksInStrategy(stockTickerField.getText(),
                    stockNameField.getText(), ratioField.getText(), initial, 1));
    investFixedAmtButton.addActionListener(evt ->
            features.investFixedAmount(stockTickerField.getText(),
                    stockNameField.getText(), ratioField.getText(), initial, amountField.getText(),
                    concatDate()));

    //Start to finish investment strategy
    selectOngoing.addActionListener(evt -> toggleEndDateField(selectOngoing.isSelected()));
    addMoreStockForSTFInvButton.addActionListener(evt ->
            features.addMoreStocksInStrategy(stockTickerField.getText(),
                    stockNameField.getText(), ratioField.getText(), initial, 2));
    dollarCostAverageButton.addActionListener(evt ->
            features.dollarCostAvg(stockTickerField.getText(),
                    stockNameField.getText(), ratioField.getText(), amountField.getText(),
                    intervalNumField.getText(), intervalDuration.getSelectedItem().toString(),
                    concatDate(),
                    concatEndDate(), selectOngoing.isSelected(), initial));
    addStocksUsingDollarCostAvg.addActionListener(evt -> features
            .flexiblePortfolioFeatures("Dollar-cost Averaging"));

    //display graph
    showLineChartButton.addActionListener(evt -> features.plotLineGraph(concatDate(),
            concatEndDate()));
    showBarChartButton.addActionListener(evt -> features.plotBarGraph(concatDate(),
            concatEndDate()));

    // back to login
    goBackToLogin.addActionListener(evt -> startUpMenuInterface());

    // back to select portfolio
    goBackToSelectPortfolio.addActionListener(evt -> showSelectTypeOfPortfolio());

    //exit the program or system
    exitSystem.addActionListener(evt -> exitSystem());
  }

  private void exitSystem() {
    System.exit(0);
  }


  private String concatDate() {
    return selectYear.getSelectedItem().toString() + "-" +
            selectMonth.getSelectedItem().toString() + "-" + selectDay.getSelectedItem().toString();
  }

  private String concatEndDate() {
    // if ongoing is selected, then return yesterday's date.
    if (disableEndDate) {
      return LocalDate.now().minusDays(1).toString();
    }
    //else return the selected end date.
    return selectEndYear.getSelectedItem().toString() + "-" +
            selectEndMonth.getSelectedItem().toString() + "-" +
            selectEndDay.getSelectedItem().toString();
  }


  private String getFilepath() {
    if (fileChooser.getSelectedFile() == null) {
      return "null";
    } else {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
  }


  private void startUpMenuInterface() {
    if (createUserPanel != null) {
      this.remove(createUserPanel);
    }

    if (radioButtons.length > 0 && radioButtons[0] != null && radioButtons[1] != null
            && !radioButtons[0].isSelected() && !radioButtons[1].isSelected()) {
      showErrorDialogBox("You must select a portfolio type.");
      return;
    }

    //remove older display
    this.remove(selectPortfolioTypeRadioPanel);

    if (portfolioMenuPanel != null) {
      this.remove(portfolioMenuPanel);
    }

    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }

    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }

    loginUserIDField.setText("");
    passwordField.setText("");

    //set up new panel
    startUpMenuPanel = new JPanel();
    startUpMenuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    startUpMenuPanel.setLayout(new BoxLayout(startUpMenuPanel, BoxLayout.PAGE_AXIS));

    //add components to the panel
    startUpMenuPanel.add(selectAnOption);
    startUpMenuPanel.add(enterUserID);
    startUpMenuPanel.add(loginUserIDField);
    startUpMenuPanel.add(enterPassword);
    startUpMenuPanel.add(passwordField);
    startUpMenuPanel.add(loginButton);
    startUpMenuPanel.add(createUserButton);

    //add panel to the frame
    this.add(startUpMenuPanel);

    //set visible
    pack();
    setVisible(true);
  }


  @Override
  public void createObservingUserInterface() {
    //remove older display
    this.remove(startUpMenuPanel);

    //set up new panel
    createUserPanel = new JPanel();
    createUserPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    createUserPanel.setLayout(new BoxLayout(createUserPanel, BoxLayout.PAGE_AXIS));

    //initialize components
    enterFirstName = new JLabel("Enter first name:");
    enterLastName = new JLabel("Enter last name:");
    enterMiddleName = new JLabel("Enter middle name:");

    firstNameField = new JTextField();
    lastNameField = new JTextField();
    middleNameField = new JTextField();
    createUserIDField = new JTextField();
    createUserPasswordField = new JTextField();
    commissionFeeField = new JTextField();


    //add components
    createUserPanel.add(enterFirstName);
    createUserPanel.add(firstNameField);

    createUserPanel.add(enterLastName);
    createUserPanel.add(lastNameField);

    createUserPanel.add(enterMiddleName);
    createUserPanel.add(middleNameField);

    createUserPanel.add(enterUserID);
    createUserPanel.add(createUserIDField);

    createUserPanel.add(enterPassword);
    createUserPanel.add(createUserPasswordField);
    commissionFeeField.setText("0");


    createUserPanel.add(createUserActionButton);

    createUserPanel.add(goBackToLogin);

    //add panel to the frame
    this.add(createUserPanel);

    //set visible
    pack();
    setVisible(true);
  }

  @Override
  public void createFlexibleUserInterface() {
    //remove older display
    this.remove(startUpMenuPanel);

    //set up new panel
    createUserPanel = new JPanel();
    createUserPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    createUserPanel.setLayout(new BoxLayout(createUserPanel, BoxLayout.PAGE_AXIS));

    //initialize components
    enterFirstName = new JLabel("Enter first name:");
    enterLastName = new JLabel("Enter last name:");
    enterMiddleName = new JLabel("Enter middle name:");
    JLabel enterCommissionFee = new JLabel("Enter commission fee:");

    firstNameField = new JTextField();
    lastNameField = new JTextField();
    middleNameField = new JTextField();
    commissionFeeField = new JTextField();
    createUserIDField = new JTextField();
    createUserPasswordField = new JTextField();


    //add components
    createUserPanel.add(enterFirstName);
    createUserPanel.add(firstNameField);

    createUserPanel.add(enterLastName);
    createUserPanel.add(lastNameField);

    createUserPanel.add(enterMiddleName);
    createUserPanel.add(middleNameField);

    createUserPanel.add(enterCommissionFee);
    createUserPanel.add(commissionFeeField);

    createUserPanel.add(enterUserID);
    createUserPanel.add(createUserIDField);

    createUserPanel.add(enterPassword);
    createUserPanel.add(createUserPasswordField);


    createUserPanel.add(createUserActionButton);

    createUserPanel.add(goBackToLogin);

    //add panel to the frame
    this.add(createUserPanel);

    //set visible
    pack();
    setVisible(true);
  }

  @Override
  public void showWelcomeMessage(String firstName) {
    if (createUserPanel != null) {
      this.remove(createUserPanel);
    }
    if (startUpMenuPanel != null) {
      this.remove(startUpMenuPanel);
    }

    //set up new panel
    portfolioMenuPanel = new JPanel();
    portfolioMenuPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    portfolioMenuPanel.setLayout(new BoxLayout(portfolioMenuPanel, BoxLayout.PAGE_AXIS));

    JLabel welcomeMessage = new JLabel("Hello " + firstName + ",");
    portfolioMenuPanel.add(welcomeMessage);

    portfolioMenuPanel.add(chooseCreatePortfolioButton);
    portfolioMenuPanel.add(viewPortfolioButton);
    portfolioMenuPanel.add(selectPortfolioButton);
    portfolioMenuPanel.add(uploadPortfolioButton);
    portfolioMenuPanel.add(exitSystem);


    //add panel to the frame
    this.add(portfolioMenuPanel);

    //set visible
    pack();
    setVisible(true);

  }

  private void showPortfolioNameTextField() {

    if (showObservingPortfolioContentPanel != null) {
      this.remove(showObservingPortfolioContentPanel);
    }

    if (showFlexPortfolioContentPanel != null) {
      this.remove(showFlexPortfolioContentPanel);
    }

    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }

    if (investFixedAmountPanel != null) {
      this.remove(investFixedAmountPanel);
    }

    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }

    if (flexiblePortfolioMenuPanel != null) {
      this.remove(flexiblePortfolioMenuPanel);
    }

    if (addNewStockPanel != null) {
      this.remove(addNewStockPanel);
    }

    if (sellPortfolioPanel != null) {
      this.remove(sellPortfolioPanel);
    }

    if (investFixedAmountPanel != null) {
      this.remove(investFixedAmountPanel);
    }

    if (dollarCostAveragePanel != null) {
      this.remove(dollarCostAveragePanel);
    }

    //set up new panel
    portfolioNameEntryPanel = new JPanel();
    portfolioNameEntryPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    portfolioNameEntryPanel.setLayout(new BoxLayout(portfolioNameEntryPanel, BoxLayout.PAGE_AXIS));

    JLabel enterPortfolioName = new JLabel("Enter name of the portfolio:");
    portfolioNameField = new JTextField();

    portfolioNameEntryPanel.add(enterPortfolioName);
    portfolioNameEntryPanel.add(portfolioNameField);
    portfolioNameEntryPanel.add(createPortfolioButton);

    this.add(portfolioNameEntryPanel);

    //set visible
    pack();
    setVisible(true);
  }

  @Override
  public void showErrorDialogBox(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage,
            "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showAddNewStockInObserving() {
    this.remove(portfolioNameEntryPanel);

    //set up new panel
    addNewStockPanel = new JPanel();
    addNewStockPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    addNewStockPanel.setLayout(new BoxLayout(addNewStockPanel, BoxLayout.PAGE_AXIS));

    //initiating fields
    enterStockTicker = new JLabel("Enter stock ticker:");
    enterStockQuantity = new JLabel("Enter stock quantity:");
    enterStockName = new JLabel("Enter stock name:");

    stockTickerField = new JTextField();
    stockNameField = new JTextField();
    stockQuantityField = new JTextField();
    JTextField transactionDateField = new JTextField();
    transactionDateField.setText("");

    addNewStockPanel.add(enterStockTicker);
    addNewStockPanel.add(stockTickerField);
    addNewStockPanel.add(enterStockName);
    addNewStockPanel.add(stockNameField);
    addNewStockPanel.add(enterStockQuantity);
    addNewStockPanel.add(stockQuantityField);

    addNewStockPanel.add(addMoreStocksButton);
    addNewStockPanel.add(savePortfolioButton);

    this.add(addNewStockPanel);

    //set visible
    pack();
    setVisible(true);

  }

  @Override
  public void showAddNewStockInFlexible() {
    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }
    if (flexiblePortfolioMenuPanel != null) {
      this.remove(flexiblePortfolioMenuPanel);
    }

    //set up new panel
    addNewStockPanel = new JPanel();
    addNewStockPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    addNewStockPanel.setLayout(new BoxLayout(addNewStockPanel, BoxLayout.PAGE_AXIS));

    //initiating fields
    JLabel heading = new JLabel("Enter buying details:");
    enterStockTicker = new JLabel("Enter stock ticker:");
    enterStockQuantity = new JLabel("Enter stock quantity:");
    enterStockName = new JLabel("Enter stock name:");
    JLabel enterTransactionDate = new JLabel("Enter transaction date:");


    stockTickerField = new JTextField();
    stockNameField = new JTextField();
    stockQuantityField = new JTextField();
    stockTickerField.setPreferredSize(new Dimension(150, 20));
    stockNameField.setPreferredSize(new Dimension(150, 20));
    stockQuantityField.setPreferredSize(new Dimension(150, 20));

    addNewStockPanel.add(heading);

    JPanel tickerPanel = new JPanel();
    tickerPanel.setLayout(new FlowLayout());
    JPanel namePanel = new JPanel();
    namePanel.setLayout(new FlowLayout());
    JPanel qtyPanel = new JPanel();
    qtyPanel.setLayout(new FlowLayout());

    tickerPanel.add(enterStockTicker);
    tickerPanel.add(stockTickerField);
    addNewStockPanel.add(tickerPanel);
    namePanel.add(enterStockName);
    namePanel.add(stockNameField);
    addNewStockPanel.add(namePanel);
    qtyPanel.add(enterStockQuantity);
    qtyPanel.add(stockQuantityField);
    addNewStockPanel.add(qtyPanel);
    showDatePicker(addNewStockPanel, enterTransactionDate);

    addNewStockPanel.add(addMoreStocksButton);
    addNewStockPanel.add(savePortfolioButton);
    addNewStockPanel.add(addStocksUsingDollarCostAvg);


    this.add(addNewStockPanel);

    //set visible
    pack();
    setVisible(true);

  }


  @Override
  public void clearAddNewStockInputFields() {
    stockTickerField.setText("");
    stockNameField.setText("");
    stockQuantityField.setText("");
  }

  private void removeAddNewStockPanel() {
    this.remove(addNewStockPanel);

    //set visible
    pack();
    setVisible(true);

  }

  @Override
  public void showSuccessDialogBox(String title, String message) {
    JOptionPane.showMessageDialog(this, message,
            title, JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void showPortfolios(List<String> portfolioNames) {
    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }

    if (showObservingPortfolioContentPanel != null) {
      this.remove(showObservingPortfolioContentPanel);
    }

    if (showFlexPortfolioContentPanel != null) {
      this.remove(showFlexPortfolioContentPanel);
    }

    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }

    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }

    if (addNewStockPanel != null) {
      this.remove(addNewStockPanel);
    }

    if (flexiblePortfolioMenuPanel != null) {
      this.remove(flexiblePortfolioMenuPanel);
    }

    if (sellPortfolioPanel != null) {
      this.remove(sellPortfolioPanel);
    }

    if (investFixedAmountPanel != null) {
      this.remove(investFixedAmountPanel);
    }

    if (dollarCostAveragePanel != null) {
      this.remove(dollarCostAveragePanel);
    }

    //set up new panel
    showPortfoliosPanel = new JPanel();
    showPortfoliosPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

    showPortfoliosPanel.setLayout(new BoxLayout(showPortfoliosPanel, BoxLayout.PAGE_AXIS));

    if (portfolioNames.isEmpty()) {
      showPortfoliosPanel.add(new JLabel("No portfolios created."));

      this.add(showPortfoliosPanel);
      //set visible
      pack();
      setVisible(true);
      return;
    }

    viewPortfoliosHeading = new JLabel("List of your portfolios:");
    showPortfoliosPanel.add(viewPortfoliosHeading);

    for (int i = 0; i < portfolioNames.size(); i++) {
      showPortfoliosPanel.add(new JLabel((i + 1) + ". " + portfolioNames.get(i)));
      //listOfPortfolios[i] = new JLabel(portfolioNames.get(i));
    }

    this.add(showPortfoliosPanel);
    //set visible
    pack();
    setVisible(true);

  }

  @Override
  public void showPortfoliosForSelection(List<String> portfolioNames) {
    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }

    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }

    if (showObservingPortfolioContentPanel != null) {
      this.remove(showObservingPortfolioContentPanel);
    }

    if (showFlexPortfolioContentPanel != null) {
      this.remove(showFlexPortfolioContentPanel);
    }

    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    if (addNewStockPanel != null) {
      this.remove(addNewStockPanel);
    }

    if (flexiblePortfolioMenuPanel != null) {
      this.remove(flexiblePortfolioMenuPanel);
    }

    if (sellPortfolioPanel != null) {
      this.remove(sellPortfolioPanel);
    }

    if (investFixedAmountPanel != null) {
      this.remove(investFixedAmountPanel);
    }

    if (dollarCostAveragePanel != null) {
      this.remove(dollarCostAveragePanel);
    }


    //set up new panel
    showPortfoliosPanel = new JPanel();
    showPortfoliosPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));

    showPortfoliosPanel.setLayout(new BoxLayout(showPortfoliosPanel, BoxLayout.PAGE_AXIS));

    if (portfolioNames.isEmpty()) {
      showPortfoliosPanel.add(new JLabel("No portfolios created."));

      this.add(showPortfoliosPanel);
      //set visible
      pack();
      setVisible(true);
      return;
    }

    viewPortfoliosHeading = new JLabel("List of your portfolios:");
    showPortfoliosPanel.add(viewPortfoliosHeading);

    radioButtons = new JRadioButton[portfolioNames.size()];


    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(portfolioNames.get(i), true);
      //radioButtons[i].setSelected(false);
      radioButtons[i].setActionCommand(portfolioNames.get(i));

      portfolioButtonGroup.add(radioButtons[i]);
      showPortfoliosPanel.add(radioButtons[i]);

    }

    showPortfoliosPanel.add(okSelectPortfolio);


    this.add(showPortfoliosPanel);
    //set visible
    pack();
    setVisible(true);

  }

  private void showDatePicker(JPanel panel, JLabel heading) {
    // set labels for fields.
    JLabel dayLabel = new JLabel("Day:");
    JLabel monthLabel = new JLabel("Month:");
    JLabel yearLabel = new JLabel("Year:");
    JPanel dateRowPanel = new JPanel();
    dateRowPanel.setLayout(new FlowLayout());


    // create and populate dropdowns.
    selectDay = new JComboBox<>();
    for (int i = 1; i <= 31; i++) {
      if (i < 10) {
        selectDay.addItem("0" + i);
      } else {
        selectDay.addItem(i);
      }

    }
    selectDay.setSelectedItem(15);

    selectMonth = new JComboBox<>();
    for (int i = 1; i <= 12; i++) {
      if (i < 10) {
        selectMonth.addItem("0" + i);
      } else {
        selectMonth.addItem(i);
      }
    }
    selectMonth.setSelectedItem(05);

    selectYear = new JComboBox<>();
    for (int i = 2002; i <= 2022; i++) {
      selectYear.addItem(i);
    }
    selectYear.setSelectedItem(2021);

    panel.add(heading);

    dateRowPanel.add(dayLabel);
    dateRowPanel.add(selectDay);
    dateRowPanel.add(monthLabel);
    dateRowPanel.add(selectMonth);
    dateRowPanel.add(yearLabel);
    dateRowPanel.add(selectYear);

    panel.add(dateRowPanel);
  }

  private void showEndDatePicker(JPanel panel, JLabel heading) {
    // set labels for fields.
    JLabel dayLabel = new JLabel("Day:");
    JLabel monthLabel = new JLabel("Month:");
    JLabel yearLabel = new JLabel("Year:");
    endDateRowPanel = new JPanel();
    endDateRowPanel.setLayout(new FlowLayout());

    // create and populate dropdowns.
    selectEndDay = new JComboBox<>();
    for (int i = 1; i <= 31; i++) {
      if (i < 10) {
        selectEndDay.addItem("0" + i);
      } else {
        selectEndDay.addItem(i);
      }

    }
    selectEndDay.setSelectedItem(15);

    selectEndMonth = new JComboBox<>();
    for (int i = 1; i <= 12; i++) {
      if (i < 10) {
        selectEndMonth.addItem("0" + i);
      } else {
        selectEndMonth.addItem(i);
      }
    }
    selectEndMonth.setSelectedItem(05);

    selectEndYear = new JComboBox<>();
    for (int i = 2002; i <= 2022; i++) {
      selectEndYear.addItem(i);
    }
    selectEndYear.setSelectedItem(2021);

    panel.add(heading);
    endDateRowPanel.add(dayLabel);
    endDateRowPanel.add(selectEndDay);
    endDateRowPanel.add(monthLabel);
    endDateRowPanel.add(selectEndMonth);
    endDateRowPanel.add(yearLabel);
    endDateRowPanel.add(selectEndYear);
    panel.add(endDateRowPanel);
  }


  @Override
  public void showEnterDate(boolean displayDateField) {

    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    this.remove(showPortfoliosPanel);

    //set up new panel
    enterDatePanel = new JPanel();
    enterDatePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    enterDatePanel.setLayout(new BoxLayout(enterDatePanel, BoxLayout.PAGE_AXIS));

    enterDate = new JLabel("View price of portfolio on:");
    JLabel enterDateOR = new JLabel("OR");


    showDatePicker(enterDatePanel, enterDate);

    // if checkbox is selected.
    if (displayDateField) {
      selectDay.setEnabled(false);
      selectMonth.setEnabled(false);
      selectYear.setEnabled(false);
    }


    enterDatePanel.add(enterDateOR);
    enterDatePanel.add(selectPrevDate);
    enterDatePanel.add(getPricesButton);


    this.add(enterDatePanel);


    pack();
    setVisible(true);
  }

  @Override
  public void showPortfolioContents(Portfolio portfolio, List<Double> prices, LocalDate date) {
    if (showObservingPortfolioContentPanel != null) {
      this.remove(showObservingPortfolioContentPanel);
    }
    int n = portfolio.getStockID().size();

    //set up new panel
    showObservingPortfolioContentPanel = new JPanel();
    showObservingPortfolioContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    showObservingPortfolioContentPanel.setLayout(new
            BoxLayout(showObservingPortfolioContentPanel, BoxLayout.PAGE_AXIS));

    this.remove(enterDatePanel);

    List<String> strPrices = prices.stream().map(Object::toString).collect(Collectors.toList());

    String[] columns = {"Stock Ticker", "Stock Name", "Stock Quantity", "Stock Price on " + date};
    String[] tickerArray = convertToStringArray(portfolio.getStockID());
    String[] nameArray = convertToStringArray(portfolio.getStockName());
    String[] quantityArray = convertToStringArray(portfolio.getStockQuantity());
    String[] priceArray = convertToStringArray(strPrices);

    String[][] data = new String[n][4];
    for (int i = 0; i < n; i++) {
      data[i][0] = tickerArray[i];
      data[i][1] = nameArray[i];
      data[i][2] = quantityArray[i];
      data[i][3] = priceArray[i];
    }

    JTable table = new JTable(data, columns);
    table.setBounds(30, 40, 20, 30);
    JScrollPane jp = new JScrollPane(table);


    showObservingPortfolioContentPanel.add(jp);

    this.add(showObservingPortfolioContentPanel);


    pack();
    setVisible(true);

  }

  @Override
  public void showFlexiblePortfolioMenu() {
    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    if (addNewStockPanel != null) {
      this.remove(addNewStockPanel);
    }

    if (sellPortfolioPanel != null) {
      this.remove(sellPortfolioPanel);
    }

    if (graphAnalysisDateInputPanel != null) {
      this.remove(graphAnalysisDateInputPanel);
    }

    if (investFixedAmountPanel != null) {
      this.remove(investFixedAmountPanel);
    }

    if (showFlexPortfolioContentPanel != null) {
      this.remove(showFlexPortfolioContentPanel);
    }

    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }


    //set up new panel
    flexiblePortfolioMenuPanel = new JPanel();
    flexiblePortfolioMenuPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    flexiblePortfolioMenuPanel.setLayout(new
            BoxLayout(flexiblePortfolioMenuPanel, BoxLayout.PAGE_AXIS));


    JLabel flexMenuHeading = new JLabel("Select an option:");
    flexiblePortfolioMenuPanel.add(flexMenuHeading);

    List<String> menuOptions = new ArrayList<>();
    menuOptions.add("Buy stock");
    menuOptions.add("Sell stock");
    menuOptions.add("Get value on a date");
    menuOptions.add("Get cost-basis");
    menuOptions.add("Get portfolio composition");
    menuOptions.add("Graph analysis");
    menuOptions.add("Invest Fixed Amount");
    menuOptions.add("Dollar-cost Averaging");

    JRadioButton[] flexMenuRadioButtons = new JRadioButton[8];


    for (int i = 0; i < flexMenuRadioButtons.length; i++) {
      flexMenuRadioButtons[i] = new JRadioButton(menuOptions.get(i), true);
      flexMenuRadioButtons[i].setActionCommand(menuOptions.get(i));

      flexMenuButtonGroup.add(flexMenuRadioButtons[i]);
      flexiblePortfolioMenuPanel.add(flexMenuRadioButtons[i]);

    }
    flexiblePortfolioMenuPanel.add(okSelectFlexMenuOption);

    this.add(flexiblePortfolioMenuPanel);

    //set visible
    pack();
    setVisible(true);
  }

  @Override
  public void sellPortfolioInterface() {

    this.remove(flexiblePortfolioMenuPanel);

    //set up new panel
    sellPortfolioPanel = new JPanel();
    sellPortfolioPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    sellPortfolioPanel.setLayout(new BoxLayout(sellPortfolioPanel, BoxLayout.PAGE_AXIS));

    enterDate = new JLabel("Enter transaction date:");
    enterStockTicker = new JLabel("Enter stock ticker:");
    enterStockQuantity = new JLabel("Enter selling quantity");
    JLabel sellHeading = new JLabel("Enter selling details:");

    stockTickerField = new JTextField();
    stockQuantityField = new JTextField();
    stockTickerField.setPreferredSize(new Dimension(75, 20));
    stockQuantityField.setPreferredSize(new Dimension(75, 20));

    sellPortfolioPanel.add(sellHeading);
    showDatePicker(sellPortfolioPanel, enterDate);

    JPanel tickerFields = new JPanel();
    tickerFields.add(enterStockTicker);
    tickerFields.add(stockTickerField);

    JPanel qtyFields = new JPanel();
    qtyFields.add(enterStockQuantity);
    qtyFields.add(stockQuantityField);

    sellPortfolioPanel.add(tickerFields);
    sellPortfolioPanel.add(qtyFields);
    sellPortfolioPanel.add(sellStockButton);
    sellPortfolioPanel.add(goToFlexPortfolioMenu);

    this.add(sellPortfolioPanel);

    //set visible
    pack();
    setVisible(true);

  }


  private String[] convertToStringArray(List<String> list) {
    String[] arr = new String[list.size()];

    for (int i = 0; i < list.size(); i++) {
      arr[i] = list.get(i);
    }

    return arr;
  }

  private void clearSellStockPage() {
    this.remove(sellPortfolioPanel);

    this.add(flexiblePortfolioMenuPanel);
  }

  private void showDefaultPanel() {
    JPanel defaultPanel = new JPanel();
    defaultPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.PAGE_AXIS));

    this.add(defaultPanel);

    //set visible
    pack();
    setVisible(true);

  }

  @Override
  public void showUploadPortfolio() {

    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }

    if (showObservingPortfolioContentPanel != null) {
      this.remove(showObservingPortfolioContentPanel);
    }

    if (showFlexPortfolioContentPanel != null) {
      this.remove(showFlexPortfolioContentPanel);
    }

    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    if (portfolioNameEntryPanel != null) {
      this.remove(portfolioNameEntryPanel);
    }

    JPanel fileSavePanel;
    JLabel fileSaveDisplay;

    fileSavePanel = new JPanel();
    fileSavePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    fileSavePanel.setLayout(new FlowLayout());
    fileSavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    fileSavePanel.add(fileSaveDisplay);

    fileChooser.showSaveDialog(JFrameView.this);
  }

  @Override
  public void enterDateForFlexValueOnDate() {
    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    this.remove(flexiblePortfolioMenuPanel);

    //set up new panel
    enterDatePanel = new JPanel();
    enterDatePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    enterDatePanel.setLayout(new BoxLayout(enterDatePanel, BoxLayout.PAGE_AXIS));

    enterDate = new JLabel("Enter the date:");

    showDatePicker(enterDatePanel, enterDate);
    enterDatePanel.add(getFlexValue);
    enterDatePanel.add(goToFlexPortfolioMenu);


    this.add(enterDatePanel);


    pack();
    setVisible(true);
  }

  @Override
  public void enterDateForFlexCostBasis() {
    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    this.remove(flexiblePortfolioMenuPanel);

    //set up new panel
    enterDatePanel = new JPanel();
    enterDatePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    enterDatePanel.setLayout(new BoxLayout(enterDatePanel, BoxLayout.PAGE_AXIS));

    enterDate = new JLabel("Enter the date:");

    showDatePicker(enterDatePanel, enterDate);
    enterDatePanel.add(getFlexCostBasis);
    enterDatePanel.add(goToFlexPortfolioMenu);


    this.add(enterDatePanel);


    pack();
    setVisible(true);
  }

  @Override
  public void showValueOfFlexPortfolioInPanel(String date, double value) {
    if (result != null) {
      enterDatePanel.remove(result);
    }
    result = new JLabel("The value of portfolio on " + date + " is $" + value);

    enterDatePanel.add(result);
    enterDatePanel.add(goToFlexPortfolioMenu);


    pack();
    setVisible(true);
  }

  @Override
  public void showCostBasisOfFlexPortfolioInPanel(String date, double value) {
    if (result != null) {
      enterDatePanel.remove(result);
    }
    result = new JLabel("The cost-basis of portfolio on " + date + " is $" + value);

    enterDatePanel.add(result);
    enterDatePanel.add(goToFlexPortfolioMenu);


    pack();
    setVisible(true);
  }

  @Override
  public void enterDateForFlexShowComposition() {
    if (enterDatePanel != null) {
      this.remove(enterDatePanel);
    }

    this.remove(flexiblePortfolioMenuPanel);

    //set up new panel
    enterDatePanel = new JPanel();
    enterDatePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    enterDatePanel.setLayout(new BoxLayout(enterDatePanel, BoxLayout.PAGE_AXIS));

    enterDate = new JLabel("Enter the date:");

    showDatePicker(enterDatePanel, enterDate);
    enterDatePanel.add(getShowComposition);
    enterDatePanel.add(goToFlexPortfolioMenu);


    this.add(enterDatePanel);


    pack();
    setVisible(true);
  }

  @Override
  public void showFlexPortfolioContents(Portfolio portfolio) {
    if (showFlexPortfolioContentPanel != null) {
      this.remove(showFlexPortfolioContentPanel);
    }
    int n = portfolio.getStockID().size();

    //set up new panel
    showFlexPortfolioContentPanel = new JPanel();
    showFlexPortfolioContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    showFlexPortfolioContentPanel.setLayout(new
            BoxLayout(showFlexPortfolioContentPanel, BoxLayout.PAGE_AXIS));

    this.remove(enterDatePanel);

    String[] columns = {"Stock Ticker", "Stock Name", "Stock Quantity"};
    String[] tickerArray = convertToStringArray(portfolio.getStockID());
    String[] nameArray = convertToStringArray(portfolio.getStockName());
    String[] quantityArray = convertToStringArray(portfolio.getStockQuantity());

    String[][] data = new String[n][3];
    for (int i = 0; i < n; i++) {
      data[i][0] = tickerArray[i];
      data[i][1] = nameArray[i];
      data[i][2] = quantityArray[i];
    }

    JTable table = new JTable(data, columns);
    table.setBounds(30, 40, 20, 30);
    JScrollPane jp = new JScrollPane(table);


    showFlexPortfolioContentPanel.add(jp);
    showFlexPortfolioContentPanel.add(goToFlexPortfolioMenu);

    this.add(showFlexPortfolioContentPanel);


    pack();
    setVisible(true);

  }

  @Override
  public void goBackToListOfPortfolios() {
    this.remove(flexiblePortfolioMenuPanel);

    this.add(showPortfoliosPanel);


    pack();
    setVisible(true);
  }

  @Override
  public void showInvestFixedAmount() {

    if (investFixedAmountPanel != null) {
      this.remove(investFixedAmountPanel);
    }

    this.remove(showPortfoliosPanel);
    this.remove(flexiblePortfolioMenuPanel);

    //set up new panel
    investFixedAmountPanel = new JPanel();
    investFixedAmountPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    investFixedAmountPanel.setLayout(new BoxLayout(investFixedAmountPanel, BoxLayout.PAGE_AXIS));

    amount = new JLabel("Amount:");
    enterDate = new JLabel("Enter transaction date:");
    setRatios = new JLabel("Enter stock details:");


    amountField = new JTextField();
    JTextField dateField = new JTextField();


    investFixedAmountPanel.add(amount);
    investFixedAmountPanel.add(amountField);

    // add date picker
    showDatePicker(investFixedAmountPanel, enterDate);

    investFixedAmountPanel.add(setRatios);

    this.initial = true;
    addMoreInvStockFields(true);


    investFixedAmountPanel.add(addMoreStockForInvButton);
    investFixedAmountPanel.add(investFixedAmtButton);
    investFixedAmountPanel.add(goToFlexPortfolioMenu);

    this.add(investFixedAmountPanel);

    //set visible
    pack();
    setVisible(true);

  }

  @Override
  public void addMoreInvStockFields(boolean initial) {
    this.initial = initial;
    enterStockTicker = new JLabel("Ticker:");
    enterStockName = new JLabel("Stock name:");
    enterRatio = new JLabel("Ratio(%):");

    stockTickerField = new JTextField();
    stockNameField = new JTextField();
    ratioField = new JTextField();
    stockTickerField.setPreferredSize(new Dimension(75, 20));
    stockNameField.setPreferredSize(new Dimension(75, 20));
    ratioField.setPreferredSize(new Dimension(75, 20));

    JPanel tickerFields = new JPanel();

    tickerFields.add(enterStockTicker);
    tickerFields.add(stockTickerField);
    tickerFields.add(enterStockName);
    tickerFields.add(stockNameField);
    tickerFields.add(enterRatio);
    tickerFields.add(ratioField);
    investFixedAmountPanel.add(tickerFields);
    investFixedAmountPanel.remove(addMoreStockForInvButton);
    investFixedAmountPanel.add(addMoreStockForInvButton);
    investFixedAmountPanel.remove(investFixedAmtButton);
    investFixedAmountPanel.add(investFixedAmtButton);
    investFixedAmountPanel.remove(goToFlexPortfolioMenu);
    investFixedAmountPanel.add(goToFlexPortfolioMenu);

    //set visible
    pack();
    setVisible(true);
  }

  @Override
  public void addMoreSTFInvStockFields(boolean initial) {
    this.initial = initial;
    enterStockTicker = new JLabel("Ticker:");
    enterStockName = new JLabel("Stock name:");
    enterRatio = new JLabel("Ratio(%):");

    stockTickerField = new JTextField();
    stockNameField = new JTextField();
    ratioField = new JTextField();
    stockTickerField.setPreferredSize(new Dimension(75, 20));
    stockNameField.setPreferredSize(new Dimension(75, 20));
    ratioField.setPreferredSize(new Dimension(75, 20));

    JPanel stockDetails = new JPanel();
    stockDetails.setLayout(new FlowLayout());

    stockDetails.add(enterStockTicker);
    stockDetails.add(stockTickerField);
    stockDetails.add(enterStockName);
    stockDetails.add(stockNameField);
    stockDetails.add(enterRatio);
    stockDetails.add(ratioField);

    dollarCostAveragePanel.add(stockDetails);

    dollarCostAveragePanel.remove(addMoreStockForSTFInvButton);
    dollarCostAveragePanel.remove(dollarCostAverageButton);
    dollarCostAveragePanel.add(addMoreStockForSTFInvButton);
    dollarCostAveragePanel.add(dollarCostAverageButton);

    //set visible
    pack();
    setVisible(true);
  }


  @Override
  public void showDollarCostAverage(boolean disableEndDate) {
    JLabel selectInterval = new JLabel("Select interval:");
    JLabel enterNumber = new JLabel("Enter number:");
    JLabel selectDuration = new JLabel("Select duration:");

    if (dollarCostAveragePanel != null) {
      this.remove(dollarCostAveragePanel);
    }
    if (showPortfoliosPanel != null) {
      this.remove(showPortfoliosPanel);
    }
    if (flexiblePortfolioMenuPanel != null) {
      this.remove(flexiblePortfolioMenuPanel);
    }

    if (addNewStockPanel != null) {
      this.remove(addNewStockPanel);
    }


    //set up new panel
    dollarCostAveragePanel = new JPanel();
    dollarCostAveragePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    dollarCostAveragePanel.setLayout(new BoxLayout(dollarCostAveragePanel, BoxLayout.PAGE_AXIS));


    JLabel interval1 = new JLabel("Interval:");
    startDate = new JLabel("Start Date:");
    endDate = new JLabel("End Date:");
    amount = new JLabel("Amount:");
    enterDate = new JLabel("Enter transaction date:");
    setRatios = new JLabel("Set Ratios as %:");

    amountField = new JTextField();
    intervalNumField = new JTextField();
    intervalNumField.setPreferredSize(new Dimension(150, 20));

    intervalDuration = new JComboBox<>();
    intervalDuration.addItem("days");
    intervalDuration.addItem("weeks");
    intervalDuration.addItem("months");
    intervalDuration.addItem("years");
    intervalDuration.setSelectedItem("days");

    dollarCostAveragePanel.add(amount);
    dollarCostAveragePanel.add(amountField);


    showDatePicker(dollarCostAveragePanel, startDate);

    if (!disableEndDate) {
      showEndDatePicker(dollarCostAveragePanel, endDate);
    } else {
      this.disableEndDate = true;
    }
    dollarCostAveragePanel.add(selectOngoing);


    JPanel interval = new JPanel();
    interval.setLayout(new FlowLayout());

    dollarCostAveragePanel.add(selectInterval);
    interval.add(enterNumber);
    interval.add(intervalNumField);
    interval.add(selectDuration);
    interval.add(intervalDuration);

    dollarCostAveragePanel.add(interval);

    this.initial = true;
    addMoreSTFInvStockFields(true);

    dollarCostAveragePanel.add(dollarCostAverageButton);

    this.add(dollarCostAveragePanel);

    //set visible
    pack();
    setVisible(true);
  }

  private void toggleEndDateField(boolean selected) {
    if (selected) {
      dollarCostAveragePanel.remove(endDateRowPanel);
      selectOngoing.setEnabled(false);
    }
    pack();
    setVisible(true);
  }

  @Override
  public void graphAnalysisLine(String portfolioName, String fromDate, String toDate,
                                List<String> timeStamps, List<Integer> height, double increment) {

    JFrameView graphFrame = new JFrameView();
    graphFrame.setSize(1000, 300);
    graphFrame.setLocation(200, 200);
    graphFrame.setTitle("Bar Graph");
    graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    graphFrame.setLayout(new FlowLayout());
    graphFrame.setBackground(Color.cyan);

    // Create data
    DefaultCategoryDataset data = createData(timeStamps, height, increment);
    // Create chart
    JFreeChart lineChart = ChartFactory.createLineChart("Performance of portfolio " +
                    portfolioName + " from " + fromDate + " to " + toDate, // Chart title
            "Time period", // X-Axis Label
            "Value of portfolio ($)", // Y-Axis Label
            data,
            PlotOrientation.HORIZONTAL,
            true,
            true,
            false
    );

    ChartPanel graphAnalysisPanel = new ChartPanel(lineChart);
    graphFrame.setContentPane(graphAnalysisPanel);

    //setVisible
    graphFrame.pack();
    graphFrame.setVisible(true);
  }

  @Override
  public void graphAnalysisBar(String portfolioName, String fromDate, String toDate,
                               List<String> timeStamps, List<Integer> height, double increment) {

    JFrameView graphFrame = new JFrameView();
    graphFrame.setSize(1000, 300);
    graphFrame.setLocation(200, 200);
    graphFrame.setTitle("Bar Graph");
    graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    graphFrame.setLayout(new FlowLayout());
    graphFrame.setBackground(Color.cyan);

    // Create data
    DefaultCategoryDataset data = createData(timeStamps, height, increment);
    // Create chart
    JFreeChart barChart = ChartFactory.createBarChart("Performance of portfolio " +
                    portfolioName + " from " + fromDate + " to " + toDate, // Chart title
            "Time period", // X-Axis Label
            "Value of portfolio ($)", // Y-Axis Label
            data,
            PlotOrientation.HORIZONTAL,
            true,
            true,
            false
    );

    ChartPanel graphAnalysisPanel = new ChartPanel(barChart);
    graphFrame.setContentPane(graphAnalysisPanel);

    //setVisible
    graphFrame.pack();
    graphFrame.setVisible(true);
  }

  private DefaultCategoryDataset createData(List<String> timeStamps, List<Integer> height,
                                            double increment) {

    String legend = "Value of portfolio";

    DefaultCategoryDataset data = new DefaultCategoryDataset();

    for (int i = 1; i < timeStamps.size(); i++) {
      data.addValue(((height.get(i) * increment) + height.get(0)), legend, timeStamps.get(i));
    }

    return data;

  }

  @Override
  public void graphAnalysisDateInput() {
    this.remove(showPortfoliosPanel);
    this.remove(flexiblePortfolioMenuPanel);

    //set up new panel
    graphAnalysisDateInputPanel = new JPanel();
    graphAnalysisDateInputPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    graphAnalysisDateInputPanel.setLayout(new
            BoxLayout(graphAnalysisDateInputPanel, BoxLayout.PAGE_AXIS));

    startDate = new JLabel("Start Date:");
    endDate = new JLabel("End Date:");

    showDatePicker(graphAnalysisDateInputPanel, startDate);
    showEndDatePicker(graphAnalysisDateInputPanel, endDate);
    graphAnalysisDateInputPanel.add(showBarChartButton);
    graphAnalysisDateInputPanel.add(showLineChartButton);
    graphAnalysisDateInputPanel.add(goToFlexPortfolioMenu);

    this.add(graphAnalysisDateInputPanel);

    //set visible
    pack();
    setVisible(true);
  }
}


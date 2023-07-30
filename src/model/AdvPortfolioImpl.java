package model;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static model.Model.convertQuantityInput;

/**
 * This class extends AbstractPortfolio class and implements the unique operations of an
 * advanced portfolio in the Portfolio Management system.
 */
public class AdvPortfolioImpl extends AbstractPortfolio {

  private List<LocalDate> transactionDate;
  private List<Double> buyPrice;
  private List<Double> stockValue;
  StockMarketAPI api = new APIAlphaVantage();

  /**
   * Constructor to create AdvPortfolioImpl objects and setting the portfolio name to given value
   * and other fields to its default values.
   *
   * @param portfolioName the name of the portfolio provided by the user.
   */
  public AdvPortfolioImpl(String portfolioName) {
    this.portfolioName = portfolioName;
    this.stockID = new ArrayList<>();
    this.stockName = new ArrayList<>();
    this.stockQuantity = new ArrayList<>();
    this.transactionDate = new ArrayList<>();
    this.buyPrice = new ArrayList<>();
    this.stockValue = new ArrayList<>();
  }

  @Override
  public List<LocalDate> getTransactionDate() {
    return this.transactionDate;
  }

  @Override
  public List<Double> getBuyPrice() {
    return this.buyPrice;
  }

  @Override
  public List<Double> getStockValue() {
    return this.stockValue;
  }


  @Override
  public void setTransactionDate(LocalDate date) {
    this.transactionDate.add(date);
  }

  @Override
  public void setBuyPrice(double buyPrice) {
    this.buyPrice.add(buyPrice);
  }

  @Override
  public void setStockValue(double value) {
    this.stockValue.add(value);
  }

  @Override
  public void savePortfolio(User currentUser) {
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

      Document doc = dBuilder.newDocument();

      //creating a root element.
      Element rootElement = doc.createElement("portfolios");
      doc.appendChild(rootElement);

      //creating stockID element.


      for (int i = 0; i < this.stockID.size(); i++) {

        Element stockID = doc.createElement("stockID");
        rootElement.appendChild(stockID);

        //setting attribute to stockID
        Attr attr = doc.createAttribute("stockIDData");
        //add stock ID of each stock
        attr.setValue(this.stockID.get(i));
        stockID.setAttributeNode(attr);

        //setting
        Element stockName = doc.createElement("stockName");
        stockName.appendChild(doc.createTextNode(this.stockName.get(i)));
        stockID.appendChild(stockName);

        Element stockQuantity = doc.createElement("stockQuantity");
        stockQuantity.appendChild(doc.createTextNode(this.stockQuantity.get(i)));
        stockID.appendChild(stockQuantity);

        Element transactionDate = doc.createElement("transactionDate");
        transactionDate.appendChild(doc.createTextNode(String.valueOf(
                this.transactionDate.get(i))));
        stockID.appendChild(transactionDate);

        Element stockBuyPrice = doc.createElement("buyPrice");
        stockBuyPrice.appendChild(doc.createTextNode(String.valueOf(this.buyPrice.get(i))));
        stockID.appendChild(stockBuyPrice);

        Element stockValue = doc.createElement("stockValue");
        stockValue.appendChild(doc.createTextNode(String.valueOf(this.stockValue.get(i))));
        stockID.appendChild(stockValue);


      }

      saveAsXMLFile(doc, currentUser, "Adv_");


    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void sellStock(String stockID, double sellQty, String date) {

    List<Integer> indexes = new ArrayList<>();
    List<String> stockIDs = this.getStockID();
    double totalQty = 0;

    //collect indexes of the duplicate entries of stockID.
    for (int i = 0; i < stockIDs.size(); i++) {
      if (stockIDs.get(i).equals(stockID)) {
        indexes.add(i);
      }
    }

    //calculate resultant quantity of positive and negative stocks available.
    for (int index : indexes) {
      if (this.getTransactionDate().get(index).isAfter(LocalDate.parse(date))) {
        continue;
      }
      totalQty += Double.parseDouble(this.getStockQuantity().get(index));
    }

    //check if the selling qty is greater than total qty.
    if (sellQty > totalQty) {
      throw new IllegalArgumentException("Not enough quantity available.");
    }

    //check if the selling date is after the latest trading date.
    if (!LocalDate.parse(date).isAfter(this.getTransactionDate().get(indexes.get(indexes.size()
                    - 1))
            .minusDays(1))) {
      throw new IllegalArgumentException("Selling date cannot be before the latest transaction "
              + "date");
    }

    //add the sell row in the portfolio.
    addStock("stockID", stockID);
    addStock("stockName", this.getStockName().get(indexes.get(0)));
    if (sellQty > 0) {
      addStock("stockSellQuantity", "-" + sellQty);
    } else {
      addStock("stockSellQuantity", "" + sellQty);
    }
    addStock("transactionDate", date);
    addStock("setBuyingPriceAndValue", "");

  }

  @Override
  public void addStock(String key, String value) {
    //add stockID, stock name and ticker.
    addStockHelper(key, value);

    //add stock selling quantity after validating.
    if (key.equals("stockSellQuantity")) {
      if (convertQuantityInput(value) > 0) {
        throw new IllegalArgumentException("Stock selling quantity entered is invalid.");
      }
      this.setStockQuantity(convertQuantityInput(value).toString());
    }

    if (key.equals("transactionDate")) {
      //if date is in the future.
      try {
        if (!LocalDate.now().isAfter(LocalDate.parse(value))) {
          throw new IllegalArgumentException("Date entered is in the future.");
        }
        this.setTransactionDate(LocalDate.parse(value));
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid date entered.");
      }

    }

    if (key.equals("setBuyingPriceAndValue")) {
      //setting the buying price of the stock.
      int index = this.getStockID().size() - 1;

      double buyPrice = api.getPrice(this.getStockID().get(index),
              this.getTransactionDate().get(index));
      this.setBuyPrice(buyPrice);

      this.setStockValue((Double.parseDouble(this.getStockQuantity().get(index)) * buyPrice));
    }

    if (key.equals("setBuyingPriceAndValueForInvFixedAmt")) {
      //setting the buying price of the stock.
      int index = this.getStockID().size() - 1;
      double buyPrice = Double.parseDouble(value);
      this.setBuyPrice(Double.parseDouble(value));

      this.setStockValue((Double.parseDouble(this.getStockQuantity().get(index)) * buyPrice));
    }
  }


}

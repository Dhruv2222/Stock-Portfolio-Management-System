package model;

import org.w3c.dom.Document;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static model.Model.checkTickerExistence;
import static model.Model.convertQuantityInput;

/**
 * This class contains all the common functionality between the various implementations
 * of the Portfolio interface.
 */
public abstract class AbstractPortfolio implements Portfolio {

  protected String portfolioName;
  protected List<String> stockID;
  protected List<String> stockName;
  protected List<String> stockQuantity;

  @Override
  public void setStockID(String stockID) {
    this.stockID.add(stockID);
  }

  @Override
  public void setStockName(String stockName) {
    this.stockName.add(stockName);
  }

  @Override
  public void setStockQuantity(String stockQuantity) {
    this.stockQuantity.add(stockQuantity);
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public List<String> getStockID() {
    return stockID;
  }

  @Override
  public List<String> getStockName() {
    return stockName;
  }

  @Override
  public List<String> getStockQuantity() {
    return stockQuantity;
  }

  @Override
  public List<LocalDate> getTransactionDate() {
    throw new IllegalArgumentException();
  }

  @Override
  public List<Double> getBuyPrice() {
    throw new IllegalArgumentException();
  }

  @Override
  public List<Double> getStockValue() {
    throw new IllegalArgumentException();
  }

  @Override
  public void setTransactionDate(LocalDate date) {
    throw new IllegalArgumentException();
  }

  @Override
  public void setBuyPrice(double buyPrice) {
    throw new IllegalArgumentException();
  }

  @Override
  public void setStockValue(double stockValue) {
    throw new IllegalArgumentException();
  }

  @Override
  public void sellStock(String stockID, double sellQty, String date) {
    throw new IllegalArgumentException();
  }

  @Override
  public void addStock(String key, String value) {
    throw new IllegalArgumentException();
  }

  protected void saveAsXMLFile(Document doc, User currentUser, String pre) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);

      StreamResult result = new StreamResult(new File("./user_account/" + currentUser
              .getUserID() + "/" + pre + portfolioName + ".xml"));
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  protected void addStockHelper(String key, String value) {
    //add stock ID ticker.
    if (key.equals("stockID")) {
      if (checkTickerExistence(value)) {
        this.setStockID(value);


      } else {
        throw new IllegalArgumentException("StockID ticker is invalid.");
      }
    }

    //add stock name.
    if (key.equals("stockName")) {
      this.setStockName(value);
    }

    //add stock quantity after validating.
    if (key.equals("stockQuantity")) {
      if (convertQuantityInput(value) < 0) {
        throw new IllegalArgumentException("Stock quantity entered is invalid.");
      }
      //removed math.floor from here.
      this.setStockQuantity((value));
    }

    //add fractional stock quantity for fixed investment strategy.
    if (key.equals("fractionalStockQuantity")) {
      if (convertQuantityInput(value) < 0) {
        throw new IllegalArgumentException("Stock quantity entered is invalid.");
      }
      this.setStockQuantity(value);
    }
  }
}

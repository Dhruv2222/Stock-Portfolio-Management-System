package model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * This class performs Junit tests on the APIAlphaVantage class to verify its proper functioning.
 */
public class APIAlphaVantageTest {

  StockMarketAPI api = new APIAlphaVantage();

  /**
   * Test if the price value returned is correct.
   * Obtained expected value from Yahoo finance.
   */
  @Test
  public void testAPI_1() {
    api = new APIAlphaVantage();

    //expected price obtained from https://finance.yahoo.com/quote/META/history/?guccounter=
    // 1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAF6imvPqU1U8f3OL
    // HWsrs4_Yh_P0emVd-4xSxHy_YTm_c-AExRpLtgkTQSdQcusnY_4YvuIF1F35zmi9fXhZshykc_19eJSCyntZ
    // bI4k3uGil-CFc8yCyQ1hZ7v1-fKV4AfZ57cmIwWV_3qLltugvsVfRUGrPUrl1PCPoPIwwcBn
    assertEquals("153.13", api.getPrice("META", LocalDate.parse("2022-09-13")) + "");
  }

  /**
   * Test if prices are correctly returned for more dates.
   */
  @Test
  public void testAPI_2() {

    //expected price obtained from https://finance.yahoo.com/quote/META/history/?guccounter=
    // 1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAF6imvPqU1U8f3OL
    // HWsrs4_Yh_P0emVd-4xSxHy_YTm_c-AExRpLtgkTQSdQcusnY_4YvuIF1F35zmi9fXhZshykc_19eJSCyntZ
    // bI4k3uGil-CFc8yCyQ1hZ7v1-fKV4AfZ57cmIwWV_3qLltugvsVfRUGrPUrl1PCPoPIwwcBn
    assertEquals("168.96", api.getPrice("META", LocalDate.parse("2022-09-12")) + "");
    assertEquals("153.13", api.getPrice("META", LocalDate.parse("2022-09-13")) + "");
    assertEquals("151.47", api.getPrice("META", LocalDate.parse("2022-09-14")) + "");
    assertEquals("149.55", api.getPrice("META", LocalDate.parse("2022-09-15")) + "");
    assertEquals("146.29", api.getPrice("META", LocalDate.parse("2022-09-16")) + "");
    assertEquals("148.02", api.getPrice("META", LocalDate.parse("2022-09-19")) + "");
  }

  /**
   * Test if 6 API calls are allowed within 1 minute.
   */
  @Test
  public void testAPI_3() {

    //expected price obtained from https://finance.yahoo.com/
    assertEquals("93.16", api.getPrice("META", LocalDate.parse("2022-10-31")) + "");
    assertEquals("94.66", api.getPrice("GOOG", LocalDate.parse("2022-10-31")) + "");
    assertEquals("227.54", api.getPrice("TSLA", LocalDate.parse("2022-10-31")) + "");
    assertEquals("102.44", api.getPrice("AMZN", LocalDate.parse("2022-10-31")) + "");
    assertEquals("291.88", api.getPrice("NFLX", LocalDate.parse("2022-10-31")) + "");
    assertEquals("34.97", api.getPrice("RIVN", LocalDate.parse("2022-10-31")) + "");
  }

}

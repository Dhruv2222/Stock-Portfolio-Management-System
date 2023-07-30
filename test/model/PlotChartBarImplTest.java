package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * A class to perform JUnit tests on the PlotChartImpl class.
 */
public class PlotChartBarImplTest {

  /**
   * Error is shown if the interval between start and end date is less than 5 days.
   */
  @Test
  public void testGetTimestamp_1() {
    PlotChart chart = new PlotChartBarImpl();
    try {
      assertEquals("[2022-11-09, 2022-11-12]",chart.getTimestamp("2022-11-09",
              "2022-11-12").toString());
    } catch (Exception e) {
      //pass.
    }
  }

  /**
   * List of dates is returned if the interval is less than 30 (but greater than 5).
   */
  @Test
  public void testGetTimestamp_2() {
    PlotChart chart = new PlotChartBarImpl();
    assertEquals("[day, 2022-11-09, 2022-11-10, 2022-11-11, 2022-11-12, 2022-11-13, " +
                    "2022-11-14, " +
                    "2022-11-15, 2022-11-16, 2022-11-17, 2022-11-18, 2022-11-19]",
            chart.getTimestamp("2022-11-09", "2022-11-19").toString());
  }

  /**
   * List of dates is returned if the interval is less than 155 (but greater than 30).
   */
  @Test
  public void testGetTimestamp_3() {
    PlotChart chart = new PlotChartBarImpl();
    assertEquals("[day, 2022-07-09, 2022-07-15, 2022-07-21, 2022-07-27, " +
                    "2022-08-02, 2022-08-08, 2022-08-14, 2022-08-20, " +
                    "2022-08-26, 2022-09-01, 2022-09-07, 2022-09-13, " +
                    "2022-09-19, 2022-09-25, 2022-10-01, 2022-10-07, " +
                    "2022-10-13, 2022-10-19, 2022-10-25, 2022-10-31, 2022-11-06]",
            chart.getTimestamp("2022-07-09", "2022-11-09").toString());
    assertEquals(22, chart.getTimestamp("2022-07-09", "2022-11-09").size());
  }

  /**
   * List of dates is returned if the interval is less than 30*31 (but greater than 155).
   */
  @Test
  public void testGetTimestamp_4() {
    PlotChart chart = new PlotChartBarImpl();
    assertEquals("[month, MAY 2022, JUNE 2022, JULY 2022, AUGUST 2022, SEPTEMBER 2022, " +
                    "OCTOBER 2022]",
            chart.getTimestamp("2022-05-09", "2022-11-09").toString());
    assertEquals(7, chart.getTimestamp("2022-05-09", "2022-11-09").size());
  }

  /**
   * List of dates is returned if the interval is less than 30*12*31 (but greater than 30*31).
   */
  @Test
  public void testGetTimestamp_5() {
    PlotChart chart = new PlotChartBarImpl();
    assertEquals("[year, 2015, 2016, 2017, 2018, 2019, 2020, 2021]",
            chart.getTimestamp("2015-05-09", "2022-11-09").toString());
    assertEquals(8, chart.getTimestamp("2015-05-09", "2022-11-09").size());
  }

  /**
   * List of dates is returned if the interval is less than 30*12*31 (but greater than 30*31).
   */
  @Test
  public void testGetstarcount_1() {
    PlotChart chart = new PlotChartBarImpl();
    List<Double> temp = new ArrayList<>();
    temp.add(2000.54);
    temp.add(1000.54);
    temp.add(2400.54);
    temp.add(2050.74);
    temp.add(2900.84);
    temp.add(4000.54);
    assertEquals("[year, 2015, 2016, 2017, 2018, 2019, 2020, 2021]",
            chart.getStarCount(temp).toString());
  }


}

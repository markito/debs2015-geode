package org.apache.geode.example.debs.loader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author wmarkito
 * @date 1/12/16.
 */
public class Config {

  static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  static final int batchSize = Integer.getInteger("batchSize", 200);
  static final String LOCATOR_HOST = System.getProperty("locatorHost", "localhost");
  static final int LOCATOR_PORT = Integer.getInteger("locatorPort", 10334);
  static final String TAXI_TRIP_REGION = "TaxiTrip";

}

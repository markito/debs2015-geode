package org.apache.geode.example  .debs.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author wmarkito
 * @date 1/12/16.
 */
public class Config {

  public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  public static final int batchSize = Integer.getInteger("batchSize", 200);
  public static final String LOCATOR_HOST = System.getProperty("locatorHost", "localhost");
  public static final int LOCATOR_PORT = Integer.getInteger("locatorPort", 10334);

  public static final String TAXI_TRIP_REGION = "TaxiTrip";
  public static final String TAXI_TRIP_ARCHIVE_REGION = "TaxiTripArchive";
  public static final String FREQUENT_ROUTES_REGION = "FrequentRoute";

  public static final int PAUSE_MILLIS_BETWEEN_BATCH_INSERT = 500;
  public static final String EMPTY_TAXI_REGION = "EmptyTaxi";
  public static final long TIME_WINDOW = TimeUnit.SECONDS.toSeconds(15);
}

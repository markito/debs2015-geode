package org.apache.geode.example.debs.loader;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.SelectResults;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * This is an integration test and will require a locator/server
 * TODO: mock locator/server or use embedded loner for testing
 * @author wmarkito
 * @date 11/3/15.
 */
public class DataLoaderTest {

  private DataLoader loader;
  private final static String fileName= DataLoaderTest.class.getClassLoader().getResource("debs2015-file100.csv").getFile();


  @Before
  public void setUp() throws Exception {
    System.setProperty("locatorPort", String.valueOf(Config.LOCATOR_PORT));
    System.setProperty("locatorHost", "localhost"); //String.valueOf(InetAddress.getLocalHost()));
    loader = new DataLoader(fileName);
  }


  @Test
  public void testLoad() throws Exception {

    ClientCache cache = ClientCacheFactory.getAnyInstance();
    SelectResults results = (SelectResults) cache.getQueryService().newQuery("select * from /TaxiTrip").execute();

    int currentSize = results.size();
    assertFalse(results.isEmpty());

    loader.load();

    results = (SelectResults) cache.getQueryService().newQuery("select * from /TaxiTrip").execute();

    assertFalse(results.isEmpty());
    assertEquals(currentSize + 100, results.size());
  }

  @Test
  public void testProcessWithMultipleBatch() throws Exception {
//    loader.
  }

  public void testProcessWithSingleBatch() throws Exception {
//    loader.process();
  }

  @Test
  public void testConnect() throws Exception {
    ClientCache cache = loader.connect();

    assertFalse(cache.isClosed());
    assertNotNull(cache.getRegion(Config.TAXI_TRIP_REGION));
  }

}
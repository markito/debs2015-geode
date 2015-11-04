import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.SelectResults;

import org.apache.geode.example.debs.model.TaxiTrip;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author wmarkito
 * @date 11/3/15.
 */
public class DataLoaderTest {

  private DataLoader loader;
  private final static String fileName= DataLoaderTest.class.getClassLoader().getResource("debs2015-file100.csv").getFile();

  @Before
  public void setUp() throws Exception {
//    System.setProperty("locatorPort",String.valueOf(10334));
//    System.setProperty("locatorHost", "10.0.0.250"); //String.valueOf(InetAddress.getLocalHost()));
    loader = new DataLoader(fileName, true);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testLoad() throws Exception {
    loader.load();
    ClientCache cache = ClientCacheFactory.getAnyInstance();
    SelectResults results = (SelectResults) cache.getQueryService().newQuery("select * from /TaxiTrip").execute();

    assertFalse(results.isEmpty());
    assertEquals(100, results.size());
  }

  @Test
  public void testProcessWithMultipleBatch() throws Exception {
//    loader.process();
  }

  public void testProcessWithSingleBatch() throws Exception {
//    loader.process();
  }

  @Test
  public void testConnect() throws Exception {
    loader = new DataLoader(fileName);
    ClientCache cache = loader.connect();

    assertFalse(cache.isClosed());
  }

  @Test
  public void testParseLine() throws Exception {
    final String _exampleLine = "5EE2C4D3BF57BDB455E74B03B89E43A7,E96EF8F6E6122591F9465376043B946D,2013-01-01 00:00:09,2013-01-01 00:00:36,26,0.10,-73.992210,40.725124,-73.991646,40.726658,CSH,2.50,0.50,0.50,0.00,0.00,3.50";
    TaxiTrip trip = loader.parseLine(_exampleLine.split(","));
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    assertEquals(trip.getMedallion(), "5EE2C4D3BF57BDB455E74B03B89E43A7");
    assertEquals(trip.getPickup_datetime(), dateFormat.parse("2013-01-01 00:00:09"));
    assertEquals(trip.getDropoff_datetime(), dateFormat.parse("2013-01-01 00:00:36"));
    assertEquals(trip.getTotal_amount(), new BigDecimal(3.50));
  }

}
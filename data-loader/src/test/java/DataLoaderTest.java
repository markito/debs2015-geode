import org.apache.geode.example.debs.model.TaxiTrip;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static junit.framework.Assert.assertEquals;

/**
 * DataLoader Tester.
 *
 * @author markito
 * @since <pre>Oct 16, 2015</pre>
 * @version 1.0
 */

public class DataLoaderTest {

  private final String _exampleLine = "5EE2C4D3BF57BDB455E74B03B89E43A7,E96EF8F6E6122591F9465376043B946D,2013-01-01 00:00:09,2013-01-01 00:00:36,26,0.10,-73.992210,40.725124,-73.991646,40.726658,CSH,2.50,0.50,0.50,0.00,0.00,3.50";
  private final static String fileName= DataLoaderTest.class.getClassLoader().getResource("debs2015-file100.csv").getFile();

  DataLoader loader;

  @Before
  public void before() throws Exception {
    loader = new DataLoader(fileName);
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: load()
   */
  @Test
  public void testLoad() throws Exception {
  }

  /**
   *
   * Method: connect()
   *
   */
  @Test
  public void testConnect() throws Exception {
    //TODO: Test goes here...
  }


  @Test
  public void testProcess() throws Exception {
    loader.load();
    assertEquals(0, loader.queueSize());
  }


  /**
   * Method: parseLine(final String[] line)
   */
  @Test
  public void testParseLine() throws Exception {
    TaxiTrip trip = loader.parseLine(_exampleLine.split(","));
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    assertEquals(trip.getMedallion(), "5EE2C4D3BF57BDB455E74B03B89E43A7");
    assertEquals(trip.getPickup_datetime(), dateFormat.parse("2013-01-01 00:00:09"));
    assertEquals(trip.getDropoff_datetime(), dateFormat.parse("2013-01-01 00:00:36"));
    assertEquals(trip.getTotal_amount(), new BigDecimal(3.50));
  }

}
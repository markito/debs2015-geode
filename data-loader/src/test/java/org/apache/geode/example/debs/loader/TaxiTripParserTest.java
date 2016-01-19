package org.apache.geode.example.debs.loader;

import org.apache.geode.example.debs.model.TaxiTrip;
import org.apache.hadoop.hbase.thrift.generated.IllegalArgument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * @author wmarkito
 * @date 1/17/16.
 */
public class TaxiTripParserTest {

  TaxiTripParser parser;
  TaxiTrip taxiTrip;
  final String _exampleLine = "5EE2C4D3BF57BDB455E74B03B89E43A7,E96EF8F6E6122591F9465376043B946D,2013-01-01 00:00:09,2013-01-01 00:00:36,26,0.10,-73.992210,40.725124,-73.991646,40.726658,CSH,2.50,0.50,0.50,0.00,0.00,3.50";
  final String _invalidLineWithFewerFields = "E96EF8F6E6122591F9465376043B946D,2013-01-01 00:00:09,2013-01-01 00:00:36,26,0.10,-73.992210,40.725124,-73.991646";
  final String _invalidContentLine = "5EE2C4D3BF57BDB455E74B03B89E43A7,E96EF8F6E6122591F9465376043B946D,2013-01 00:00:09,2013-01-01 00,26,0.10,-73.992210,40.725124,-73.991646,40.726658,CSH,2.50,0.50,0.50,0.00,0.00,3.50";

  @Before
  public void setUp() throws Exception {
    parser = new TaxiTripParser();
  }

  @Test
  public void testParseLine() throws Exception {
    taxiTrip = parser.parseLine(_exampleLine.split(","));

    assertNotNull(taxiTrip);
    assertEquals(taxiTrip.getMedallion(), "5EE2C4D3BF57BDB455E74B03B89E43A7");
    assertEquals(taxiTrip.getTotal_amount(), new BigDecimal(3.50));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseInvalidLineWithFewerFields() throws Exception {
    taxiTrip = parser.parseLine(_invalidLineWithFewerFields.split(","));
    assertNull(taxiTrip);
  }

  @Test(expected = ParseException.class)
  public void testParseInvalidContent() throws Exception {
    taxiTrip = parser.parseLine(_invalidContentLine.split(","));
    assertNull(taxiTrip);
  }

  @Test
  public void testParseLineDateFormat() throws Exception {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    taxiTrip = parser.parseLine(_exampleLine.split(","));

    assertNotNull(taxiTrip);
    assertEquals(taxiTrip.getPickup_datetime(), dateFormat.parse("2013-01-01 00:00:09"));
    assertEquals(taxiTrip.getDropoff_datetime(), dateFormat.parse("2013-01-01 00:00:36"));
  }

}
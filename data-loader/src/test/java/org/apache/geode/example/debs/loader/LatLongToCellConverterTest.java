package org.apache.geode.example.debs.loader;

import org.apache.geode.example.debs.model.Cell;
import org.apache.geode.example.debs.model.TaxiTrip;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class LatLongToCellConverterTest {

  double GIVEN_LAT = 41.474937;
  double GIVEN_LONG = -74.913585;
  double LAT_DELTA = 0.004491556;
  double LONG_DELTA = 0.005986;

  final String _exampleLine = "5EE2C4D3BF57BDB455E74B03B89E43A7,E96EF8F6E6122591F9465376043B946D,2013-01-01 00:00:09,2013-01-01 00:00:36,26,0.10,-73.992210,40.725124,-73.991646,40.726658,CSH,2.50,0.50,0.50,0.00,0.00,3.50";

  LatLongToCellConverter converter;

  @Before
  public void setUp() {
    converter = new LatLongToCellConverter();
  }

  @Test
  public void givenLineExample() {
    String[] line = _exampleLine.split(",");

    TaxiTrip trip = new TaxiTrip();
    trip.setMedallion(line[0]);
    trip.setHack_license(line[1]);
    trip.setTrip_time_in_secs(Double.valueOf(line[4]));
    trip.setTrip_distance(Double.valueOf(line[5]));
    trip.setPickup_longitude(Double.valueOf(line[6]));
    trip.setPickup_latitude(Double.valueOf(line[7]));
    trip.setDropoff_longitude(Double.valueOf(line[8]));
    trip.setDropoff_latitude(Double.valueOf(line[9]));
    trip.setPayment_type(line[10]);
    trip.setFare_amount(BigDecimal.valueOf(Double.valueOf(line[11])));
    trip.setSurcharge(BigDecimal.valueOf(Double.valueOf(line[12])));
    trip.setMta_tax(BigDecimal.valueOf(Double.valueOf(line[13])));
    trip.setTip_amount(BigDecimal.valueOf(Double.valueOf(line[14])));
    trip.setTolls_amount(BigDecimal.valueOf(Double.valueOf(line[15])));
    trip.setTotal_amount(BigDecimal.valueOf(Double.valueOf(line[16])));

    //41.474937, -74.913585
    Cell cell = converter.getCell(trip.getDropoff_latitude(), trip.getDropoff_longitude());

    assertEquals(cell.getX(), 168);
    assertEquals(cell.getY(), 155);
  }

  @Test
  public void givenLatLongReturnsCell1() {
    Cell cell = converter.getCell(GIVEN_LAT, GIVEN_LONG);
    assertEquals(1, cell.getX());
    assertEquals(1, cell.getY());
  }
  @Test
  public void givenLatLongReturnsCell10() {
    System.out.println(+GIVEN_LONG+" "+(GIVEN_LONG+(LONG_DELTA*10)));
    Cell cell = converter.getCell(GIVEN_LAT-(LAT_DELTA*10), GIVEN_LONG+(LONG_DELTA*10));
    assertEquals(11, cell.getX());
    assertEquals(11, cell.getY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLatThrowsException() throws Exception {
    double lat = GIVEN_LAT + LAT_DELTA;
    converter.getCell(lat, GIVEN_LONG);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLatThrowsException2() throws Exception {
    double lat = GIVEN_LAT + (LAT_DELTA*300);
    converter.getCell(lat, GIVEN_LONG);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLongThrowsException() throws Exception {
    double longitude = GIVEN_LONG - LONG_DELTA;
    converter.getCell(GIVEN_LAT, longitude);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLongThrowsException2() throws Exception {
    double longitude = GIVEN_LONG + (LONG_DELTA*300);
    converter.getCell(GIVEN_LAT, longitude);
  }
}
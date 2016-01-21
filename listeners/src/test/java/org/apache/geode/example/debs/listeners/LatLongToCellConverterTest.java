package org.apache.geode.example.debs.listeners;

import org.apache.geode.example.debs.model.Cell;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class LatLongToCellConverterTest {

  double GIVEN_LAT = 41.474937;
  double GIVEN_LONG = -74.913585;
  double LAT_DELTA = 0.004491556;
  double LONG_DELTA = 0.005986;
  @Test
  public void givenLatLongReturnsCell1() {
    LatLongToCellConverter converter = new LatLongToCellConverter();
    Cell cell = converter.getCell(GIVEN_LAT, GIVEN_LONG);
    assertEquals(1, cell.getX());
    assertEquals(1, cell.getY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLatThrowsException() throws Exception {
    double lat = GIVEN_LAT - LAT_DELTA;
    LatLongToCellConverter converter = new LatLongToCellConverter();
    converter.getCell(lat, GIVEN_LONG);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLatThrowsException2() throws Exception {
    double lat = GIVEN_LAT + (LAT_DELTA*300);
    LatLongToCellConverter converter = new LatLongToCellConverter();
    converter.getCell(lat, GIVEN_LONG);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLongThrowsException() throws Exception {
    double longitude = GIVEN_LONG - LONG_DELTA;
    LatLongToCellConverter converter = new LatLongToCellConverter();
    converter.getCell(GIVEN_LAT, longitude);
  }

  @Test(expected = IllegalArgumentException.class)
  public void outsideLongThrowsException2() throws Exception {
    double longitude = GIVEN_LONG + (LONG_DELTA*300);
    LatLongToCellConverter converter = new LatLongToCellConverter();
    converter.getCell(GIVEN_LAT, longitude);
  }
}
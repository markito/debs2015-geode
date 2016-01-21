package org.apache.geode.example.debs.listeners;

import org.apache.geode.example.debs.model.Cell;

/**
 * Created by sbawaskar on 11/29/15.
 */
public class LatLongToCellConverter {

  private final double latDelta = 0.004491556;
  private final double longDelta = 0.005986;
  private final double START_LATITUDE = 41.474937 - (latDelta/2);
  private final double START_LONGITUDE = -74.913585 - (longDelta/2);
  private final double ENDING_LATITUDE = START_LATITUDE + (latDelta * 300);
  private final double ENDING_LONGITUDE = START_LONGITUDE + (longDelta * 300);

  public Cell getCell(double latitude, double longitude) {
    //add 1 since the co-ordinate system starts at 1,1 not 0,0
    verifyCellLocation(latitude, longitude);
    int x = (int) ((latitude - START_LATITUDE) / latDelta) + 1;
    int y = (int) ((longitude - START_LONGITUDE) / longDelta) + 1;

    return new Cell(x, y);
  }

  /**
   * verify that the cell lies between 1 and 300 for both lat and long
   */
  private void verifyCellLocation(double latitude, double longitude) {
    if (latitude < START_LATITUDE || latitude > ENDING_LATITUDE) {
      throw new IllegalArgumentException();
    }
    if (longitude < START_LONGITUDE || longitude > ENDING_LONGITUDE) {
      throw new IllegalArgumentException();
    }
  }
}

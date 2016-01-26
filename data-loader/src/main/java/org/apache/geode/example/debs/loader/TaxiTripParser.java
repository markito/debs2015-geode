package org.apache.geode.example.debs.loader;

import org.apache.geode.example.debs.config.Config;
import org.apache.geode.example.debs.model.TaxiTrip;

import java.math.BigDecimal;
import java.text.ParseException;

public class TaxiTripParser {

  private final int fieldNumber = 17;

  public TaxiTripParser() {
  }

  /**
   * Parse line from csv
   *
   * @return TaxiTrip
   */
  public TaxiTrip parseLine(final String[] line) throws ParseException {
    if (line.length == fieldNumber) {
      try {
        TaxiTrip trip = new TaxiTrip();
        trip.setMedallion(line[0]);
        trip.setHack_license(line[1]);
        trip.setPickup_datetime(Config.dateFormat.parse(line[2]));
        trip.setDropoff_datetime(Config.dateFormat.parse(line[3]));
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

        return trip;

      } catch (NumberFormatException ex) {
        // TODO: -1 ?
        throw new ParseException(ex.getMessage(), -1);
      }
    } else {
      throw new IllegalArgumentException(String.format("Line does not contain minimal number of fields. Found {%d}. Required {%d}", line.length, fieldNumber) );
    }

  }
}
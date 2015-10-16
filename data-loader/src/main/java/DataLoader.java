import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

import org.apache.geode.example.debs.model.TaxiTrip;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author wmarkito
 * @date 10/16/15.
 */
public class DataLoader {

  private static final String fileLocation = "/Users/wmarkito/Pivotal/ASF/samples/debs2015-geode/data/debs2015-file100.csv";

  public static void main(String[] args) {
    ClientCacheFactory factory = new ClientCacheFactory().setPdxPersistent(true);
    ClientCache clientCache = factory.create();

    ArrayList<String[]> lines = new ArrayList<>();

    try {
      Files.lines(Paths.get(fileLocation)).forEach(line  -> lines.add((line.split(",")) ));
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      for (String[] line : lines) {
        System.out.println(parseLine(line));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }

  private static TaxiTrip parseLine(final String[] line) throws ParseException {

//    medallion	an md5sum of the identifier of the taxi - vehicle bound
//    hack_license	an md5sum of the identifier for the taxi license
//    pickup_datetime	time when the passenger(s) were picked up
//    dropoff_datetime	time when the passenger(s) were dropped off
//    trip_time_in_secs	duration of the trip
//    trip_distance	trip distance in miles
//    pickup_longitude	longitude coordinate of the pickup location
//    pickup_latitude	latitude coordinate of the pickup location
//    dropoff_longitude	longitude coordinate of the drop-off location
//    dropoff_latitude	latitude coordinate of the drop-off location
//    payment_type	the payment method - credit card or cash
//    fare_amount	fare amount in dollars
//    surcharge	surcharge in dollars
//    mta_tax	tax in dollars
//    tip_amount	tip in dollars
//    tolls_amount	bridge and tunnel tolls in dollars
//    total_amount	total paid amount in dollars


    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  TaxiTrip trip = new TaxiTrip();
    trip.setMedallion(line[0]);
    trip.setHack_license(line[1]);
    trip.setPickup_datetime(dateFormat.parse(line[2]));
    trip.setDropoff_datetime(dateFormat.parse(line[3]));
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

//    TaxiTrip trip = new TaxiTrip(line[0], line[1],line[2], line[3], line[4], line[5], line[6], line[7])
    return trip;
  }


}

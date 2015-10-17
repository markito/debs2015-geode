import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.pdx.ReflectionBasedAutoSerializer;

import org.apache.geode.example.debs.model.TaxiTrip;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wmarkito
 * @date 10/16/15.
 */
public class DataLoader {

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private String fileLocation;
  private final int batchSize = 10;
  private Map<String, TaxiTrip> batchMap  = new HashMap<>();

  private ClientCache clientCache;
  private Region<String, TaxiTrip> taxiTripRegion;

  public DataLoader(final String fileLocation) {
    this.fileLocation = fileLocation;
    this.clientCache = connect();
    this.taxiTripRegion = clientCache.<String,TaxiTrip>createClientRegionFactory(ClientRegionShortcut.PROXY).create("taxiTripRegion");
  }

  public long queueSize() {
    return batchMap.size();
  }

  public void load() {
    try {
      Files.lines(Paths.get(fileLocation)).forEach((line) -> process(line.split(",")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param line
   */
  public void process(final String[] line) {
    try {
      TaxiTrip trip = parseLine(line);
      batchMap.put(trip.getMedallion(), trip);

      if (queueSize() % batchSize == 0)  {
        taxiTripRegion.putAll(batchMap);
        batchMap.clear();
      }

    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
  /**
   * Connect to a Geode locator and sets serialization to model package
   * @return ClientCache
   */
  public ClientCache connect() {

    ClientCacheFactory factory = new ClientCacheFactory()
            .addPoolLocator("localhost", 10334)
            .setPdxSerializer(new ReflectionBasedAutoSerializer("org.apache.geode.example.debs.model.*"))
            .setPdxPersistent(true);

    return factory.create();
  }

  /**
   * Parse line from csv
   * @param line
   * @return TaxiTrip
   * @throws ParseException
   */
  public TaxiTrip parseLine(final String[] line) throws ParseException {

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

    return trip;
  }

}

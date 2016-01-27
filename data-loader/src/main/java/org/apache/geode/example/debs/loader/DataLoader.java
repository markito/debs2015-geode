package org.apache.geode.example.debs.loader;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.TypeMismatchException;
import com.gemstone.gemfire.pdx.ReflectionBasedAutoSerializer;

import org.apache.geode.example.debs.config.Config;
import org.apache.geode.example.debs.model.TaxiTrip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * @author wmarkito
 * @date 10/16/15.
 */
public class DataLoader {

  private static final Logger logger = Logger.getLogger(DataLoader.class.getName());
  private final TaxiTripParser taxiTripParser = new TaxiTripParser();
  private final String fileLocation;
  private Stats stats;
  private Map<String, TaxiTrip> batchMap = new HashMap<>();
  private ClientCache clientCache;
  private Region<String, TaxiTrip> taxiTripRegion;


  public DataLoader(final String fileLocation) {
    this.fileLocation = fileLocation;
    initialize();
  }

  public DataLoader(final String fileLocation, ClientCache clientCache) {
    this(fileLocation);
    this.clientCache = clientCache;
  }

  public Region<String, TaxiTrip> createTaxiTripRegion() {
    return clientCache.<String, TaxiTrip>createClientRegionFactory(ClientRegionShortcut.PROXY).create(Config.TAXI_TRIP_REGION);
  }

  public void initialize() {
    this.clientCache = this.connect();
    this.taxiTripRegion = createTaxiTripRegion();
    this.stats = new Stats();
  }

  public long queueSize() {
    return batchMap.size();
  }

  public void load() throws IOException {
    if (Files.exists(Paths.get(fileLocation))) {
      logger.info("Loading file...");
      try (Stream<String> stream = Files.lines(Paths.get(fileLocation))) {
        stream.forEach((line) -> process(line));
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        // last batch
        processBatch();
      }
    } else {
      throw new IOException(String.format("File not found: %s", fileLocation));
    }
  }

  /**
   *
   * @param line
   */
  public void process(final String line) {

    try {
      TaxiTrip trip = taxiTripParser.parseLine(line.split(","));
      batchMap.put(trip.getMedallion() + trip.getPickup_datetime(), trip);

    } catch (ParseException | NumberFormatException e) {
      getStats().incrementError();
      logger.log(Level.SEVERE,
              String.format("%s\n Line:%s - Error #:%d",
                      e.getMessage(),
                      line,
                      getStats().getErrorCount()));
    }

    if (queueSize() % Config.batchSize == 0) {
      processBatch();
    }
  }

  private void processBatch() {
    if (batchMap.size() > 0) {
      taxiTripRegion.putAll(batchMap);
      logger.fine(String.format("Batch processed. #%d", getStats().getBatchCount()));

      getStats().incrementCounter(batchMap.size());
      batchMap.clear();
    }
  }

  /**
   * Connect to a Geode locator and sets serialization to model package
   *
   * @return ClientCache
   */
  public ClientCache connect() {
    if (clientCache != null)
      return clientCache;

    this.clientCache = new ClientCacheFactory()
            .addPoolLocator(Config.LOCATOR_HOST, Config.LOCATOR_PORT)
            .setPdxSerializer(new ReflectionBasedAutoSerializer("org.apache.geode.example.debs.*"))
            .setPdxReadSerialized(true)
            .setPdxPersistent(true)
            .create();

    return clientCache;
  }

  public static void main(String[] args) throws IOException {

    DataLoader loader = new DataLoader("/Users/wmarkito/Pivotal/ASF/samples/debs2015-geode/data/debs2015-file100.csv");

//    long start= System.nanoTime();
    loader.load();

    ClientCache clientCache = loader.connect();

    QueryService queryService = clientCache.getQueryService();

    Query newQuery = queryService.newQuery("select t.pickup_cell, t.dropoff_cell from /TaxiTrip t");

    try {
      System.out.println(newQuery.execute());

    } catch (FunctionDomainException e) {
      e.printStackTrace();
    } catch (TypeMismatchException e) {
      e.printStackTrace();
    } catch (NameResolutionException e) {
      e.printStackTrace();
    } catch (QueryInvocationTargetException e) {
      e.printStackTrace();
    }
//

////
//    long end = System.nanoTime();
//    long timeSpent = end-start;
//
//    logger.info(String.format("[Errors: %d] - [Total time: %s ms]"
//            ,loader.getErrorCount()
//            ,TimeUnit.NANOSECONDS.toMillis(timeSpent))
//    );

  }

  public Stats getStats() {
    return stats;
  }

  public static class Stats {
    protected LongAdder batchCount = new LongAdder();
    protected LongAdder errorCount = new LongAdder();
    protected LongAdder counter = new LongAdder();

    public long getBatchCount() {
      return batchCount.longValue();
    }

    public long getErrorCount() {
      return errorCount.longValue();
    }

    public void incrementError() {
      errorCount.increment();
    }

    private void incrementCounter(long x) {
      counter.add(x);
      batchCount.increment();
    }

    @Override
    public String toString() {
      return "Stats{" +
              "batchCount=" + batchCount +
              ", errorCount=" + errorCount +
              ", counter=" + counter +
              '}';
    }
  }
}

package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Partition resolver to use pickup cell as the hash key.
 * Created by sbawaskar on 1/28/16.
 */
public class TaxiTripPartitionResolver implements PartitionResolver, Declarable {

  private static final Logger logger = Logger.getLogger(TaxiTripPartitionResolver.class.getName());

  @Override
  public Object getRoutingObject(EntryOperation opDetails) {
    logger.info("PARTITION RESOLVER:" + opDetails.getKey() + " op:" + opDetails);
    return opDetails.getKey();
  }

  @Override
  public String getName() {
    return "TaxiTripPartitionResolver";
  }

  @Override
  public void close() {

  }

  @Override
  public void init(Properties props) {

  }
}

package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryOperation;
import com.gemstone.gemfire.cache.PartitionResolver;
import com.gemstone.gemfire.pdx.PdxInstance;
import org.apache.geode.example.debs.model.TripId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Partition resolver to use pickup cell as the hash key.
 * Created by sbawaskar on 1/28/16.
 */
public class TaxiTripPartitionResolver implements PartitionResolver, Declarable {

  private static final Logger logger = LogManager.getLogger(TaxiTripPartitionResolver.class.getName());

  @Override
  public Object getRoutingObject(EntryOperation opDetails) {
    Object key = opDetails.getKey();
    if (key instanceof TripId) {
      TripId tripId = (TripId) key;
      return tripId.getPickupCell().hashCode();
    } else if (key instanceof PdxInstance) {
      PdxInstance tripId = (PdxInstance) key;
      return tripId.getField("pickupCell").hashCode();
    }
    return key;
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

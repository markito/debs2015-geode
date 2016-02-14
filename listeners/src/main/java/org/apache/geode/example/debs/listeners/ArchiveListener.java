package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

import org.apache.geode.example.debs.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * @author wmarkito
 * @date 2/8/16.
 */
public class ArchiveListener extends CacheListenerAdapter implements Declarable {

  private Region archiveRegion;
  private static final Logger logger = LogManager.getLogger(ArchiveListener.class.getName());

  public ArchiveListener() {
    archiveRegion = CacheFactory.getAnyInstance().getRegion("TaxiTripArchive");
  }

  public void afterCreate(EntryEvent event) {
    logger.debug("######### " + event);
    archiveRegion = CacheFactory.getAnyInstance().getRegion(Config.TAXI_TRIP_ARCHIVE_REGION);
    archiveRegion.put(event.getKey(), event.getSerializedNewValue());
  }

  public void afterUpdate(EntryEvent event) {
    logger.debug("######### " + event);
    archiveRegion = CacheFactory.getAnyInstance().getRegion(Config.TAXI_TRIP_ARCHIVE_REGION);
    archiveRegion.put(event.getKey(), event.getSerializedNewValue());
  }

  public void afterDestroy(EntryEvent event) {
//    logger.debug("######### " + event);
  }

  @Override
  public void init(Properties props) {
    archiveRegion = CacheFactory.getAnyInstance().getRegion(Config.TAXI_TRIP_ARCHIVE_REGION);
  }
}

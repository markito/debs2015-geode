package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/22/16.
 */
public class FrequentRoutesListener extends CacheListenerAdapter {
  private static final Logger logger = Logger.getLogger(FrequentRoutesListener.class.getName());

  @Override
  public void afterUpdate(EntryEvent event) {
    logger.info("Received Event:"+event);
  }
}

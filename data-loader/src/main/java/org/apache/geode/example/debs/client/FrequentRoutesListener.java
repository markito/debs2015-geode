package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.query.CqEvent;
import com.gemstone.gemfire.cache.query.CqListener;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import org.apache.geode.example.debs.model.Route;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/22/16.
 */
public class FrequentRoutesListener implements CqListener {
  private static final Logger logger = Logger.getLogger(FrequentRoutesListener.class.getName());

  @Override
  public void onEvent(CqEvent aCqEvent) {
    Route route = (Route) aCqEvent.getKey();
    logger.info("ROUTE "+route+" changed");
  }

  @Override
  public void onError(CqEvent aCqEvent) {

  }

  @Override
  public void close() {

  }
}

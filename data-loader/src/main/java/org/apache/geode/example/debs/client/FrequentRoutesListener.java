package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/30/16.
 */
public class FrequentRoutesListener extends CacheListenerAdapter {
  private static final Logger logger = Logger.getLogger(FrequentRoutesListener.class.getName());

  private ClientCache clientCache;
  private Query top10Query;

  public FrequentRoutesListener(ClientCache clientCache) {
    this.clientCache = clientCache;
  }

  @Override
  public void afterUpdate(EntryEvent event) {
    try {
      runLocalQuery();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void afterCreate(EntryEvent event) {
    try {
      runLocalQuery();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void registerLocalQuery() {
    QueryService queryService = clientCache.getLocalQueryService();
    //this.top10Query = queryService.newQuery("select e.key.pickup_cell.x,e.key.pickup_cell.y,e.key.dropoff_cell,e.value.numTrips from /FrequentRoute.entries e ORDER BY e.value.numTrips DESC LIMIT 10");
    this.top10Query = queryService.newQuery("select e.key,e.value from /FrequentRoute.entries e ORDER BY e.value.numTrips DESC LIMIT 10");
  }

  private void runLocalQuery() throws Exception {
    logger.info("RUNNING QUERY LOCALLY");
    SelectResults results = (SelectResults) this.top10Query.execute();
    for (Object result : results) {
      logger.info("QUERY RESULT: "+result);
    }
    logger.info("*********************************************");
  }
}

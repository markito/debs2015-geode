package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.query.*;
import org.apache.geode.example.debs.model.Route;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/22/16.
 */
public class FrequentRoutesCQListener implements CqListener {
  private static final Logger logger = Logger.getLogger(FrequentRoutesCQListener.class.getName());

  private ClientCache clientCache;
  private Query top10Query;

  public FrequentRoutesCQListener(ClientCache clientCache) {
    this.clientCache = clientCache;
    registerLocalQuery();
  }

  @Override
  public void onEvent(CqEvent aCqEvent) {
    Route route = (Route) aCqEvent.getKey();
    //logger.info("ROUTE "+route+" changed");
    try {
      runLocalQuery();
    } catch (Exception e) {
      logger.warning(e.getLocalizedMessage());
    }
  }

  @Override
  public void onError(CqEvent aCqEvent) {

  }

  @Override
  public void close() {

  }

  private void registerLocalQuery() {
    QueryService queryService = clientCache.getLocalQueryService();
    this.top10Query = queryService.newQuery("select e.key.pickup_cell,e.key.dropoff_cell,e.value.numTrips from /FrequentRoute.entries e ORDER BY e.value.numTrips DESC LIMIT 10");
  }

  private void runLocalQuery() throws Exception {
    SelectResults results = (SelectResults) this.top10Query.execute();
    for (Object result : results) {
      logger.info("QUERY RESULT: "+result);
    }
  }
}

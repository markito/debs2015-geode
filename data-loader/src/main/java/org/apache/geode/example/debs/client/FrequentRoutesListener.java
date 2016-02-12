package org.apache.geode.example.debs.client;

import com.gemstone.gemfire.LogWriter;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.cache.query.Struct;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;
import org.apache.geode.example.debs.model.Route;
import org.apache.geode.example.debs.model.RouteLog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/30/16.
 */
public class FrequentRoutesListener extends CacheListenerAdapter {
  private LogWriter logger;

  private ClientCache clientCache;
  private Query top10Query;
  private List<Route> top10Routes = new ArrayList<>(10);

  public FrequentRoutesListener(ClientCache clientCache) {
    this.clientCache = clientCache;
    this.logger = this.clientCache.getLogger();
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
    this.top10Query = queryService.newQuery("select e.key,e.value from /FrequentRoute.entries e ORDER BY e.value.numTrips DESC LIMIT 10");
  }

  private void runLocalQuery() throws Exception {
    logger.info("RUNNING QUERY LOCALLY");
    SelectResults results = (SelectResults) this.top10Query.execute();
    if (top10Routes.isEmpty()) {
      printInitialResults(results);
    } else {
      printUpdatedResults(results);
    }
  }

  private void printInitialResults(SelectResults results) {
    List<Route> newTopRoutes = new ArrayList<>(10);
    int i=0;
    logger.info(String.format("%5s %30s %40s", "Rank", "Route", "NumTrips"));
    for (Object result : results) {
      Struct struct = (Struct) result;
      Route r = (Route) struct.get("key");
      RouteLog rl = (RouteLog) struct.get("value");
      logger.info(String.format("%5d %60s %4d", i, r, rl.getNumTrips()));
      newTopRoutes.add(r);
      i++;
    }
    top10Routes = newTopRoutes;
  }

  private void printUpdatedResults(SelectResults results) {
    int i=0;
    List<Route> newTopRoutes = new ArrayList<>(10);
    for (Object result : results) {
      Struct struct = (Struct) result;
      Route r = (Route) struct.get("key");
      RouteLog rl = (RouteLog) struct.get("value");
      if (!r.equals(top10Routes.get(i))) {
        logger.info(String.format("Route %s is now at position %d dropOffTime %s", r, i, rl.getLatestDropoffDatetime()));
      }
      newTopRoutes.add(r);
      i++;
    }
    top10Routes = newTopRoutes;
  }
}

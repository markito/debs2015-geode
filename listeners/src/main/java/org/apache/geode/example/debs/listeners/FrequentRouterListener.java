package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;

import com.gemstone.gemfire.pdx.PdxInstance;

import org.apache.geode.example.debs.config.Config;
import org.apache.geode.example.debs.model.Cell;
import org.apache.geode.example.debs.model.Route;
import org.apache.geode.example.debs.model.RouteLog;
import org.apache.geode.example.debs.model.TaxiLog;
import org.apache.hadoop.hbase.thrift.generated.IllegalArgument;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;

/**
 * create async-event-queue --id=frequentRouterQueue --listener=org.apache.geode.example.debs.listeners.FrequentRouterListener
 * @author wmarkito
 * @date 11/4/15.
 */
public class FrequentRouterListener implements AsyncEventListener, Declarable {

  public static final int TIME_WINDOW = (30 * 60 * 1000);  // 30 minutes

  private static final Logger logger = LogManager.getLogger(FrequentRouterListener.class.getName());

  private Region<Route, RouteLog> routesRegion;

  private Region<Cell, List<TaxiLog>> taxiRegion;

  public FrequentRouterListener() {
    this(CacheFactory.getAnyInstance().getRegion(Config.FREQUENT_ROUTES_REGION));
  }

  public FrequentRouterListener(Region routesRegion) {
    this.routesRegion = routesRegion;
  }

  private Region<Route, RouteLog> getFrequentRouteRegion() {
    if (this.routesRegion == null) {
      this.routesRegion = CacheFactory.getAnyInstance().getRegion(Config.FREQUENT_ROUTES_REGION);
    }
    return this.routesRegion;
  }

  @Override
  public boolean processEvents(List<AsyncEvent> list) {

    this.taxiRegion = CacheFactory.getAnyInstance().getRegion("Taxi");

    for (AsyncEvent ev : list) {
      logger.debug("Processing "+ev);
      PdxInstance taxiTrip = (PdxInstance) ev.getDeserializedValue();

      Cell pickupCell;
      Cell dropoffCell;
      Date pickupDatetime;
      Date dropoffDatetime;

      try {
        pickupCell = getCellFromPdx((PdxInstance) taxiTrip.getField("pickup_cell"));
        dropoffCell = getCellFromPdx((PdxInstance) taxiTrip.getField("dropoff_cell"));
        pickupDatetime = (Date) taxiTrip.getField("pickup_datetime");
        dropoffDatetime = (Date) taxiTrip.getField("dropoff_datetime");

        updateTaxiLog(taxiTrip, dropoffCell, pickupDatetime, dropoffDatetime);

      } catch (RuntimeException e) {
        logger.info("Caught Exception "+e);
        continue;
      }

      Route route = new Route(pickupCell, dropoffCell);
      RouteLog routeLog = getFrequentRouteRegion().get(route);
      if (routeLog == null) {
        insertRouteLog(route, pickupDatetime, dropoffDatetime);
      } else {
        updateRouteLog(route, routeLog, pickupDatetime, dropoffDatetime);
      }
    }

    list.clear();

    return true;
  }

  public void updateTaxiLog(PdxInstance taxiTrip, Cell dropoffCell, Date pickupDatetime, Date dropoffDatetime) {
    String medallion = (String) taxiTrip.getField("medallion");
    List<TaxiLog> taxiLogList = taxiRegion.get(dropoffCell);
    TaxiLog taxiLog = new TaxiLog(dropoffDatetime.getTime(), medallion);

    if (taxiLogList == null) {
      taxiLogList = new ArrayList<>();
      taxiLogList.add(taxiLog);

      taxiRegion.put(dropoffCell, taxiLogList);
    } else {
      int index = taxiLogList.indexOf(taxiLog);

      if (index == -1) {
        taxiLogList.add(taxiLog);
      } else {
        TaxiLog oldTaxiLog = taxiLogList.get(index);
        long lastMinute = TimeUnit.MINUTES.toMinutes(oldTaxiLog.getLastTrip_time());
        long currentMinute = TimeUnit.MINUTES.toMinutes(pickupDatetime.getTime());

        if ((currentMinute - lastMinute) >  TIME_WINDOW ) {
          taxiLogList.add(taxiLog);
        } else {
          taxiLogList.remove(index);
          logger.debug(dropoffCell + " seems profitable...");
        }
      }

      // TODO: while true...
      taxiRegion.replace(dropoffCell, taxiLogList);
    }
  }

  private Cell getCellFromPdx(PdxInstance pdxCell) {
    return new Cell((int)pdxCell.getField("x"), (int)pdxCell.getField("y"));
  }

  private void updateRouteLog(Route route, RouteLog routeLog, Date pickupDatetime, Date dropoffDatetime) {
    while (true) {
      RouteLog newRouteLog = new RouteLog(routeLog.getNumTrips() + 1, pickupDatetime, dropoffDatetime);
      if (getFrequentRouteRegion().replace(route, routeLog, newRouteLog)) {
        break;
      }
      routeLog = getFrequentRouteRegion().get(route);
    }
  }

  private void insertRouteLog(Route route, Date pickupDatetime, Date dropoffDatetime) {
    RouteLog routeLog = new RouteLog(1, pickupDatetime, dropoffDatetime);
    RouteLog oldRouteLog = getFrequentRouteRegion().putIfAbsent(route, routeLog);
    if (oldRouteLog != null) {
      updateRouteLog(route, oldRouteLog, pickupDatetime, dropoffDatetime);
    }
  }

  @Override
  public void close() {

  }

  @Override
  public void init(Properties props) {

  }
}

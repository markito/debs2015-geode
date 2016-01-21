package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.CacheWriter;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.RegionEvent;
import org.apache.geode.example.debs.model.Cell;
import org.apache.geode.example.debs.model.TaxiTrip;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class CellWriter implements CacheWriter<String, TaxiTrip> {

  private static final Logger logger = Logger.getLogger(CellWriter.class.getName());

  private final LatLongToCellConverter latLongToCellConverter;

  public CellWriter(LatLongToCellConverter latLongToCellConverter) {
    this.latLongToCellConverter = latLongToCellConverter;
  }

  public CellWriter() {
    this(new LatLongToCellConverter());
  }

  @Override
  public void beforeUpdate(EntryEvent<String, TaxiTrip> event) throws CacheWriterException {

  }

  @Override
  public void beforeCreate(EntryEvent<String, TaxiTrip> event) throws CacheWriterException {
    TaxiTrip trip = event.getNewValue();
    Cell pickupCell;
    Cell dropoffCell;
    try {
      pickupCell = this.latLongToCellConverter.getCell(trip.getPickup_latitude(), trip.getPickup_longitude());
      dropoffCell = this.latLongToCellConverter.getCell(trip.getDropoff_latitude(), trip.getDropoff_longitude());
    } catch (IllegalArgumentException e) {
      logger.info("Trip "+event.getKey()+" outside study zone");
      throw e;
    }
    trip.setPickup_cell(pickupCell);
    trip.setDropoff_cell(dropoffCell);
  }

  @Override
  public void beforeDestroy(EntryEvent<String, TaxiTrip> event) throws CacheWriterException {

  }

  @Override
  public void beforeRegionDestroy(RegionEvent<String, TaxiTrip> event) throws CacheWriterException {

  }

  @Override
  public void beforeRegionClear(RegionEvent<String, TaxiTrip> event) throws CacheWriterException {

  }

  @Override
  public void close() {

  }
}

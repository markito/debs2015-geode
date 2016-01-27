package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.CacheWriter;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.RegionEvent;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gemstone.gemfire.pdx.WritablePdxInstance;
import org.apache.geode.example.debs.model.Cell;
import org.apache.geode.example.debs.model.TaxiTrip;

import java.util.logging.Logger;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class CellWriter implements CacheWriter<String, PdxInstance> {

  private static final Logger logger = Logger.getLogger(CellWriter.class.getName());

  private final LatLongToCellConverter latLongToCellConverter;

  public CellWriter(LatLongToCellConverter latLongToCellConverter) {
    this.latLongToCellConverter = latLongToCellConverter;
  }

  public CellWriter() {
    this(new LatLongToCellConverter());
  }

  @Override
  public void beforeUpdate(EntryEvent<String, PdxInstance> event) throws CacheWriterException {

  }

  @Override
  public void beforeCreate(EntryEvent<String, PdxInstance> event) throws CacheWriterException {
    PdxInstance trip = event.getNewValue();
    Cell pickupCell;
    Cell dropoffCell;
    try {
      double pickupLatitude = (double) trip.getField("pickup_latitude");
      double pickupLongitude = (double) trip.getField("pickup_longitude");
      double dropoffLatitude = (double) trip.getField("dropoff_latitude");
      double dropoffLongitude = (double) trip.getField("dropoff_longitude");
      pickupCell = this.latLongToCellConverter.getCell(pickupLatitude, pickupLongitude);
      dropoffCell = this.latLongToCellConverter.getCell(dropoffLatitude, dropoffLongitude);
    } catch (IllegalArgumentException e) {
      logger.info("Trip "+event.getKey()+" outside study zone");
      throw e;
    }
    WritablePdxInstance writablePdxInstance = trip.createWriter();
    writablePdxInstance.setField("pickup_cell", pickupCell);
    writablePdxInstance.setField("dropoff_cell", dropoffCell);

  }

  @Override
  public void beforeDestroy(EntryEvent<String, PdxInstance> event) throws CacheWriterException {

  }

  @Override
  public void beforeRegionDestroy(RegionEvent<String, PdxInstance> event) throws CacheWriterException {

  }

  @Override
  public void beforeRegionClear(RegionEvent<String, PdxInstance> event) throws CacheWriterException {

  }

  @Override
  public void close() {

  }
}

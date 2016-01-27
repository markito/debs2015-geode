package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.CacheWriter;
import com.gemstone.gemfire.cache.CacheWriterException;
import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionEvent;
import com.gemstone.gemfire.internal.cache.EntryEventImpl;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gemstone.gemfire.pdx.PdxInstanceFactory;
import com.gemstone.gemfire.pdx.WritablePdxInstance;
import com.gemstone.gemfire.pdx.internal.PdxInstanceFactoryImpl;
import com.gemstone.gemfire.pdx.internal.json.PdxInstanceHelper;


import org.apache.geode.example.debs.model.Cell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class CellWriter implements CacheWriter<String, PdxInstance>, Declarable {

  private static final Logger logger = LogManager.getLogger();

  private final LatLongToCellConverter latLongToCellConverter;

  public CellWriter(LatLongToCellConverter latLongToCellConverter) {
    this.latLongToCellConverter = latLongToCellConverter;
  }

  public CellWriter() {
    this(new LatLongToCellConverter());
  }


  @Override
  public void beforeCreate(EntryEvent<String, PdxInstance> event) throws CacheWriterException {
    calculateCells(event);
  }

  public void calculateCells(EntryEvent<String, PdxInstance> event) {
    PdxInstance pdxInstance = event.getNewValue();
    WritablePdxInstance writablePdxInstance = pdxInstance.createWriter();
    Cell pickupCell;
    Cell dropoffCell;

    try {

      pickupCell = this.latLongToCellConverter.getCell((double) pdxInstance.getField("pickup_latitude"), (double) pdxInstance.getField("pickup_longitude"));
      dropoffCell = this.latLongToCellConverter.getCell((double) pdxInstance.getField("dropoff_latitude"), (double) pdxInstance.getField("dropoff_longitude"));

      logger.info("Pickup:"+ pickupCell);
      logger.info("Dropoff:" + dropoffCell);

//      PdxInstanceFactory pdxInstanceFactory = PdxInstanceFactoryImpl.newCreator("org.apache.geode.example.debs.model.Cell", false);
//      PdxInstance pdxCellInstance = pdxInstanceFactory.create();

      writablePdxInstance.setField("pickup_cell", pickupCell);
      writablePdxInstance.setField("dropoff_cell", dropoffCell);

      EntryEventImpl eei = (EntryEventImpl) event;
      eei.setNewValue(writablePdxInstance);
//      eei.makeSerializedNewValue();

    } catch (IllegalArgumentException e) {
      logger.info(String.format("Trip %s outside study zone", event.getKey()));
      logger.info(String.format("Exception: %s", e.getMessage()));
//      throw e;
    }
  }


  @Override
  public void close() {

  }

  @Override
  public void init(Properties props) {

  }

  @Override
  public void beforeUpdate(EntryEvent<String, PdxInstance> event) throws CacheWriterException {
    calculateCells(event);
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
}

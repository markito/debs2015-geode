package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.EntryEvent;
import org.apache.geode.example.debs.model.Cell;
import org.apache.geode.example.debs.model.TaxiTrip;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class CellWriterTest {

  @Test
  public void testBeforeCreate() throws Exception {
    LatLongToCellConverter mockConverter = mock(LatLongToCellConverter.class);
    CellWriter writer = new CellWriter(mockConverter);
    EntryEvent mockEntryEvent = mock(EntryEvent.class);
    TaxiTrip mockTrip = mock(TaxiTrip.class);
    when(mockEntryEvent.getNewValue()).thenReturn(mockTrip);
    writer.beforeCreate(mockEntryEvent);
    verify(mockTrip, times(1)).getPickup_latitude();
    verify(mockTrip, times(1)).getPickup_longitude();
    verify(mockTrip, times(1)).getDropoff_latitude();
    verify(mockTrip, times(1)).getDropoff_longitude();
    verify(mockTrip, times(1)).setPickup_cell(any(Cell.class));
    verify(mockTrip, times(1)).setDropoff_cell(any(Cell.class));
  }
}
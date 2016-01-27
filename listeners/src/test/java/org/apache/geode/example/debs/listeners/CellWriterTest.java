package org.apache.geode.example.debs.listeners;

import com.gemstone.gemfire.cache.EntryEvent;
import com.gemstone.gemfire.pdx.PdxInstance;
import com.gemstone.gemfire.pdx.WritablePdxInstance;
import org.apache.geode.example.debs.model.Cell;
import org.apache.geode.example.debs.model.TaxiTrip;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by sbawaskar on 1/20/16.
 */
public class CellWriterTest {

  double GIVEN_LAT = 41.474937;
  double GIVEN_LONG = -74.913585;
  @Test
  @Ignore
  public void testBeforeCreate() throws Exception {
    LatLongToCellConverter mockConverter = mock(LatLongToCellConverter.class);
    CellWriter writer = new CellWriter(mockConverter);
    EntryEvent mockEntryEvent = mock(EntryEvent.class);
    PdxInstance mockTrip = mock(PdxInstance.class);
    when(mockEntryEvent.getNewValue()).thenReturn(mockTrip);
    when(mockTrip.getField("pickup_latitude")).thenReturn(GIVEN_LAT);
    when(mockTrip.getField("pickup_longitude")).thenReturn(GIVEN_LONG);
    when(mockTrip.getField("dropoff_latitude")).thenReturn(GIVEN_LAT);
    when(mockTrip.getField("dropoff_longitude")).thenReturn(GIVEN_LONG);

    WritablePdxInstance writableMock = mock(WritablePdxInstance.class);
    when(mockTrip.createWriter()).thenReturn(writableMock);

    writer.beforeCreate(mockEntryEvent);
    verify(mockTrip, times(1)).getField(matches("pickup_latitude"));
    verify(mockTrip, times(1)).getField(matches("pickup_longitude"));
    verify(mockTrip, times(1)).getField(matches("dropoff_latitude"));
    verify(mockTrip, times(1)).getField(matches("dropoff_longitude"));
    verify(mockTrip, times(1)).createWriter();
    verify(writableMock, times(1)).setField(matches("pickup_cell"), any(Cell.class));
    verify(writableMock, times(1)).setField(matches("dropoff_cell"), any(Cell.class));
  }
}
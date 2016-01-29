package org.apache.geode.example.debs.model;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;

import java.util.Date;

/**
 * Created by sbawaskar on 1/28/16.
 */
public class TripId implements PdxSerializable {
  private String medallion;
  private Date pickupDatetime;
  private Cell pickupCell;

  public TripId() {
  }

  public TripId(String medallion, Date pickupDatetime, Cell pickupCell) {
    this.medallion = medallion;
    this.pickupDatetime = pickupDatetime;
    this.pickupCell = pickupCell;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TripId@");
    sb.append(System.identityHashCode(this));
    sb.append(" medallion ").append(medallion);
    sb.append(" pickupDatetime ").append(pickupDatetime);
    sb.append(" pickupCell ").append(pickupCell);
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TripId) {
      TripId other = (TripId) obj;
      if (this.medallion.equals(other.medallion) &&
          this.pickupDatetime.equals(other.pickupDatetime) &&
          this.pickupCell.equals(other.pickupCell)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return medallion.hashCode() + pickupDatetime.hashCode() + pickupCell.hashCode();
  }

  @Override
  public void toData(PdxWriter writer) {
    writer.writeString("medallion", medallion);
    writer.writeDate("pickupDatetime", pickupDatetime);
    writer.writeObject("pickupCell", pickupCell);
  }

  @Override
  public void fromData(PdxReader reader) {
    this.medallion = reader.readString("medallion");
    this.pickupDatetime = reader.readDate("pickupDatetime");
    this.pickupCell = (Cell) reader.readObject("pickupCell");
  }
}

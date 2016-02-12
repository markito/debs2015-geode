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
    this.setMedallion(medallion);
    this.setPickupDatetime(pickupDatetime);
    this.setPickupCell(pickupCell);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TripId@");
    sb.append(System.identityHashCode(this));
    sb.append(" medallion ").append(getMedallion());
    sb.append(" pickupDatetime ").append(getPickupDatetime());
    sb.append(" pickupCell ").append(getPickupCell());
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TripId) {
      TripId other = (TripId) obj;
      if (this.getMedallion().equals(other.getMedallion()) &&
          this.getPickupDatetime().equals(other.getPickupDatetime()) &&
          this.getPickupCell().equals(other.getPickupCell())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getMedallion().hashCode() + getPickupDatetime().hashCode() + getPickupCell().hashCode();
  }

  @Override
  public void toData(PdxWriter writer) {
    writer.writeString("medallion", getMedallion());
    writer.writeDate("pickupDatetime", getPickupDatetime());
    writer.writeObject("pickupCell", getPickupCell());
  }

  @Override
  public void fromData(PdxReader reader) {
    this.setMedallion(reader.readString("medallion"));
    this.setPickupDatetime(reader.readDate("pickupDatetime"));
    this.setPickupCell((Cell) reader.readObject("pickupCell"));
  }

  public String getMedallion() {
    return medallion;
  }

  public void setMedallion(String medallion) {
    this.medallion = medallion;
  }

  public Date getPickupDatetime() {
    return pickupDatetime;
  }

  public void setPickupDatetime(Date pickupDatetime) {
    this.pickupDatetime = pickupDatetime;
  }

  public Cell getPickupCell() {
    return pickupCell;
  }

  public void setPickupCell(Cell pickupCell) {
    this.pickupCell = pickupCell;
  }
}

package org.apache.geode.example.debs.model;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;

/**
 * Created by sbawaskar on 1/27/16.
 */
public class Route implements PdxSerializable {
  private Cell pickup_cell;
  private Cell dropoff_cell;

  public Route() {
  }

  public Route(Cell pickupCell, Cell dropoffCell) {
    this.pickup_cell = pickupCell;
    this.dropoff_cell = dropoffCell;
  }

  @Override
  public void toData(PdxWriter writer) {
    writer.writeObject("pickup_cell", pickup_cell);
    writer.writeObject("dropoff_cell", dropoff_cell);
  }

  @Override
  public void fromData(PdxReader reader) {
    this.pickup_cell = (Cell) reader.readObject("pickup_cell");
    this.dropoff_cell = (Cell) reader.readObject("dropoff_cell");
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Route) {
      Route other = (Route) obj;
      if (this.pickup_cell.equals(other.pickup_cell) &&
          this.dropoff_cell.equals(other.dropoff_cell)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.pickup_cell.hashCode() + this.dropoff_cell.hashCode();
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("Route@");
    sb.append(System.identityHashCode(this));
    sb.append(" pickupCell ").append(pickup_cell.toString());
    sb.append(" dropoffCell ").append(dropoff_cell.toString());
    return sb.toString();
  }
}

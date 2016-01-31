package org.apache.geode.example.debs.model;

import com.gemstone.gemfire.DataSerializable;
import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;

/**
 * Created by sbawaskar on 11/29/15.
 */
public class Cell implements PdxSerializable, Comparable<Cell> {
  private int x;
  private int y;


  public Cell() {

  }

  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("Cell[");
    //sb.append(System.identityHashCode(this));
    sb.append(" x:").append(this.x);
    sb.append(" y:").append(this.y);
    sb.append("]");
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Cell) {
      Cell other = (Cell) obj;
      return this.x == other.x && this.y == other.y;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.x + this.y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public void toData(PdxWriter writer) {
    writer.writeInt("x", x);
    writer.writeInt("y", y);
  }

  @Override
  public void fromData(PdxReader reader) {
    x = reader.readInt("x");
    y = reader.readInt("y");
  }

  @Override
  public int compareTo(Cell o) {
    int x_compare = Integer.compare(this.getX(), o.getX());
    if (x_compare != 0) return x_compare;

    return Integer.compare(this.getY(), o.getY());
  }

}

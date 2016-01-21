package org.apache.geode.example.debs.model;

/**
 * Created by sbawaskar on 11/29/15.
 */
public class Cell {
  private final int x;
  private final int y;

  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("Cell@");
    sb.append(System.identityHashCode(this));
    sb.append(" x:").append(this.x);
    sb.append(" y:").append(this.y);
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

}

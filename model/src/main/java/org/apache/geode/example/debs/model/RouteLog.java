package org.apache.geode.example.debs.model;

import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;

import java.util.Date;

/**
 * Created by sbawaskar on 1/27/16.
 */
public class RouteLog implements PdxSerializable {
  private int numTrips;
  private Date latestPickupDatetime;
  private Date latestDropoffDatetime;

  public RouteLog() {
  }

  public RouteLog(int numTrips, Date latestPickupDatetime, Date latestDropoffDatetime) {
    this.numTrips = numTrips;
    this.latestPickupDatetime = latestPickupDatetime;
    this.latestDropoffDatetime = latestDropoffDatetime;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    sb.append("numTrips: ").append(numTrips);
    sb.append(" latestPickupDatetime:").append(latestPickupDatetime);
    sb.append(" latestDropoffDatetime:").append(latestDropoffDatetime);
    sb.append("]");
    return sb.toString();
  }

  @Override
  public void toData(PdxWriter writer) {
    writer.writeInt("numTrips", getNumTrips());
    writer.writeDate("latestPickupDatetime", getLatestPickupDatetime());
    writer.writeDate("latestDropoffDatetime", getLatestDropoffDatetime());
  }

  @Override
  public void fromData(PdxReader reader) {
    this.setNumTrips(reader.readInt("numTrips"));
    this.setLatestPickupDatetime(reader.readDate("latestPickupDatetime"));
    this.setLatestDropoffDatetime(reader.readDate("latestDropoffDatetime"));
  }

  public int getNumTrips() {
    return numTrips;
  }

  public void setNumTrips(int numTrips) {
    this.numTrips = numTrips;
  }

  public Date getLatestPickupDatetime() {
    return latestPickupDatetime;
  }

  public void setLatestPickupDatetime(Date latestPickupDatetime) {
    this.latestPickupDatetime = latestPickupDatetime;
  }

  public Date getLatestDropoffDatetime() {
    return latestDropoffDatetime;
  }

  public void setLatestDropoffDatetime(Date latestDropoffDatetime) {
    this.latestDropoffDatetime = latestDropoffDatetime;
  }
}

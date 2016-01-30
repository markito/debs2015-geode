package org.apache.geode.example.debs.model;

import com.gemstone.gemfire.cache.Declarable;
import com.gemstone.gemfire.pdx.PdxReader;
import com.gemstone.gemfire.pdx.PdxSerializable;
import com.gemstone.gemfire.pdx.PdxWriter;

import java.util.Properties;

/**
 * @author wmarkito
 * @date 1/29/16.
 */
public class TaxiLog implements PdxSerializable, Declarable {

  long lastTrip_time;
  String medallion;

  public TaxiLog() {

  }

  public TaxiLog(long lastTrip_time, String medallion) {
    this.lastTrip_time = lastTrip_time;
    this.medallion = medallion;
  }

  public long getLastTrip_time() {
    return lastTrip_time;
  }

  public String getMedallion() {
    return medallion;
  }

  @Override
  public String toString() {
    return "TaxiLog{" +
            "lastTrip_time=" + lastTrip_time +
            ", medallion='" + medallion + '\'' +
            '}';
  }

  @Override
  public void init(Properties props) {

  }

  @Override
  public void toData(PdxWriter writer) {

  }

  @Override
  public void fromData(PdxReader reader) {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final TaxiLog taxiLog = (TaxiLog) o;

    if (lastTrip_time != taxiLog.lastTrip_time) return false;
    return medallion.equals(taxiLog.medallion);

  }

  @Override
  public int hashCode() {
    int result = (int) (lastTrip_time ^ (lastTrip_time >>> 32));
    result = 31 * result + medallion.hashCode();
    return result;
  }
}

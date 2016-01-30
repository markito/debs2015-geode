package org.apache.geode.example.debs.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 *
  medallion	an md5sum of the identifier of the taxi - vehicle bound
 hack_license	an md5sum of the identifier for the taxi license
 pickup_datetime	time when the passenger(s) were picked up
 dropoff_datetime	time when the passenger(s) were dropped off
 trip_time_in_secs	duration of the trip
 trip_distance	trip distance in miles
 pickup_longitude	longitude coordinate of the pickup location
 pickup_latitude	latitude coordinate of the pickup location
 dropoff_longitude	longitude coordinate of the drop-off location
 dropoff_latitude	latitude coordinate of the drop-off location
 payment_type	the payment method - credit card or cash
 fare_amount	fare amount in dollars
 surcharge	surcharge in dollars
 mta_tax	tax in dollars
 tip_amount	tip in dollars
 tolls_amount	bridge and tunnel tolls in dollars
 total_amount	total paid amount in dollars

 * @author wmarkito
 * @date 10/16/15.
 * @see http://www.debs2015.org/call-grand-challenge.html
 */
public class TaxiTrip {
  private String medallion;
  private String hack_license;
  private Date pickup_datetime;
  private Date dropoff_datetime;
  private Double trip_time_in_secs;
  private Double trip_distance;
  private Double pickup_longitude;
  private Double  pickup_latitude;
  private Double dropoff_longitude;
  private Double dropoff_latitude;
  private String payment_type;
  private BigDecimal fare_amount;
  private BigDecimal surcharge;
  private BigDecimal mta_tax;
  private BigDecimal tip_amount;
  private BigDecimal tolls_amount;
  private BigDecimal total_amount;
  private Cell pickup_cell;
  private Cell dropoff_cell;

  final Calendar cal = Calendar.getInstance();

  public TaxiTrip() {}

  public TaxiTrip(String medallion, String hack_license, Date pickup_datetime, Date dropoff_datetime, Double trip_time_in_secs, Double trip_distance, Double pickup_longitude, Double pickup_latitude, Double dropoff_longitude, Double dropoff_latitude, String payment_type, BigDecimal fare_amount, BigDecimal surcharge, BigDecimal mta_tax, BigDecimal tip_amount, BigDecimal tolls_amount, BigDecimal total_amount) {
    this.medallion = medallion;
    this.hack_license = hack_license;
    this.pickup_datetime = pickup_datetime;
    this.dropoff_datetime = dropoff_datetime;
    this.trip_time_in_secs = trip_time_in_secs;
    this.trip_distance = trip_distance;
    this.pickup_longitude = pickup_longitude;
    this.pickup_latitude = pickup_latitude;
    this.dropoff_longitude = dropoff_longitude;
    this.dropoff_latitude = dropoff_latitude;
    this.payment_type = payment_type;
    this.fare_amount = fare_amount;
    this.surcharge = surcharge;
    this.mta_tax = mta_tax;
    this.tip_amount = tip_amount;
    this.tolls_amount = tolls_amount;
    this.total_amount = total_amount;
  }

  @Override
  public String toString() {
    return "org.apache.geode.example.debs.model.TaxiTrip{" +
            "medallion='" + medallion + '\'' +
            ", hack_license='" + hack_license + '\'' +
            ", pickup_datetime=" + pickup_datetime +
            ", dropoff_datetime=" + dropoff_datetime +
            ", trip_time_in_secs=" + trip_time_in_secs +
            ", trip_distance=" + trip_distance +
            ", pickup_longitude=" + pickup_longitude +
            ", pickup_latitude=" + pickup_latitude +
            ", dropoff_longitude=" + dropoff_longitude +
            ", dropoff_latitude=" + dropoff_latitude +
            ", payment_type='" + payment_type + '\'' +
            ", fare_amount=" + fare_amount +
            ", surcharge=" + surcharge +
            ", mta_tax=" + mta_tax +
            ", tip_amount=" + tip_amount +
            ", tolls_amount=" + tolls_amount +
            ", total_amount=" + total_amount +
            '}';
  }

  public BigDecimal getFarePlusTip() {
    return fare_amount.add(tip_amount);
  }

  public int pickup_minuteSlot(int window) {
    return getMinuteSlot(window, getPickup_time());
  }

  public int dropoff_minuteSlot(int window) {
    return getMinuteSlot(window, getDropoff_time());
  }

  private int getMinuteSlot(int window, long time) {
    cal.setTimeInMillis(time * 1000L);
    int weekDay = cal.get(Calendar.DAY_OF_WEEK);
    return (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(
            Calendar.MINUTE)) / window;
  }

  public String getMedallion() {
    return medallion;
  }

  public void setMedallion(String medallion) {
    this.medallion = medallion;
  }

  public String getHack_license() {
    return hack_license;
  }

  public void setHack_license(String hack_license) {
    this.hack_license = hack_license;
  }

  public Date getPickup_datetime() {
    return pickup_datetime;
  }

  public long getPickup_time() { return pickup_datetime.getTime(); }

  public void setPickup_datetime(Date pickup_datetime) {
    this.pickup_datetime = pickup_datetime;
  }

  public Date getDropoff_datetime() {
    return dropoff_datetime;
  }

  public long getDropoff_time() {
    return dropoff_datetime.getTime();
  }

  public void setDropoff_datetime(Date dropoff_datetime) {
    this.dropoff_datetime = dropoff_datetime;
  }

  public Double getTrip_time_in_secs() {
    return trip_time_in_secs;
  }

  public void setTrip_time_in_secs(Double trip_time_in_secs) {
    this.trip_time_in_secs = trip_time_in_secs;
  }

  public Double getTrip_distance() {
    return trip_distance;
  }

  public void setTrip_distance(Double trip_distance) {
    this.trip_distance = trip_distance;
  }

  public Double getPickup_longitude() {
    return pickup_longitude;
  }

  public void setPickup_longitude(Double pickup_longitude) {
    this.pickup_longitude = pickup_longitude;
  }

  public Double getPickup_latitude() {
    return pickup_latitude;
  }

  public void setPickup_latitude(Double pickup_latitude) {
    this.pickup_latitude = pickup_latitude;
  }

  public Double getDropoff_longitude() {
    return dropoff_longitude;
  }

  public void setDropoff_longitude(Double dropoff_longitude) {
    this.dropoff_longitude = dropoff_longitude;
  }

  public Double getDropoff_latitude() {
    return dropoff_latitude;
  }

  public void setDropoff_latitude(Double dropoff_latitude) {
    this.dropoff_latitude = dropoff_latitude;
  }

  public String getPayment_type() {
    return payment_type;
  }

  public void setPayment_type(String payment_type) {
    this.payment_type = payment_type;
  }

  public BigDecimal getFare_amount() {
    return fare_amount;
  }

  public void setFare_amount(BigDecimal fare_amount) {
    this.fare_amount = fare_amount;
  }

  public BigDecimal getSurcharge() {
    return surcharge;
  }

  public void setSurcharge(BigDecimal surcharge) {
    this.surcharge = surcharge;
  }

  public BigDecimal getMta_tax() {
    return mta_tax;
  }

  public void setMta_tax(BigDecimal mta_tax) {
    this.mta_tax = mta_tax;
  }

  public BigDecimal getTip_amount() {
    return tip_amount;
  }

  public void setTip_amount(BigDecimal tip_amount) {
    this.tip_amount = tip_amount;
  }

  public BigDecimal getTolls_amount() {
    return tolls_amount;
  }

  public void setTolls_amount(BigDecimal tolls_amount) {
    this.tolls_amount = tolls_amount;
  }

  public BigDecimal getTotal_amount() {
    return total_amount;
  }

  public void setTotal_amount(BigDecimal total_amount) {
    this.total_amount = total_amount;
  }

  public Cell getPickup_cell() {
    return pickup_cell;
  }

  public void setPickup_cell(Cell pickup_cell) {
    this.pickup_cell = pickup_cell;
  }

  public Cell getDropoff_cell() {
    return dropoff_cell;
  }

  public void setDropoff_cell(Cell dropoff_cell) {
    this.dropoff_cell = dropoff_cell;
  }
}

package com.tjmcgrew.omnisplit.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * A class for holding and formatting unix timestamps
 */
public class Time {
  private long value;

  /**
   * Creates a new Time from the given value in milliseconds.
   * 
   * @param time The value for this time.
   */
  public Time (long time) {
    this.setValue(time);
  }

  /**
   * Creates a new Time from the given timestamp in the form hh:mm:ss.xxx
   * 
   * @param The timestamp to parse the value from.
   */
  public Time (String timestamp) {
    this.setValue(timestamp);
  }

  /**
   * Returns a timestamp in the form hh:mm:ss.xxx as a string.
   * 
   * @return The timestamp.
   */
  public String toString() {
    return Time.formatTime(value);
  }

  /**
   * Gets the number of milliseconds this time represents.
   * 
   * @return The number of milliseconds.
   */
  public long getValue() {
    return this.value;
  }

  /**
   * Sets a new value for this time in milliseconds.
   * 
   * @param time The new value.
   */
  public void setValue(long time) {
    this.value = time;
  }

  /**
   * Sets a new value from the given timestamp in the form hh:mm:ss.xxx
   * 
   * @param The timestamp to extract a value from.
   */
  public void setValue(String timestamp) {
    this.value = Time.parseTime(timestamp);
  }

  /**
   * Compares this Time to another Time for equality.
   * 
   * @param time The Time compare this Time to for equality.
   * @return A boolean indicating whether they are equal.
   */
  public boolean equals(Time time) {
    return this.equals(time.getValue());
  }

  /**
   * Compares this time to a number of milliseconds for equality.
   * 
   * @param time The timestamp to compare this Time to for equality.
   * @return A boolean indicating whether they are equal.
   */
  public boolean equals(long time) {
    return this.value == time;
  }

  /**
   * Compares this Time to another Time.
   * 
   * @param time The Time to compare this time to.
   * @return An integer indicating the relationship of the 2 Times.
   */
  public int compareTo(Time time) {
    return this.compareTo(time.getValue());
  }

  /**
   * Compares this time to a number of milliseconds.
   * 
   * @param time The number of milliseconds to compare this Time to.
   * @return An int indicating the relationship of this time to the passed in 
   *   value.
   */
  public int compareTo(long time) {
    if (this.value < time) 
      return -1;
    else if (this.value > time)
      return 1;
    return 0;
  }

  /**
   * Subtracts the passed in Time from this time and returns a new Time with the
   * calculated value.
   * 
   * @param time The Time to subtract from this Time.
   * @return The newly created Time.
   */
  public Time minus(Time time) {
    return this.minus(time.getValue());
  }

  /**
   * Subtracts the passed in number of milliseconds and returns a new Time with
   * the calculated value.
   * 
   * @param time The number of milliseconds to subtract from this time.
   * @return  The newly created Time.
   */
  public Time minus(long time) {
    return new Time(this.value - time);
  }

  /**
   * Adds the passed in Time from this time and returns a new Time with the
   * calculated value.
   * 
   * @param time The Time to subtract from this Time.
   * @return The newly created Time.
   */
  public Time plus(Time time) {
    return this.plus(time.getValue());
  }

  /**
   * Adds the passed in number of milliseconds and returns a new Time with
   * the calculated value.
   * 
   * @param time The number of milliseconds to subtract from this time.
   * @return  The newly created Time.
   */
  public Time plus(long time) {
    return new Time(this.value + time);
  }

  /**
   * Parses a timestamp in the form hh:mm:ss.xxx into a long value representing
   * the number of milliseconds.
   * 
   * @param time The timestamp.
   * @return The number of milliseconds.
   */
  public static long parseTime(String time) {
    Pattern p = null;
    Matcher m = null;
    long hours, minutes, seconds, msec;
    try {
      p = Pattern.compile("(?:(?:(\\d+)\\:)?(\\d+)\\:)?(\\d+).(\\d{3})");
      m = p.matcher(time);
      m.find();
      String hourString = m.group(1);
      String minuteString = m.group(2);
      hours = (hourString != null) ? Long.parseLong(hourString) : 0L;
      minutes = (minuteString != null) ? Long.parseLong(minuteString) : 0L;
      seconds = Long.parseLong(m.group(3));
      msec = Long.parseLong(m.group(4));
      return msec + seconds * 1000 + minutes * 60 * 1000 + hours * 60 * 60 * 1000;
    } catch (IllegalStateException e) {
      System.out.printf("Pattern: %s\n", p.toString());
      System.out.printf("Input:   %s\n", time);
      System.out.printf("Matches: %s\n", m.matches());
      System.out.printf("Groups:  %d\n", m.groupCount());
      System.out.println(e.getMessage());
    }
    return 0L;
  }

  /**
   * Formats a number of milliseconds into a timestamp in the form hh:mm:ss.xxx.
   * 
   * @param time The number of milliseconds.
   * @return The timestamp.
   */
  public static String formatTime(long time) {
    return formatTime(time, false);
  }

  /**
   * Formats a timestamp as hh:mm:ss
   * 
   * @param time The time to format as a long
   * @param forceSign force display of '+' for positive numbers
   * @return A string containing the formatted time.
   */
  public static String formatTime(long time, boolean forceSign) {
    String first = forceSign ? "%+d" : "%d";
    int ms = (int)(time % 1000) / 10; // actually centiseconds...
    int sec = (int)(time / 1000 % 60);
    int min = (int)(time / 60000 % 60);
    int hours = (int)(time / 3600000);
    if (Math.abs(time) < 60000) {
      first = (forceSign && time > 0) ? "+" : "";
      return String.format( first + "%d.%02d", sec, Math.abs(ms));
    } else if (Math.abs(time) < 3600000) {
      return String.format(first + ":%02d.%02d", min, Math.abs(sec), 
        Math.abs(ms));
    } else {
      return String.format(first + "%2d:%02d.%02d", hours, Math.abs(min), 
          Math.abs(sec), Math.abs(ms));
    }
  }

}

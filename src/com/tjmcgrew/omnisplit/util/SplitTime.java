package com.tjmcgrew.omnisplit.util;

import java.util.ArrayList;
import java.util.List;

public class SplitTime {

  protected String name;
  protected long bestSegment;
  protected long bestTime;
  protected long endTime;
  protected long pauseTime;
  protected long startTime;
  protected long runStartTime;
  protected List <SplitListener> listeners;

  /**
   * Creates a new SplitTime with no defined times.
   */
  public SplitTime(String name) {
    this( name, Long.MIN_VALUE, Long.MIN_VALUE);
  }

  /**
   * Creates a new SplitTime with the given best time.
   * 
   * @param bestTime The current best time in milliseconds from the beginning of
   *  the run.
   */
  public SplitTime(String name, long bestTime) {
    this(name, bestTime, Long.MIN_VALUE);
  }

  /**
   * Creates a new SplitTime with the given parameters.
   * 
   * @param bestTime The current best time in milliseconds from the beginning of 
   *  the run.
   * @param bestSegment The current best time in milliseconds for this segment 
   *  of the run.
   */
  public SplitTime(String name, long bestTime, long bestSegment) {
    this.name = name;
    this.bestTime = bestTime;
    this.bestSegment = bestSegment;
    this.startTime = this.pauseTime = this.endTime = Long.MIN_VALUE;
    this.runStartTime = Long.MIN_VALUE;
		this.listeners = new ArrayList();
  }

  /**
   * Sets a new best time in milliseconds for this split.
   * 
   * @param time The new time from the start of the run.
   */
  public void setBestTime(long time) {
    this.bestTime = time;
  }

  public long getBestTime() {
    return this.bestTime;
  }

  /**
   * Sets a new best segment Time for this split
   * 
   * @param time The new time from the start of the split
   */
  public void setBestSegment(long time) {
    this.bestSegment = time;
  }

  /**
	 * Gets the current best segment for this split.
	 * 
	 * @return The current best segment.
	 */
  public long getBestSegment() {
    return this.bestSegment;
  }

  /**
   * Sets the name of this split.
   * 
   * @param name The new name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the current name for this split.
   * 
   * @return The name of this run.
   */
  public String getName() {
    return this.name;
  }

  public long getEndTime() {
    return this.endTime;
  }

  public long getStartTime() {
    return this.startTime;
  }

  /**
   * Sets the start time of the run (used for displaying split time comparisons)
   * 
   * @param runStart The run start time.
   */
  public void setRunStart(long runStart) {
    this.runStartTime = runStart;
  }

  /**
   * Sets the start time of the run (used for displaying split time comparisons)
   * 
   * @return The run start time.
   */
  public long getRunStart() {
    return this.runStartTime;
  }

  /**
   * Gets the time for this split from the beginning of the run.
   * 
   * @return The time for this split.
   */
  public long getRunTime() {
    if (this.isEnded()) {
      return this.endTime - this.runStartTime;
    } else {
      return System.currentTimeMillis() - this.runStartTime;
    }
  }

  /**
   * Shifts all previously set times by this amount (for pause compensation).
   * 
   * @param amount The number of milliseconds to shift by.
   */
  public void shift(long amount) {
    if (this.startTime != Long.MIN_VALUE)
      this.startTime += amount;
      // pretty sure we don't need this
//    if (this.pauseTime != Long.MIN_VALUE)
//      this.pauseTime += amount;
    if (this.endTime != Long.MIN_VALUE)
      this.endTime += amount;
    if (this.runStartTime != Long.MIN_VALUE)
      this.runStartTime += amount;
  }

  /**
   * Starts this split.
   */
  public boolean start() {
    this.startTime = System.currentTimeMillis();
    if (this.runStartTime == Long.MIN_VALUE)
      this.runStartTime = System.currentTimeMillis();
    this.fireEvent(new SplitEvent(SplitEvent.Type.START, this));
    return true;
  }

  /**
   * Ends this split.
   */
  public boolean end() {
    this.endTime = System.currentTimeMillis();
    this.fireEvent(new SplitEvent(SplitEvent.Type.END, this));
    return true;
  }

  /**
   * Pauses the run.
   */
  public boolean pause() {
		if (!this.isPaused()) {
			this.pauseTime = System.currentTimeMillis();
			this.fireEvent(new SplitEvent(SplitEvent.Type.PAUSE, this));
      return true;
		}
    return false;
  }

  /**
   * Resumes the run.
   */
  public boolean resume() {
		if (this.isPaused()) {
//			this.startTime += System.currentTimeMillis() - this.pauseTime;
			this.pauseTime = Long.MIN_VALUE;
			this.fireEvent(new SplitEvent(SplitEvent.Type.RESUME, this));
      return true;
		}
    return false;
  }

  public boolean isActive() {
    return this.startTime != Long.MIN_VALUE && 
        this.endTime == Long.MIN_VALUE;
  }

  public boolean isPaused() {
    return this.pauseTime != Long.MIN_VALUE;
  }

  public boolean isStarted() {
    return this.startTime != Long.MIN_VALUE;
  }

  public boolean isEnded() {
    return this.endTime != Long.MIN_VALUE;
  }

  /**
   * Gets the total segment time for this split.
   * 
   * @return The time for the split.
   */
  public long getSegmentTime() {
    if (this.isPaused()) {
        return this.pauseTime - this.startTime;
    } else if (this.isActive()) {
        return System.currentTimeMillis() - this.startTime;
    } else if (this.isStarted()) {
        return this.endTime - this.startTime;
    } 
    return 0L;
  }

  /**
   * Gets the total time for this split.
   * 
   * @return The time for the split.
   */
  public long getTime() {
    if (this.isPaused()) {
        return this.pauseTime - this.runStartTime;
    } else if (this.isActive()) {
        return System.currentTimeMillis() - this.runStartTime;
    } else if (this.isStarted()) {
        return this.endTime - this.runStartTime;
    } 
    return 0L;
  }
  
  /**
   * Adds a new listener for events occurring in this split.
   * 
   * @param l The new listener to be added.
   * @return true if the operation succeeded.
   */
  public boolean addListener(SplitListener l) {
    return this.listeners.add(l);
  }

  /**
   * Removes a listener from this SplitTime.
   * 
   * @param l The listener to be removed.
   * @return true if the operation succeeded (if the listener was found).
   */
  public boolean removeListener(SplitListener l) {
    return this.listeners.remove(l);
  }

  /**
   * Fires a split event, notifying all listeners of this split.
   * 
   * @param evt The event to be fired
   */
  private void fireEvent(SplitEvent evt) {
    for (SplitListener l:this.listeners) {
      l.splitEvent(evt);
    }
  }

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

  public void update() {
		this.fireEvent(new SplitEvent(SplitEvent.Type.UPDATE, this));
  }
}

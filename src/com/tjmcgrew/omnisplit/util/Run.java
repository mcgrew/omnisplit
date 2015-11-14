package com.tjmcgrew.omnisplit.util;

import java.util.ArrayList;
import java.util.List;

import edu.purdue.bbc.util.DaemonListener;
import edu.purdue.bbc.util.UpdateDaemon;

public class Run extends SplitTime implements DaemonListener {

  private List<SplitTime> splits;
  private int currentSplit;
  private UpdateDaemon updater;
  private int width, height;
  private int attemptCount;
  private Time startDelay;
  private String filename;

  /**
   * Creates a new empty run.
   */
  public Run () {
    this("", new ArrayList(), Long.MIN_VALUE);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param name The name of this run.
   */
  public Run (String name) {
    this(name, new ArrayList(), Long.MIN_VALUE);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param pb The personal best time for this split.
   */
  public Run (long pb) {
    this("", new ArrayList(), pb);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param splits An array of splits for this run.
   */
  public Run(List splits) {
    this("", splits, Long.MIN_VALUE);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param name The name of this run.
   * @param splits An array of splits for this run.
   */
  public Run(String name, List splits) {
    this(name, splits, Long.MIN_VALUE);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param name The name of this run.
   * @param pb The personal best time for this split.
   */
  public Run(String name, long pb) {
    this(name, new ArrayList(), Long.MIN_VALUE);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param splits An array of splits for this run.
   * @param pb The personal best time for this split.
   */
  public Run(List splits, long pb) {
    this("", splits, pb);
  }
    
  /**
   * Creates a new run with the given parameters.
   * 
   * @param name The name of this run.
   * @param splits An array of splits for this run.
   * @param pb The personal best time for this split.
   */
  public Run(String name, List splits, long pb) {
		super(name, pb, Long.MIN_VALUE);
    this.attemptCount = 0;
    this.startDelay = new Time(0L);
    this.splits = splits;
  }

  /**
   * Adds a new split to this run.
   * 
   * @param split The split to be added.
   */
  public void add(SplitTime split) {
    this.splits.add(split);
  }

  /**
   * Remove a split for this run.
   * 
   * @param 
   * @return 
   */
  public boolean remove(SplitTime split) {
    return this.splits.remove(split); 
  }

  /**
   * Starts a new timed run.
   */
  public boolean start() {
    if (this.updater == null) {
      this.updater = UpdateDaemon.create(100);
      this.updater.update(this);
      this.updater.start();
    }
		boolean returnvalue = super.start();
    this.currentSplit = 0;
    this.splits.get(0).start();
    for (SplitTime s : this.splits) {
      s.setRunStart(this.startTime);
    }
    return returnvalue;
  }

  /**
   * Pauses the current run.
   */
  public boolean pause() {
		boolean returnvalue = super.pause();
    this.splits.get(this.currentSplit).pause();
    return returnvalue;
  }

  public boolean resume() {
    long shiftAmount = System.currentTimeMillis() - this.pauseTime;
		if (this.isPaused()) {
			this.shift(shiftAmount);
      for (SplitTime s : this.splits) {
        s.shift(shiftAmount);
      }
		}
    boolean returnvalue = super.resume();
    this.splits.get(this.currentSplit).resume();
    return returnvalue;
  }

  /**
   * Advances to the next split.
   */
  public void next() {
		if (this.isEnded()) {
			return;
		}
    this.splits.get(this.currentSplit).end();
		if (this.currentSplit < this.splits.size()-1) {
			this.currentSplit++;
			this.splits.get(this.currentSplit).start();
		} else {
			this.end();
		}
  }

  public void reset() {
    this.startTime = this.pauseTime = this.endTime = Long.MIN_VALUE;
  }

  public List<SplitTime> getSplits() {
    return this.splits;
  }

  /**
   * Updates any listeners of this run with the new time.
   * 
   * @param 
   * @return 
   */
  public void update() {
		super.update();
    this.splits.get(this.currentSplit).update();
		if (!this.isEnded()) {
			this.updater.update(this);
		}
  }

  /**
   * Changes the window width setting for this run.
   * 
   * @param width The new width setting.
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Gets the current width setting for this run.
   * 
   * @return The current width setting.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Changes the current height setting for this run.
   * 
   * @param height The new height setting.
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Gets the current height setting for this run.
   * 
   * @return The current height setting.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Changes the attempt count.
   * 
   * @param count The new attempt count.
   */
  public void setAttemptCount(int count) {
    this.attemptCount = count;
  }

  /**
   * Gets the current attempt count.
   * 
   * @return The current attempt count.
   */
  public int getAttemptCount() {
    return this.attemptCount;
  }

  /**
   * Sets the start delay for this run in milliseconds.
   * 
   * @param delay The start delay.
   */
  public void setStartDelay(long delay) {
    this.startDelay.setValue(delay);
  }

  /**
   * Gets the start delay in milliseconds.
   * 
   * @return The start delay.
   */
  public Time getStartDelay() {
    return this.startDelay;
  }

  /**
   * Sets the filename for this run.
   * 
   * @param filename The file to use when saving.
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }

  /**
   * Gets the filename for this run.
   * 
   * @return The current filename for this run.
   */
  public String getFilename() {
    return this.filename;
  }
}

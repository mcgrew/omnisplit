package com.tjmcgrew.omnisplit.util;

import java.util.ArrayList;
import java.util.List;

import edu.purdue.bbc.util.DaemonListener;
import edu.purdue.bbc.util.UpdateDaemon;

public class Run extends SplitTime implements DaemonListener {

  private List<SplitTime> splits;
  private int currentSplit;
  private UpdateDaemon updater;

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
    this.splits = splits;
    this.updater = UpdateDaemon.create(100);
    this.updater.update(this);
    this.updater.start();
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
    this.splits.get(this.currentSplit).end();
		if (this.currentSplit < this.splits.size() -1) {
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
    this.updater.update(this);
    this.splits.get(this.currentSplit).update();
  }
}

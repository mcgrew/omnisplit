package com.tjmcgrew.omnisplit.util;

import java.util.ArrayList;
import java.util.List;

import edu.purdue.bbc.util.DaemonListener;
import edu.purdue.bbc.util.UpdateDaemon;

// TODO: Have this class extend SplitTime
public class Run extends SplitTime implements DaemonListener {

  private List<SplitTime> splits;
  private int currentSplit;

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
  public Run(ArrayList splits) {
    this("", splits, Long.MIN_VALUE);
  }

  /**
   * Creates a new run with the given parameters.
   * 
   * @param name The name of this run.
   * @param splits An array of splits for this run.
   */
  public Run(String name, ArrayList splits) {
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
  public Run(ArrayList splits, long pb) {
    this("", splits, pb);
  }
    
  /**
   * Creates a new run with the given parameters.
   * 
   * @param name The name of this run.
   * @param splits An array of splits for this run.
   * @param pb The personal best time for this split.
   */
  public Run(String name, ArrayList splits, long pb) {
		super(name, pb, Long.MIN_VALUE);
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
  public void start() {
		super.start();
    this.currentSplit = 0;
    System.out.println("Run start");
  }

  /**
   * Pauses the current run.
   */
  public void pause() {
		super.pause();
    if (this.pauseTime == Long.MIN_VALUE) {
      this.splits.get(this.currentSplit).pause();
    } else {
      this.reset();
    }
  }

  /**
   * Advances to the next split.
   */
  public void next() {
    this.splits.get(this.currentSplit).end();
		if (this.currentSplit < this.splits.size()) {
			this.currentSplit++;
			this.splits.get(this.currentSplit).start();
		} else {
			this.end();
		}
  }

  public void reset() {
    System.out.println("Reset run");
    this.startTime = this.pauseTime = this.endTime = Long.MIN_VALUE;
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
  }
}

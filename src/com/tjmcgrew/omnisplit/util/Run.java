package com.tjmcgrew.omnisplit.util;

import java.util.ArrayList;
import java.util.List;

import edu.purdue.bbc.util.DaemonListener;
import edu.purdue.bbc.util.UpdateDaemon;

// TODO: Have this class extend SplitTime
public class Run implements DaemonListener {

  private long pb;

  private String name;
  private List<SplitTime> splits;
  private int currentSplit;
  private long startTime;
  private long pauseTime;
  private long endTime;

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
    this.name = name;
    this.splits = splits;
    this.pb = pb;
    this.startTime = this.pauseTime = this.endTime = Long.MIN_VALUE;
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
    this.currentSplit = 0;
    System.out.println("Run start");
  }

  /**
   * Pauses the current run.
   */
  public void pause() {
    if (this.pauseTime == Long.MIN_VALUE) {
      this.pauseTime = System.currentTimeMillis();
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
    this.currentSplit++;
    this.splits.get(this.currentSplit).start();
  }

  public void reset() {
    System.out.println("Reset run");
    this.startTime = this.pauseTime = this.endTime = Long.MIN_VALUE;
  }

  /**
   * Sets the personal best time for this run.
   * 
   * @param pb The new personal best.
   */
  public void setPB(long pb) {
    this.pb = pb;
  }
  
  /**
   * Gets the current personal best time for this run.
   * 
   * @return The current personal best.
   */
  public long getPB() {
    return this.pb;
  }

  /**
   * Sets the name of this run, usually the game title.
   * 
   * @param name The new name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the current name for this run.
   * 
   * @return The name of this run.
   */
  public String setName() {
    return this.name;
  }

  /**
   * Updates any listeners of this run with the new time.
   * 
   * @param 
   * @return 
   */
  public void update() {
    this.splits.get(this.currentSplit).update();
    // fire update event
  }
}

package com.tjmcgrew.omnisplit.util;

import java.util.ArrayList;
import java.util.List;

public class Run {

  private long pb;

  private List<SplitTime> splits;
  private int currentSplit;

  public Run () {
    this(new ArrayList(), Long.MIN_VALUE);
  }

  public Run (long pb) {
    this(new ArrayList(), pb);
  }

  public Run(ArrayList splits) {
    this(splits, Long.MIN_VALUE);
  }

  public Run(ArrayList splits, long pb) {
    this.splits = splits;
    this.pb = pb;
  }

  public void add(SplitTime split) {
    this.splits.add(split);
  }

  public boolean remove(SplitTime split) {
    return this.splits.remove(split); 
  }

  public void start() {
    this.currentSplit = 0;
  }

  public void pause() {
    this.splits.get(this.currentSplit).pause();
  }

  public void next() {
    this.currentSplit++;
  }

  public void setPB(long pb) {
    this.pb = pb;
  }
  
  public long getPB() {
    return this.pb;
  }
}

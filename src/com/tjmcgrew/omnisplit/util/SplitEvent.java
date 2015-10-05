package com.tjmcgrew.omnisplit.util;


public class SplitEvent {

  private SplitEvent.Type type;
  private SplitTime source;

  public static enum Type {
    START, END, PAUSE, RESUME
  }

  public SplitEvent(SplitEvent.Type type, SplitTime source) {
    this.type = type;
    this.source = source;
  }

  public SplitEvent.Type getType() {
    return this.type;
  }

  public SplitTime getSource() {
    return this.source;
  }
}

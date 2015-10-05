package com.tjmcgrew.omnisplit.util;


/**
 * A listener for split events
 */
public interface SplitListener {

  /**
   * Notifies a listener when an event has occurred. 
   * 
   * @param evt The SplitEvent that occurred.
   */
  public void splitEvent(SplitEvent evt);
}


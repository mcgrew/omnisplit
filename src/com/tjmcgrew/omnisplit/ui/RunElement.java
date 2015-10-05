package com.tjmcgrew.omnisplit.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.tjmcgrew.omnisplit.util.Run;

public class RunElement extends JPanel {
  private BestLabel bestLabel;
  private SumOfBestsLabel sobLabel;

  public RunElement (Run run) {
    super(new BorderLayout());
    this.bestLabel = new BestLabel();
    this.sobLabel = new SumOfBestsLabel();
    this.add(this.bestLabel, BorderLayout.CENTER);
    this.add(this.sobLabel, BorderLayout.SOUTH);
  }

  private class BestLabel extends JLabel {

    public BestLabel() {
      super();
    }
  }

  private class SumOfBestsLabel extends JLabel {

    public SumOfBestsLabel() {
      super();
    }
  }
}

package com.tjmcgrew.omnisplit.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tjmcgrew.omnisplit.util.*;


/**
 * A class for displaying a single split time.
 *
 * Colors: 
 *  red = Lost time on this segment and behind.
 *  light red = gained time on that segment, but still behind.
 *  light green = lost time on this segment, but still ahead.
 *  green = gained time on that segment, and ahead.
 *  gold = new best segment time.
 * 
 */
public class SplitPanel extends JPanel implements SplitListener {
  private SplitTime split;
  private JLabel nameLabel;
  private JLabel currentLabel;
  private JLabel bestLabel;
  
  /**
   * Creates a new split element for displaying a single split.
   */
  public SplitPanel (SplitTime split) {
    super(new BorderLayout());
    this.split = split;
    split.addListener(this);
    this.nameLabel = new JLabel(split.getName());
    this.nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
    this.nameLabel.setForeground(Color.WHITE);
    this.nameLabel.setBackground(new Color(30, 30, 30));
    this.nameLabel.setOpaque(true);
    this.currentLabel = new JLabel("");
    this.currentLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    this.currentLabel.setForeground(Color.WHITE);
    this.currentLabel.setBackground(new Color(30, 30, 30));
    this.currentLabel.setOpaque(true);
    this.bestLabel = new JLabel(split.getBestTime().toString());
    this.bestLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
    this.bestLabel.setForeground(Color.WHITE);
    this.bestLabel.setBackground(new Color(30, 30, 30));
    this.bestLabel.setOpaque(true);
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(this.currentLabel, BorderLayout.EAST);
    leftPanel.add(this.nameLabel, BorderLayout.CENTER);
    this.add(this.bestLabel, BorderLayout.EAST);
    this.add(leftPanel, BorderLayout.CENTER);
  }

  public void splitEvent(SplitEvent evt) {
    SplitEvent.Type type = evt.getType();
    SplitTime time = evt.getSource();
    switch(type) {
      case UPDATE:
        if (time.getBestTime().getValue() / 5 * 4 >= time.getTime()) {
          if (!time.isEnded())
            this.currentLabel.setText("");
        } else {
          this.currentLabel.setText(Time.formatTime(
            time.getTime() - time.getBestTime().getValue(), true));
        }
        break;
      case END:
        this.currentLabel.setText(Time.formatTime(
          time.getTime() - time.getBestTime().getValue(), true));
        this.bestLabel.setText(Time.formatTime(time.getRunTime()));
        break;
    }
  }
}

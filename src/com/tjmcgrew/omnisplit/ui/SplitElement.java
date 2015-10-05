package com.tjmcgrew.omnisplit.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tjmcgrew.omnisplit.util.*;


/**
 * A class for displaying a single split time.
 */
public class SplitElement extends JPanel {
  private SplitTime split;
  private JLabel nameLabel;
  private JLabel currentLabel;
  private JLabel bestLabel;
  
  /**
   * Creates a new split element for displaying a single split.
   */
  public SplitElement (SplitTime split) {
    super(new BorderLayout());
    this.split = split;
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
    this.bestLabel = new JLabel(SplitTime.formatTime(split.getBestTime()));
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

}
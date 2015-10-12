package com.tjmcgrew.omnisplit.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tjmcgrew.omnisplit.ui.SplitPanel;
import com.tjmcgrew.omnisplit.util.SplitTime;
import com.tjmcgrew.omnisplit.util.Run;

public class RunPanel extends JPanel {
  private JPanel splitsPanel;
  private List splits = new ArrayList<SplitPanel>();
  private MainTimerLabel mainTimerLabel;
  private SplitPanel bestPanel;
  private SplitPanel sobPanel;
  private Run run;

  public RunPanel (Run run) {
    super(new BorderLayout());
    this.run = run;
    this.bestPanel = new SplitPanel(new SplitTime("Sum of Bests", 30000, Long.MIN_VALUE));
    this.sobPanel = new SplitPanel(new SplitTime("Previous Best", 30000, Long.MIN_VALUE));
    this.mainTimerLabel = new MainTimerLabel();
    this.mainTimerLabel.setOpaque(true);
    this.mainTimerLabel.setBackground(new Color(30, 30, 30));
    this.splitsPanel = new JPanel(new GridLayout(0, 1));
    JPanel bottomPanel = new JPanel(new GridLayout(0, 1));
    JPanel centerPanel = new JPanel(new BorderLayout());

    JLabel spacerLabel = new JLabel("");
    spacerLabel.setOpaque(true);
    spacerLabel.setBackground(new Color(30, 30, 30));
    centerPanel.add(spacerLabel, BorderLayout.CENTER);
    centerPanel.add(mainTimerLabel, BorderLayout.SOUTH);

    bottomPanel.add(this.bestPanel);
    bottomPanel.add(this.sobPanel);

    // temporary stuff
    this.splitsPanel.add(new SplitPanel(new SplitTime("Stage 1",  6000, 6000)));
    this.splitsPanel.add(new SplitPanel(new SplitTime("Stage 2", 12000, 6000)));
    this.splitsPanel.add(new SplitPanel(new SplitTime("Stage 3", 18000, 6000))); 
    this.splitsPanel.add(new SplitPanel(new SplitTime("Stage 4", 24000, 6000))); 
    this.splitsPanel.add(new SplitPanel(new SplitTime("Stage 5", 30000, 6000))); 
    // end temporary stuff

    this.add(this.splitsPanel, BorderLayout.NORTH);
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
  }

  public Run getRun() {
    return this.run;
  }

  private class MainTimerLabel extends JLabel {

    private String format = "<html>" +
      "<span style='font-size:36px;'>%s</span> " +
      "<span style='font-size:24px;'>%s</span>" +
      "</html>";

    public MainTimerLabel () {
      super();
      Font font = this.getFont();
      this.setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
      this.setText(String.format(format, "0:00:00", "00"));
      this.setHorizontalAlignment(JLabel.RIGHT);
      this.setOpaque(true);
      this.setBackground(new Color(30, 30, 30));
      this.setForeground(Color.WHITE);
    }

  }
}

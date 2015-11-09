package com.tjmcgrew.omnisplit.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tjmcgrew.omnisplit.ui.SplitPanel;
import com.tjmcgrew.omnisplit.util.SplitEvent;
import com.tjmcgrew.omnisplit.util.SplitListener;
import com.tjmcgrew.omnisplit.util.SplitTime;
import com.tjmcgrew.omnisplit.util.Run;

public class RunPanel extends JPanel implements SplitListener {
  private JPanel splitsPanel;
  private List splits = new ArrayList<SplitPanel>();
  private MainTimerPanel mainTimerPanel;
  private SplitPanel bestPanel; private SplitPanel sobPanel;
  private Run run;

  public RunPanel (Run run) {
    super(new BorderLayout());
    this.run = run;
    this.bestPanel = new SplitPanel(new SplitTime("Sum of Bests", 30000, Long.MIN_VALUE));
    this.sobPanel = new SplitPanel(new SplitTime("Previous Best", 30000, Long.MIN_VALUE));
    this.mainTimerPanel = new MainTimerPanel();
    this.mainTimerPanel.setOpaque(true);
    this.mainTimerPanel.setBackground(OmnisplitWindow.background);
    this.splitsPanel = new JPanel(new GridLayout(0, 1));
    JPanel bottomPanel = new JPanel(new GridLayout(0, 1));
    JPanel centerPanel = new JPanel(new BorderLayout());
    JPanel topPanel = new JPanel(new BorderLayout());

    JPanel spacerPanel = new JPanel();
    spacerPanel.setOpaque(true);
    spacerPanel.setBackground(OmnisplitWindow.background);
    centerPanel.add(this.splitsPanel, BorderLayout.NORTH);
    centerPanel.add(spacerPanel, BorderLayout.CENTER);
    JScrollPane scrollPane = new JScrollPane(centerPanel);
    scrollPane.setBorder(null);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    topPanel.add(scrollPane, BorderLayout.CENTER);
    topPanel.add(mainTimerPanel, BorderLayout.SOUTH);

    bottomPanel.add(this.bestPanel);
    bottomPanel.add(this.sobPanel);

    for (SplitTime s : run.getSplits()) {
      this.splitsPanel.add(new SplitPanel(s));
    }

//    this.add(this.splitsPanel, BorderLayout.NORTH);
//    this.add(centerPanel, BorderLayout.CENTER);
    this.add(topPanel, BorderLayout.CENTER);
    this.add(bottomPanel, BorderLayout.SOUTH);
    run.addListener(this);
  }

  public Run getRun() {
    return this.run;
  }

  public void splitEvent(SplitEvent evt) {
    SplitEvent.Type type = evt.getType();
    switch(type) {
      case UPDATE:
        this.mainTimerPanel.setTime(evt.getSource().getTime());
        break;
      default:
        break;
    }
  }

  private class MainTimerPanel extends JPanel {
    private JLabel msecLabel;
    private JLabel secLabel;

    public MainTimerPanel () {
      super(new BorderLayout());
      this.msecLabel = new JLabel("00");
      this.secLabel = new JLabel("0:00:00");
      Font font = this.msecLabel.getFont();
      this.secLabel.setFont(new Font(font.getName(), Font.BOLD, 36));
      this.secLabel.setHorizontalAlignment(JLabel.RIGHT);
      this.secLabel.setVerticalAlignment(JLabel.BOTTOM);
      this.secLabel.setOpaque(true);
      this.secLabel.setBackground(OmnisplitWindow.background);
      this.secLabel.setForeground(Color.WHITE);

      this.msecLabel.setFont(new Font(font.getName(), Font.BOLD, 24));
      this.msecLabel.setHorizontalAlignment(JLabel.RIGHT);
      this.msecLabel.setVerticalAlignment(JLabel.BOTTOM);
      this.msecLabel.setOpaque(true);
      this.msecLabel.setBackground(OmnisplitWindow.background);
      this.msecLabel.setForeground(Color.WHITE);

      this.add(this.msecLabel, BorderLayout.EAST);
      this.add(this.secLabel, BorderLayout.CENTER);
      this.setTime(0L);
    }

    public void setTime(long time) {
      String msec = String.format("%02d", time / 10 % 100);
      String sec = String.format("%d:%02d:%02d", time / 3600000 % 60,
          time / 60000 % 60, time / 1000 % 60);
      this.msecLabel.setText(msec);
      this.secLabel.setText(sec);
    }

  }
}

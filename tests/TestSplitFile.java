
import com.tjmcgrew.omnisplit.io.SplitFile;
import com.tjmcgrew.omnisplit.util.Run;
import com.tjmcgrew.omnisplit.util.SplitTime;

import java.util.List;

import junit.framework.*;

public class TestSplitFile extends TestCase {

  public void setUp() {
  }

  public void testParseTime() {
    for (int i=0; i < 100000; i++) {
      long value = (long)(Math.random() * 2 * 60 * 60 * 1000);
      String timestamp;
      if (value > 60 * 60 * 1000) {
        timestamp = String.format("%d:%02d:%02d.%03d",
            value / 1000 / 60 / 60,
            value / 1000 / 60 % 60,
            value / 1000 % 60,
            value % 1000);
      } else {
        timestamp = String.format("%d:%02d.%03d",
            value / 1000 / 60 % 60,
            value / 1000 % 60,
            value % 1000);
      }
      if (Math.random() > 0.5) {
        timestamp += 543; //randomly append extra digits
      }
      assertEquals(value, SplitFile.parseTime(timestamp));
    }
    assertEquals(2475, SplitFile.parseTime("2.475"));
  }

  public void testOpenJsonFile() {
    Run run = SplitFile.openJsonFile("tests/test.json");
    assertEquals("SotN Any% NSC", run.getName());
    assertEquals(300, run.getHeight());
    assertEquals(320, run.getWidth());
    assertEquals(71, run.getAttemptCount());
    assertEquals(2800, run.getStartDelay());
    List<SplitTime> splits = run.getSplits();
    SplitTime s = splits.get(0);
    assertEquals("Mist", s.getName());
    assertEquals(692100, s.getBestTime());
    assertEquals(701299, s.getBestRunTime());
    assertEquals(692100, s.getBestSegment());
    s = splits.get(1);
    assertEquals("Bat", s.getName());
    assertEquals(876934, s.getBestRunTime());
    assertEquals(856933, s.getBestTime());
    assertEquals(155819, s.getBestSegment());
    s = splits.get(2);
    assertEquals("Reverse", s.getName());
    assertEquals(1071600, s.getBestRunTime());
    assertEquals(1046241, s.getBestTime());
    assertEquals(188843, s.getBestSegment());
    s = splits.get(3);
    assertEquals("Dracula", s.getName());
    assertEquals(1432151, s.getBestRunTime());
    assertEquals(1432151, s.getBestTime());
    assertEquals(354446, s.getBestSegment());
  }

  public void testWriteJsonFile() {
  }

  public void testOpenWSplitFile() {
    Run run = SplitFile.openWSplitFile("tests/test.wsplit");
    assertEquals("Zelda", run.getName());
    assertEquals(120, run.getHeight());
    assertEquals(25, run.getWidth());
    assertEquals(103, run.getAttemptCount());
    assertEquals(1000, run.getStartDelay());
    List<SplitTime> splits = run.getSplits();
    SplitTime s = splits.get(0);
    assertEquals("2", s.getName());
    assertEquals(146450, s.getBestTime());
    assertEquals(146450, s.getBestRunTime());
    assertEquals(139380, s.getBestSegment());
    s = splits.get(1);
    assertEquals("3", s.getName());
    assertEquals(468030, s.getBestTime());
    assertEquals(468030, s.getBestRunTime());
    assertEquals(321580, s.getBestSegment());
    s = splits.get(2);
    assertEquals("4", s.getName());
    assertEquals(690120, s.getBestTime());
    assertEquals(690120, s.getBestRunTime());
    assertEquals(221150, s.getBestSegment());
    s = splits.get(3);
    assertEquals("1", s.getName());
    assertEquals(869670, s.getBestTime());
    assertEquals(869670, s.getBestRunTime());
    assertEquals(177130, s.getBestSegment());
    s = splits.get(4);
    assertEquals("5", s.getName());
    assertEquals(1233770, s.getBestTime());
    assertEquals(1233770, s.getBestRunTime());
    assertEquals(327340, s.getBestSegment());
    s = splits.get(5);
    assertEquals("7", s.getName());
    assertEquals(1458020, s.getBestTime());
    assertEquals(1458020, s.getBestRunTime());
    assertEquals(224250, s.getBestSegment());
    s = splits.get(6);
    assertEquals("6", s.getName());
    assertEquals(1656590, s.getBestTime());
    assertEquals(1656590, s.getBestRunTime());
    assertEquals(186650, s.getBestSegment());
    s = splits.get(7);
    assertEquals("8", s.getName());
    assertEquals(1789870, s.getBestTime());
    assertEquals(1789870, s.getBestRunTime());
    assertEquals(128670, s.getBestSegment());
    s = splits.get(8);
    assertEquals("Bacon", s.getName());
    assertEquals(2145750, s.getBestTime());
    assertEquals(2145750, s.getBestRunTime());
    assertEquals(3460, s.getBestSegment());
  }

  public void testWriteWSplitFile() {
  }
}

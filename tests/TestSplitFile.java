
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
//      System.out.println(timestamp);
      if (Math.random() > 0.5) {
        timestamp += 543; //randomly append extra digits
      }
      assertEquals(value, SplitFile.parseTime(timestamp));
    }
  }

  public void testOpenJsonFile() {
    Run run = SplitFile.openJsonFile("tests/test.json");
    assertEquals("SotN Any% NSC", run.getName());
    assertEquals(300, run.getHeight());
    assertEquals(320, run.getWidth());
//    assertEquals(71, run.getAttemptCount());
//    assertEquals(2800, run.getStartDelay());
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
//    Run run = SplitFile.openWSplitFile("tests/test.wsplit");
  }

  public void testWriteWSplitFile() {
  }
}

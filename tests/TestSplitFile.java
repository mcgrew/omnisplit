
import com.tjmcgrew.omnisplit.io.SplitFile;
import com.tjmcgrew.omnisplit.util.Run;
import com.tjmcgrew.omnisplit.util.SplitTime;

import java.util.List;

import junit.framework.*;

public class TestSplitFile extends TestCase {

  public void setUp() {
  }

  public void testOpenJsonFile() {
    Run run = SplitFile.openJsonFile("tests/test.json");
    assertEquals("SotN Any% NSC", run.getName());
    assertEquals(300, run.getHeight());
    assertEquals(320, run.getWidth());
    assertEquals(71, run.getAttemptCount());
    assertEquals(2800, run.getStartDelay().getValue());
    List<SplitTime> splits = run.getSplits();
    SplitTime s = splits.get(0);
    assertEquals("Mist", s.getName());
    assertEquals(692100, s.getBestTime().getValue());
    assertEquals(701299, s.getBestRunTime().getValue());
    assertEquals(692100, s.getBestSegment().getValue());
    s = splits.get(1);
    assertEquals("Bat", s.getName());
    assertEquals(876934, s.getBestRunTime().getValue());
    assertEquals(856933, s.getBestTime().getValue());
    assertEquals(155819, s.getBestSegment().getValue());
    s = splits.get(2);
    assertEquals("Reverse", s.getName());
    assertEquals(1071600, s.getBestRunTime().getValue());
    assertEquals(1046241, s.getBestTime().getValue());
    assertEquals(188843, s.getBestSegment().getValue());
    s = splits.get(3);
    assertEquals("Dracula", s.getName());
    assertEquals(1432151, s.getBestRunTime().getValue());
    assertEquals(1432151, s.getBestTime().getValue());
    assertEquals(354446, s.getBestSegment().getValue());
  }

  public void testWriteJsonFile() {
    Run run = SplitFile.openJsonFile("tests/test.json");
    run.setFilename("/tmp/test.json");
    SplitFile.writeJsonFile(run);
    Run run2 = SplitFile.openJsonFile("/tmp/test.json");
    assertEquals(run.getName(), run2.getName());
    assertEquals(run.getHeight(), run2.getHeight());
    assertEquals(run.getWidth(), run2.getWidth());
    assertEquals(run.getAttemptCount(), run2.getAttemptCount());
    assertEquals(run.getStartDelay().getValue(), run2.getStartDelay().getValue());
    List<SplitTime> splits = run.getSplits();
    List<SplitTime> splits2 = run2.getSplits();
    assertEquals(splits.size(), splits2.size());
    for (int i=0; i < splits.size(); i++) {
      SplitTime s = splits.get(i);
      SplitTime s2 = splits2.get(i);
      assertEquals(s.getName(), s2.getName());
      assertEquals(s.getBestTime().getValue(),s2.getBestTime().getValue());
      assertEquals(s.getBestRunTime().getValue(),s2.getBestRunTime().getValue());
      assertEquals(s.getBestSegment().getValue(),s2.getBestSegment().getValue());
    }
  }

  public void testOpenWSplitFile() {
    Run run = SplitFile.openWSplitFile("tests/test.wsplit");
    assertEquals("Zelda", run.getName());
    assertEquals(120, run.getHeight());
    assertEquals(25, run.getWidth());
    assertEquals(103, run.getAttemptCount());
    assertEquals(1000, run.getStartDelay().getValue());
    List<SplitTime> splits = run.getSplits();
    SplitTime s = splits.get(0);
    assertEquals("2", s.getName());
    assertEquals(146450, s.getBestTime().getValue());
    assertEquals(146450, s.getBestRunTime().getValue());
    assertEquals(139380, s.getBestSegment().getValue());
    s = splits.get(1);
    assertEquals("3", s.getName());
    assertEquals(468030, s.getBestTime().getValue());
    assertEquals(468030, s.getBestRunTime().getValue());
    assertEquals(321580, s.getBestSegment().getValue());
    s = splits.get(2);
    assertEquals("4", s.getName());
    assertEquals(690120, s.getBestTime().getValue());
    assertEquals(690120, s.getBestRunTime().getValue());
    assertEquals(221150, s.getBestSegment().getValue());
    s = splits.get(3);
    assertEquals("1", s.getName());
    assertEquals(869670, s.getBestTime().getValue());
    assertEquals(869670, s.getBestRunTime().getValue());
    assertEquals(177130, s.getBestSegment().getValue());
    s = splits.get(4);
    assertEquals("5", s.getName());
    assertEquals(1233770, s.getBestTime().getValue());
    assertEquals(1233770, s.getBestRunTime().getValue());
    assertEquals(327340, s.getBestSegment().getValue());
    s = splits.get(5);
    assertEquals("7", s.getName());
    assertEquals(1458020, s.getBestTime().getValue());
    assertEquals(1458020, s.getBestRunTime().getValue());
    assertEquals(224250, s.getBestSegment().getValue());
    s = splits.get(6);
    assertEquals("6", s.getName());
    assertEquals(1656590, s.getBestTime().getValue());
    assertEquals(1656590, s.getBestRunTime().getValue());
    assertEquals(186650, s.getBestSegment().getValue());
    s = splits.get(7);
    assertEquals("8", s.getName());
    assertEquals(1789870, s.getBestTime().getValue());
    assertEquals(1789870, s.getBestRunTime().getValue());
    assertEquals(128670, s.getBestSegment().getValue());
    s = splits.get(8);
    assertEquals("Bacon", s.getName());
    assertEquals(2145750, s.getBestTime().getValue());
    assertEquals(2145750, s.getBestRunTime().getValue());
    assertEquals(3460, s.getBestSegment().getValue());
  }

  public void testWriteWSplitFile() {
    Run run = SplitFile.openWSplitFile("tests/test.wsplit");
    run.setFilename("/tmp/test.wsplit");
    SplitFile.writeWSplitFile(run);
    Run run2 = SplitFile.openWSplitFile("/tmp/test.wsplit");
    assertEquals(run.getName(), run2.getName());
    assertEquals(run.getHeight(), run2.getHeight());
    assertEquals(run.getWidth(), run2.getWidth());
    assertEquals(run.getAttemptCount(), run2.getAttemptCount());
    assertEquals(run.getStartDelay().getValue(), run2.getStartDelay().getValue());
    List<SplitTime> splits = run.getSplits();
    List<SplitTime> splits2 = run2.getSplits();
    assertEquals(splits.size(), splits2.size());
    for (int i=0; i < splits.size(); i++) {
      SplitTime s = splits.get(i);
      SplitTime s2 = splits2.get(i);
      assertEquals(s.getName(), s2.getName());
      assertEquals(s.getBestTime().getValue(),s2.getBestTime().getValue());
      assertEquals(s.getBestRunTime().getValue(),s2.getBestRunTime().getValue());
      assertEquals(s.getBestSegment().getValue(),s2.getBestSegment().getValue());
    }
  }
}


import com.tjmcgrew.omnisplit.util.Time;

import java.util.List;

import junit.framework.*;

public class TestTime extends TestCase {

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
      } else if (value > 60 * 1000) {
        timestamp = String.format("%d:%02d.%03d",
            value / 1000 / 60,
            value / 1000 % 60,
            value % 1000);
      } else {
        timestamp = String.format("%02d.%03d",
            value / 1000,
            value % 1000);
      }
      if (Math.random() > 0.5) {
        timestamp += 543; //randomly append extra digits
      }
      assertEquals(value, Time.parseTime(timestamp));
    }
    assertEquals(2475, Time.parseTime("2.475"));
  }
}


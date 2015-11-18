
import com.tjmcgrew.omnisplit.util.Time;

import java.util.List;

import junit.framework.*;

public class TestTime extends TestCase {
		private Time time1a, time2a, time3a;
		private Time time1b, time2b, time3b;
		private Time time1c, time2c, time3c;
		private Time time1d, time2d, time3d;
		private Time time1e, time2e, time3e;

  public void setUp() {
		time1a = new Time(3453);
		time2a = new Time(63453);
		time3a = new Time(3663453);
		time1b = new Time("3.453");
		time2b = new Time("1:03.453");
		time3b = new Time("1:01:03.453");
		time1c = new Time("+3.453");
		time2c = new Time("+1:03.453");
		time3c = new Time("+1:01:03.453");
		time1d = new Time(-3453);
		time2d = new Time(-63453);
		time3d = new Time(-3663453);
		time1e = new Time("-3.453");
		time2e = new Time("-1:03.453");
		time3e = new Time("-1:01:03.453");
  }

	public void testConststructor() {
		assertTrue(time1a.equals(time1b));
		assertTrue(time2a.equals(time2b));
		assertTrue(time3a.equals(time3b));
		assertTrue(time1a.equals(time1c));
		assertTrue(time2a.equals(time2c));
		assertTrue(time3a.equals(time3c));
		assertTrue(time1d.equals(time1e));
		assertTrue(time2d.equals(time2e));
		assertTrue(time3d.equals(time3e));
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
        timestamp += 343; //randomly append extra digits
      }
      assertEquals(value, Time.parseTime(timestamp));
    }
    assertEquals(2475, Time.parseTime("2.475"));
  }

	public void testFormatTime() {
		assertEquals("3",           Time.formatTime(3453, false, 0));
		assertEquals("1:03",        Time.formatTime(63453, false, 0));
		assertEquals("1:01:03",     Time.formatTime(3663453, false, 0));

		assertEquals("3.4",         Time.formatTime(3453, false, 1));
		assertEquals("1:03.4",      Time.formatTime(63453, false, 1));
		assertEquals("1:01:03.4",   Time.formatTime(3663453, false, 1));

		assertEquals("3.45",        Time.formatTime(3453, false, 2));
		assertEquals("1:03.45",     Time.formatTime(63453, false, 2));
		assertEquals("1:01:03.45",  Time.formatTime(3663453, false, 2));

		assertEquals("3.453",       Time.formatTime(3453, false, 3));
		assertEquals("1:03.453",    Time.formatTime(63453, false, 3));
		assertEquals("1:01:03.453", Time.formatTime(3663453, false, 3));

		assertEquals("+3.453",       Time.formatTime(3453, true, 3));
		assertEquals("+1:03.453",    Time.formatTime(63453, true, 3));
		assertEquals("+1:01:03.453", Time.formatTime(3663453, true, 3));

		assertEquals("-3.453",       Time.formatTime(-3453, false, 3));
		assertEquals("-1:03.453",    Time.formatTime(-63453, false, 3));
		assertEquals("-1:01:03.453", Time.formatTime(-3663453, false, 3));

		assertEquals("-3.453",       Time.formatTime(-3453, true, 3));
		assertEquals("-1:03.453",    Time.formatTime(-63453, true, 3));
		assertEquals("-1:01:03.453", Time.formatTime(-3663453, true, 3));
	}


	public void testCompareTo(){
		assertEquals(-1, time2a.compareTo(time1a));
		assertEquals(1,  time2a.compareTo(time3a));
		assertEquals(0,  time2a.compareTo(time2b));
	}

	public void testPlus() {
		assertEquals(3553, time1a.plus(100).getValue());
		assertEquals(66906, time2a.plus(time1a).getValue());
	}

	public void testMinus() {
		assertEquals(3353, time1a.minus(100).getValue());
		assertEquals(60000, time2a.minus(time1a).getValue());
	}

	public void testFormat() {
		assertEquals("3",           time1a.format(false, 0));
		assertEquals("1:03",        time2a.format(false, 0));
		assertEquals("1:01:03",     time3a.format(false, 0));

		assertEquals("3.4",         time1a.format(false, 1));
		assertEquals("1:03.4",      time2a.format(false, 1));
		assertEquals("1:01:03.4",   time3a.format(false, 1));

		assertEquals("3.45",        time1a.format(false, 2));
		assertEquals("1:03.45",     time2a.format(false, 2));
		assertEquals("1:01:03.45",  time3a.format(false, 2));

		assertEquals("3.453",        time1a.format(false, 3));
		assertEquals("1:03.453",     time2a.format(false, 3));
		assertEquals("1:01:03.453",  time3a.format(false, 3));

		assertEquals("+3.453",       time1a.format(true, 3));
		assertEquals("+1:03.453",    time2a.format(true, 3));
		assertEquals("+1:01:03.453", time3a.format(true, 3));

		assertEquals("-3.453",       time1d.format(false, 3));
		assertEquals("-1:03.453",    time2d.format(false, 3));
		assertEquals("-1:01:03.453", time3d.format(false, 3));

		assertEquals("-3.453",       time1d.format(true, 3));
		assertEquals("-1:03.453",    time2d.format(true, 3));
		assertEquals("-1:01:03.453", time3d.format(true, 3));
	}

}


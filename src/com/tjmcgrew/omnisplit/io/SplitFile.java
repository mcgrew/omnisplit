package com.tjmcgrew.omnisplit.io;

import com.tjmcgrew.omnisplit.util.Run;
import com.tjmcgrew.omnisplit.util.SplitTime;

import javax.json.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SplitFile {

  public static Run openJsonFile(String file) {
		return openJsonFile(new File(file));
	}

  public static Run openJsonFile(File file) {
    JsonReader reader;
    JsonObject object;
    try {
      reader = Json.createReader(new FileReader(file));
      object = reader.readObject();
    } catch(FileNotFoundException e) {
      // Todo: log an error and report to the user...
      return null;
    }
    reader.close();
    List<SplitTime> splits = new ArrayList();
    JsonArray jsonSplits = object.getJsonArray("splits");
    for (JsonValue jsonSplit : jsonSplits) {
      JsonObject j = (JsonObject)jsonSplit;
      long bestTime = parseTime(j.getJsonString("best_time").getString());
      long bestSegment = parseTime(j.getJsonString("best_segment").getString());
      splits.add(new SplitTime(j.getJsonString("title").getString(), 
          bestTime, bestSegment));
    }
		return new Run( object.getJsonString("title").getString(), splits);
  }

  public static Run openWSplitFile(String file) {
		return openWSplitFile(new File(file));
	}

  public static Run openWSplitFile(File file) {
		// not yet implemented
		return null;
  }

  public static long parseTime(String time) {
    Pattern p = null;
    Matcher m = null;
    long hours, minutes, seconds, msec;
    try {
      p = Pattern.compile("(?:(\\d+)\\:)?(\\d+)\\:(\\d{2}).(\\d{3})");
      m = p.matcher(time);
      m.find();
      String hourString = m.group(1);
      if (hourString != null) {
        hours = Long.parseLong(hourString);
      } else {
        hours = 0L;
      }
      minutes = Long.parseLong(m.group(2));
      seconds = Long.parseLong(m.group(3));
      msec = Long.parseLong(m.group(4));
      return msec + seconds * 1000 + minutes * 60 * 1000 + hours * 60 * 60 * 1000;
    } catch (IllegalStateException e) {
//      System.out.printf("Pattern: %s\n", p.toString());
//      System.out.printf("Input:   %s\n", time);
//      System.out.printf("Matches: %s\n", m.matches());
//      System.out.printf("Groups:  %d\n", m.groupCount());
      System.out.println(e.getMessage());
    }
    return 0L;
  }


}

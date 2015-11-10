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

  private static long parseTime(String time) {
    // not yet implemented
    return 0;
  }


}

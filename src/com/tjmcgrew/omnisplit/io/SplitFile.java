package com.tjmcgrew.omnisplit.io;

import com.tjmcgrew.omnisplit.util.Run;
import com.tjmcgrew.omnisplit.util.SplitTime;
import com.tjmcgrew.omnisplit.util.Time;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.*;
import javax.swing.filechooser.FileFilter;

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
      JsonObject split = (JsonObject)jsonSplit;
      long runTime = Time.parseTime(split.getJsonString("time").getString());
      long bestTime = Time.parseTime(split.getJsonString("best_time").getString());
      long bestSegment = 
          Time.parseTime(split.getJsonString("best_segment").getString());
      splits.add(new SplitTime(split.getJsonString("title").getString(), 
          runTime, bestSegment, bestTime));
    }
		Run run = new Run( object.getJsonString("title").getString(), splits);
    run.setWidth(object.getInt("width"));
    run.setHeight(object.getInt("height"));
    run.setAttemptCount(object.getInt("attempt_count"));
    run.setStartDelay(Time.parseTime(
        object.getJsonString("start_delay").getString()));
    return run;
  }

  public static void writeJsonFile(Run run) {
    // not yet implemented
  }

  public static Run openWSplitFile(String file) {
		return openWSplitFile(new File(file));
	}

  public static Run openWSplitFile(File file) {
		// not yet implemented
    String title = null;
    int attempts = 0;
    long offset = 0;
    int height = 0, width = 0;
    ArrayList<String> icons = new ArrayList();
    ArrayList<SplitTime> splits = new ArrayList();
    Pattern valuePattern = Pattern.compile("(.*)=(.*)");
    HashMap <String,String> valueMap = new HashMap();
    try {
      Scanner s = new Scanner(file);
      while(s.hasNextLine()) {
        String line = s.nextLine();
        Matcher m = valuePattern.matcher(line);
        m.find();
        if (m.matches()) {
          valueMap.put(m.group(1), m.group(2));
//          System.out.printf("Found %s -> %s\n", m.group(1), m.group(2));
        } else {
          String[] v = splitLine(line, ",", true);
          splits.add(new SplitTime(v[0], 
                     (long)Math.round((Double.parseDouble(v[2]) * 1000)),
                     (long)Math.round((Double.parseDouble(v[3]) * 1000)),
                     (long)Math.round((Double.parseDouble(v[2]) * 1000)))); // maybe this should be v[1]?
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    Run run = new Run(valueMap.get("Title"), splits);
    String [] size = splitLine(valueMap.get("Size"), ",", true);
    run.setHeight(Integer.parseInt(size[0]));
    run.setWidth(Integer.parseInt(size[1]));
    run.setAttemptCount(Integer.parseInt(valueMap.get("Attempts")));
    run.setStartDelay(
        (long)Math.round((Double.parseDouble(valueMap.get("Offset")) * 1000)));
    // not supported yet.
//    String[] icons = splitLine(valueMap.get("Icons"), ",", true);
//    for (int i=0; i < icons.length; i++) {
//      splits.get(i).setIcon(icons[i]);
//    }
    return run;
  }

  public static void writeWSplitFile(Run run) {
    // not yet implemented
  }

  private static String [] splitLine( String input, String delimiters, 
                                      boolean useQuotes ) {
    ArrayList<String> returnValue = new ArrayList<String>( );
    StringBuilder nextValue = new StringBuilder( );
    boolean inQuotes = false;
    for ( int i=0; i < input.length( ); i++ ) {
      if ( input.charAt( i ) == '"' && useQuotes ) {
        inQuotes = !inQuotes;
      } else if ( !inQuotes && delimiters.contains( input.substring( i, i+1 ))) {
        returnValue.add( nextValue.toString( ));
        nextValue = new StringBuilder( );
      } else {
        nextValue.append( input.charAt( i ));
      }
    }
    if ( nextValue.length( ) > 0 ) {
      returnValue.add( nextValue.toString( ));
    }
    return returnValue.toArray( new String[ returnValue.size( ) ]);
  }

}

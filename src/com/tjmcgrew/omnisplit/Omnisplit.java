package com.tjmcgrew.omnisplit;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import com.tjmcgrew.omnisplit.ui.*;


/**
 * The main class
 */
public class Omnisplit {
  public static void main (String [] args) {
    OmnisplitWindow.newWindow();
  }

  public static JsonStructure readSplits(String filename) {
    return readSplits(new File(filename));
  }

  public static JsonStructure readSplits(File file) {
    JsonStructure returnvalue;
    try {
      FileReader fileReader = new FileReader(file);
      JsonReader reader = Json.createReader(fileReader);
      returnvalue = reader.read();
    } catch (IOException e) {
      returnvalue = Json.createObjectBuilder().build();
      e.printStackTrace();
    }
    return returnvalue;
  }

}




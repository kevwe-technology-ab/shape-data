package net.lulli.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
  private static PropertiesManager istanza;
  public static final String APPLICATION_CONFIGURATION_FILE = "database.properties";

  private static String configurationFileName = APPLICATION_CONFIGURATION_FILE;
  private static Properties properties;

  static Logger log = Logger.getLogger("PropertiesManager");

  private PropertiesManager(File configurationFile) {
    try {
      InputStream fileInputStream = new FileInputStream(configurationFile);
      properties = new Properties();
      properties.load(fileInputStream);
    } catch (Exception e) {
      log.error("Cannot load file: [" + configurationFileName + "]");
      throw new IllegalStateException("Cannot load file: [" + configurationFileName + "]");
    }
  }

  private PropertiesManager(String configurationFileName) {
    properties = new Properties();
    try {
      properties.load(new FileInputStream(configurationFileName));
    } catch (Exception e) {
      log.error("Cannot load file: [" + configurationFileName + "]");
      throw new IllegalStateException("Cannot load file: [" + configurationFileName + "]");
    }
  }

  public static PropertiesManager getInstance() {
    if (istanza == null) {
      istanza = new PropertiesManager(APPLICATION_CONFIGURATION_FILE);
    }
    return istanza;
  }

  public Properties getProperties() {
    return properties;
  }

  public static PropertiesManager from(String configurationFileName) {
    return new PropertiesManager(configurationFileName);
  }

  public static PropertiesManager fromFile(File configurationFile) {
    return new PropertiesManager(configurationFile);
  }

}
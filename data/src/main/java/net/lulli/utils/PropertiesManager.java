package net.lulli.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class PropertiesManager {
  private static PropertiesManager istanza;
  public static final String APPLICATION_CONFIGURATION_FILE = "database.properties";

  private static String configurationFileName = APPLICATION_CONFIGURATION_FILE;
  private static Properties properties;

  static Logger log = Logger.getLogger("PropertiesManager");

  private PropertiesManager(File configurationFile) throws IOException {
	  InputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(configurationFile);
      properties = new Properties();
      properties.load(fileInputStream);
    } catch (Exception e) {
      log.error("Cannot load file: [" + configurationFileName + "]");
      throw new IllegalStateException("Cannot load file: [" + configurationFileName + "]");
    } finally {
		if (null != fileInputStream) {
			fileInputStream.close();
		}
	}
  }

  private PropertiesManager(String configurationFileName) throws IOException {
    properties = new Properties();
    FileInputStream fileInputStream = null;
    try {
    	fileInputStream = new FileInputStream(configurationFileName);
      properties.load(fileInputStream);
    } catch (Exception e) {
      log.error("Cannot load file: [" + configurationFileName + "]");
      throw new IllegalStateException("Cannot load file: [" + configurationFileName + "]");
    }
    finally {
		if (null != fileInputStream) {
			fileInputStream.close();
		}
	}
  }

  public static PropertiesManager getInstance() {
    if (istanza == null) {
      try {
		istanza = new PropertiesManager(APPLICATION_CONFIGURATION_FILE);
	} catch (IOException e) {
		throw new IllegalStateException("Cannot instantiate PropertiesManager");
	}
    }
    return istanza;
  }

  public Properties getProperties() {
    return properties;
  }

  public static PropertiesManager from(String configurationFileName) {
    try {
		return new PropertiesManager(configurationFileName);
	} catch (IOException e) {
		throw new IllegalStateException("Cannot instantiate PropertiesManager");
	}
  }

  public static PropertiesManager fromFile(File configurationFile) {
    try {
		return new PropertiesManager(configurationFile);
	} catch (IOException e) {
		throw new IllegalStateException("Cannot instantiate PropertiesManager");
	}
  }
}

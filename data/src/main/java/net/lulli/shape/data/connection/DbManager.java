package net.lulli.shape.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import net.lulli.utils.PropertiesManager;

public class DbManager extends AbstractDbConnectionManager {
  public static DbManager INSTANCE = new DbManager();

  static Logger log = Logger.getLogger("AbstractDbConnectionManager");
  protected String jdbcUrl;
  protected String driveClassName;
  protected String databaseUser;
  protected String databasePassword;

  protected List<Connection> connections;
  protected int poolSize = 3;
  protected int connection_counter;

  public Connection getConnection() {
    Connection con = null;
    connection_counter++;
    con = connections.get(connection_counter % poolSize);
    return con;
  }

  public void releaseConnection(Connection con) {
    // Do nothing
  }

  public void closeConnection(Connection con) {
    try {
      if (null != con) {
        con.close();
      }
    } catch (Exception e) {
      log.error(e);
    }
  }

  protected void init() {
    Connection singleConn;
    connection_counter = 0;
    connections = new ArrayList<Connection>();
    for (int i = 0; i < poolSize; i++) {
      try {
        Class.forName(driveClassName);
        singleConn = DriverManager.getConnection(jdbcUrl, databaseUser, databasePassword);
        connections.add(singleConn);
      } catch (Exception e) {
        log.error(e);
      }
    }
  }

  private DbManager() {
    PropertiesManager configManager = PropertiesManager.getInstance();
    Properties dbProperties = configManager.getProperties();
    jdbcUrl = (String) dbProperties.get("database.JDBC_URL");
    driveClassName = (String) dbProperties.get("database.DRIVER_CLASS_NAME");
    databaseUser = (String) dbProperties.get("database.DB_USER");
    databasePassword = (String) dbProperties.get("database.DB_PASSWORD");
    init();
  }
}

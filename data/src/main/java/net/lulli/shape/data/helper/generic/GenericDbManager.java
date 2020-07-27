package net.lulli.shape.data.helper.generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import net.lulli.shape.data.connection.AbstractDbConnectionManager;
import net.lulli.shape.data.helper.DbConnectionParameters;
import org.apache.log4j.Logger;

public class GenericDbManager extends AbstractDbConnectionManager {
  static Logger log = Logger.getLogger("GenericDbManager");
  private static GenericDbManager instance;
  private static DbConnectionParameters staticParams;

  public static GenericDbManager withParams(DbConnectionParameters params) {
    staticParams = params;
    if (instance == null) {
      instance = new GenericDbManager(staticParams);
    }
    return instance;
  }

  protected void init() {
    Connection singleConn;
    connection_counter = 0;
    connections = new ArrayList<Connection>();
    for (int i = 0; i < poolSize; i++) {
      try {
        Class.forName(driveClassName);
        singleConn =
            DriverManager.getConnection(
                staticParams.jdbcUrl, staticParams.user, staticParams.password);
        connections.add(singleConn);
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
  }

  private GenericDbManager(DbConnectionParameters parameters) {
    jdbcUrl = parameters.jdbcUrl;
    driveClassName = parameters.driverClassName;
    databaseUser = parameters.user;
    databasePassword = parameters.password;

    init();
  }
}

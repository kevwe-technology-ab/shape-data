package net.lulli.shape.data.connection;

import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class AbstractDbConnectionManager {
  static Logger log = Logger.getLogger("DbConnectionManager");
  protected String jdbcUrl;
  protected String driveClassName;
  protected String databaseUser;
  protected String databasePassword;
  protected String databaseSchema;

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
}

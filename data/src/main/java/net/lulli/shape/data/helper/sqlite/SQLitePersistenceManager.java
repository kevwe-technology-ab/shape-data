package net.lulli.shape.data.helper.sqlite;

import net.lulli.shape.data.AbstractPersistenceManager;
import net.lulli.shape.data.connection.AbstractDbConnectionManager;

public class SQLitePersistenceManager extends AbstractPersistenceManager {
  private String dbName;
  private String sqlDialect;

  public AbstractDbConnectionManager getDbConnectionManager() {
    SQLiteDbManager dbManager;
    if (null != this.dbName) {
      dbManager = SQLiteDbManager.getInstance(dbName);
    } else {
      dbManager = SQLiteDbManager.getInstance();
    }
    return dbManager;
  }

  public SQLitePersistenceManager() {
    super();
  }

  public SQLitePersistenceManager(String dbName) {
    this.dbName = dbName;
  }

  public void setSQLDialect(String sqlDialect) {
  }

}

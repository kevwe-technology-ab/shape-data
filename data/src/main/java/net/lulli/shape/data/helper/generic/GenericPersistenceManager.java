package net.lulli.shape.data.helper.generic;

import net.lulli.shape.data.AbstractPersistenceManager;
import net.lulli.shape.data.api.Dialect;
import net.lulli.shape.data.connection.AbstractDbConnectionManager;
import net.lulli.shape.data.helper.DbConnectionParameters;

public class GenericPersistenceManager extends AbstractPersistenceManager {

  private final DbConnectionParameters params;

  public GenericPersistenceManager(DbConnectionParameters params) {
    this.params = params;
  }

  public AbstractDbConnectionManager getDbConnectionManager() {
    GenericDbManager dbManager = GenericDbManager.withParams(params);
    this.setSQLDialect(params.dialect);
    return dbManager;
  }

  @Override
  public void setSQLDialect(String sqlDialect) {
    this.sqlDialect = Dialect.valueOf(sqlDialect);
  }
}

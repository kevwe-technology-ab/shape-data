package net.lulli.shape.data.dao;

import java.util.Hashtable;

import net.lulli.shape.data.api.Dialect;
import net.lulli.shape.data.impl.jdbc.Dao;

public class DaoFactory {
  private static Hashtable<String, Object> daoPool = new Hashtable<String, Object>();
  private static Dao metaDao;

  private DaoFactory() {}

  public static Dao createMetaDao(String tableName, Dialect sqlDialect) {
    return getNewMetaDao(tableName);
  }

  public static Dao getNewMetaDao(String tableName) {
    return new Dao(tableName);
  }
}

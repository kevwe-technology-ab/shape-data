package net.lulli.shape.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IWheresMap;
import net.lulli.shape.data.connection.AbstractDbConnectionManager;
import net.lulli.shape.data.dao.DaoFactory;
import net.lulli.shape.data.dto.Dto;
import net.lulli.shape.data.dto.Wheres;
import net.lulli.shape.data.impl.jdbc.Dao;
import net.lulli.shape.data.model.SQLDialect;
import org.apache.log4j.Logger;

public abstract class AbstractPersistenceManager
    implements net.lulli.shape.data.api.IPersistenceManager {
  static Logger log = Logger.getLogger("AbstractMetaPersistenceManager");
  protected String sqlDialect = SQLDialect.STANDARD;

  public abstract AbstractDbConnectionManager getDbConnectionManager();

  public Integer insert(net.lulli.shape.data.api.IDto dto) {
    return insertLegacy(dto);
  }

  private int insertLegacy(net.lulli.shape.data.api.IDto dto) {
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    Connection conn = null;
    Dao dao;
    int insertedNo = -1;
    try {
      conn = dbManager.getConnection();
      dao = DaoFactory.createMetaDao(dto.tableName(), sqlDialect);
      insertedNo = dao.insert(dto, conn);
    } catch (Exception e) {
      log.trace(e.getMessage());
    } finally {
      dbManager.releaseConnection(conn);
    }
    return insertedNo;
  }

  public Integer update(IDto dto, IWheresMap wheres) {

    return updateLegacy(dto, wheres);
  }

  public int updateLegacy(IDto dto, IWheresMap wheres) {
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    Connection conn = null;
    Dao dao;
    int updatedNo = -1;
    try {
      conn = dbManager.getConnection();
      dao = DaoFactory.createMetaDao(dto.tableName(), sqlDialect);
      updatedNo = dao.update(dto, wheres, conn);
    } catch (Exception e) {
      log.trace(e.getMessage());
    } finally {
      dbManager.releaseConnection(conn);
    }
    return updatedNo;
  }

  public Integer delete(IDto dto, IWheresMap wheres) {
    return deleteLegacy(dto, wheres);
  }

  private int deleteLegacy(IDto dto, IWheresMap wheres) {
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    Connection conn = null;
    Dao dao;
    int deletedNo = -1;
    try {
      conn = dbManager.getConnection();
      dao = DaoFactory.createMetaDao(dto.tableName(), sqlDialect);
      deletedNo = dao.delete(dto, wheres, conn);
    } catch (Exception e) {
      log.trace(e.getMessage());
    } finally {
      dbManager.releaseConnection(conn);
    }
    return deletedNo;
  }

  public Integer save(IDto dto, IWheresMap wheres) {
    String retValue = saveLegacy(dto, (Wheres) wheres);
    return Integer.valueOf(retValue);
  }

  String saveLegacy(IDto dto, IWheresMap wheres) {
    String id = "";
    Connection conn = null;
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    Dao dao = null;
    try {
      dao = DaoFactory.createMetaDao(dto.tableName(), sqlDialect);
      if (null != dao) {
        conn = dbManager.getConnection();
        Integer presenti = dao.selectCount(dto, wheres, conn);
        if (presenti > 0) {
          removeNullKey(dto);
          dao.update(dto, wheres, conn);
        } else {
          // 3 Se non presente inserisce
          insert(dto);
        }
      }
    } catch (Exception e) {
      log.error("Insert/Update FALLITA" + e);
    }
    if (null != dao) {
      try {
        id = dao.selectIdWhere(dto, wheres, conn, false, new Integer(1));
      } catch (Exception e) {
        log.error("Cannot decode record from table=[" + dto.tableName() + "]");
      } finally {
        dbManager.releaseConnection(conn);
      }
      log.trace("[4]: RETURN_ID");
      return id;
    }
    return "0";
  }

  private void removeNullKey(IDto dto) {
    if (dto.containsKey(null)) {
      dto.remove(null);
      log.trace("METADAO_updateWhere: REMOVE_NULL");
    }
  }

  public Integer selectCount(IDto requestDto, IWheresMap wheres, boolean definedAttributes) {
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    Connection conn = null;
    Dao dao;
    Integer count = 0;
    try {
      conn = dbManager.getConnection();
      dao = DaoFactory.createMetaDao(requestDto.tableName(), sqlDialect);
      count = dao.selectCount(requestDto, wheres, conn);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return count;
  }

  public List<String> descTable(String tableName) {
    List<String> fieldList = null;
    String sqlDialect = this.getSQLDialect();
    log.trace("Detected SQLDialect:[" + sqlDialect + "]");

    if (sqlDialect.equals(STANDARD)) {
      fieldList = descTableConcrete(tableName);
    }
    return fieldList;
  }

  private List<String> descTableConcrete(String tableName) {
    List<String> fieldList = new ArrayList<String>();
    try {
      Hashtable wheres = null;
      List<IDto> risultati = query("select * from " + tableName);
      IDto rigaZero = risultati.get(0); // BAD: funziona solo se la tabella ha almeno 1
      // record!!
      Set<String> keys = rigaZero.keySet();
      Iterator<String> iter = keys.iterator();
      String field;
      while (iter.hasNext()) {
        field = iter.next();
        fieldList.add(field);
      }
    } catch (Exception e) {
      log.error("" + e);
    }
    return fieldList;
  }

  public boolean createTable(String tableName, List<String> fields) {
    boolean created = false;
    AbstractDbConnectionManager dbManager;
    Dao dao;
    Connection conn;
    try {
      dbManager = getDbConnectionManager();
      conn = dbManager.getConnection();
      dao = DaoFactory.createMetaDao(tableName, sqlDialect);
      created = dao.createTable(tableName, fields, conn);
    } catch (Exception e) {
      log.error("" + e);
    }
    return created;
  }

  public boolean dropTable(String tableName) {
    boolean dropped = false;
    AbstractDbConnectionManager dbManager;
    Dao dao;
    Connection conn;
    try {
      dbManager = getDbConnectionManager();
      conn = dbManager.getConnection();
      dao = DaoFactory.createMetaDao(tableName, sqlDialect);
      dropped = dao.dropTable(tableName, conn);
    } catch (Exception e) {
      log.error("" + e);
    }
    return dropped;
  }

  public String getSQLDialect() {
    return sqlDialect;
  }

  public List<IDto> query(String sql, String... args) {
    return query(String.format(sql, (Object[]) args));
  }

  private List<IDto> query(String sqlInputString) {
    log.trace("BEGIN search");
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    Connection conn = null;
    Dao dao;
    List<IDto> results = null;

    String sqlDialect = this.sqlDialect;
    try {
      log.trace("BEFORE conn");
      conn = dbManager.getConnection();
      log.trace("AFTER conn");
      dao = DaoFactory.createMetaDao(Dto.DEFAULT_RESULTSET_NAME, sqlDialect);
      results = dao.runQuery(sqlInputString, conn);
    } catch (Exception e) {
      log.trace(e.getMessage());
    } finally {
      dbManager.releaseConnection(conn);
    }
    return results;
  }

  public int execute(String sql, String... args) {
    return execute(String.format(sql, (Object[]) args));
  }

  private int execute(String sqlInputString) {
    int retvalue = 0;
    PreparedStatement pstmt = null;
    Connection conn = null;
    AbstractDbConnectionManager dbManager = getDbConnectionManager();
    try {
      conn = dbManager.getConnection();
      log.debug("sqlInputString = [" + sqlInputString + "]");
      pstmt = conn.prepareStatement(sqlInputString);
      retvalue = pstmt.executeUpdate();
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      try {
        if (null != pstmt) {
          pstmt.close();
        }
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    return retvalue;
  }
}

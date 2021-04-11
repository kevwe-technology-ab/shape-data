package net.lulli.shape.data.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.lulli.shape.data.api.IDao;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IWheresMap;
import net.lulli.shape.data.dao.DaoUtils;
import net.lulli.shape.data.impl.jdbc.internal.DeleteHandler;
import net.lulli.shape.data.impl.jdbc.internal.InsertHandler;
import net.lulli.shape.data.impl.jdbc.internal.SelectHandler;
import net.lulli.shape.data.impl.jdbc.internal.TableHandler;
import net.lulli.shape.data.impl.jdbc.internal.UpdateHandler;
import org.apache.log4j.Logger;

public class Dao implements IDao<Connection> {
  static Logger log = Logger.getLogger("Dao");
  public String table;
  public String idField;

  private Map<String, PreparedStatement> statementsCache = new HashMap<>();

  private Dao() {
    //
  }

  public String tableName() {
    return this.table;
  }

  public String getIdField() {
    return this.idField;
  }

  public Dao(String tableName) {
    this.table = tableName;
  }

  public boolean dropTable(String tableName, Connection dataConnection) {
    Connection conn = dataConnection;
    boolean isDropped = false;
    String sqlString = "DROP TABLE " + tableName;
    try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
      pstmt.execute();
      isDropped = true; // NB: Execute returns false for successful create !
    } catch (Exception e) {
      log.error(e);
    }
    return isDropped;
  }

  public Integer insert(IDto dto, Connection dataConnection) {
    Connection conn = dataConnection;
    int retValue = -1;
    retValue = InsertHandler.INSTANCE.insertAtomic(dto, conn);
    return Integer.valueOf(retValue);
  }

  public Integer insertMulti(IDto dto, Connection dataConnection) {
    Connection conn = dataConnection;
    int retValue = -1;

    try {
      String insertQuery = InsertHandler.INSTANCE.createInsertQuery(dto);
      String queryHash = DaoUtils.getHash(insertQuery);

      if (!statementsCache.containsKey(queryHash)) {
        PreparedStatement preparedStatement = DaoUtils.createStatement(conn, insertQuery);
        statementsCache.put(queryHash, preparedStatement);
      }
      retValue =
          InsertHandler.INSTANCE.insertReusePreparedStatement(dto, statementsCache.get(queryHash));
    } catch (SQLException e) {
      log.error(e);
    }
    return Integer.valueOf(retValue);
  }

  public Integer update(IDto dto, IWheresMap wheres, Connection dataConnection) {
    int updated = -1;
    try {
      updated = UpdateHandler.updateLegacy(dto, wheres, dataConnection);
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return updated;
  }

  public Integer delete(IDto dto, IWheresMap wheres, Connection conn) {
    int deletedNo = -1;
    try {
      deletedNo = DeleteHandler.deleteLegacy(dto, wheres, conn);
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return deletedNo;
  }

  public Integer selectCount(IDto requestDto, IWheresMap wheres, Connection conn) {
    String retValue = SelectHandler.selectCountLegacy(requestDto, wheres, conn);
    return Integer.valueOf(retValue);
  }

  public List<IDto> runQuery(String sqlInputString, Connection conn) {
    return SelectHandler.runQuery(sqlInputString, conn);
  }

  @Override
  public boolean createTable(String tableName, List<String> fields, Connection conn) {
    try {
      return TableHandler.createTable(tableName, fields, conn);
    } catch (SQLException e) {
      log.debug(e.getMessage());
      return false;
    }
  }

  @Override
  public IDto descTable(String tableName, Connection conn) {
    return TableHandler.descTable(tableName, conn);
  }

  public String selectIdWhere(
      IDto requestDto,
      IWheresMap wheres,
      Connection dataConnection,
      boolean definedAttributes,
      Integer batchSize) {
    return SelectHandler.selectIdWhere(
        requestDto, wheres, dataConnection, definedAttributes, batchSize);
  }
}

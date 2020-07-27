package net.lulli.shape.data.impl.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.dao.DaoUtils;

public abstract class InsertHandlerInterface<T> {
   
  public int insertReusePreparedStatement(IDto dto, PreparedStatement preparedStatement) {
    int retValue = 0;
    try {
      retValue = DaoUtils.executePreparedStatement(dto, preparedStatement);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return retValue;
  }

  public int insertAtomic(IDto dto, Connection conn) {
    String sqlString = createInsertQuery(dto);
    int retValue = 0;
    try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
      retValue = DaoUtils.executePreparedStatement(dto, pstmt);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return retValue;
  }

  public String createInsertQuery(IDto dto) {
    DaoUtils.removeNullKey(dto);

    boolean isFirstField = true;
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("INSERT INTO ").append(dto.tableName()).append(" (");
    for (String k : dto.keySet()) {
      if (isFirstField) {
        sqlBuilder.append(" ").append(k);
        isFirstField = false;
      } else {
        sqlBuilder.append(", ").append(k);
      }
    }
    sqlBuilder.append(") values ( ");

    boolean isFirstPlaceHolder = true;
    for (String k : dto.keySet()) {
      if (isFirstPlaceHolder) {
        sqlBuilder.append("? ");
        isFirstPlaceHolder = false;
      } else {
        sqlBuilder.append(",? ");
      }
    }
    sqlBuilder.append(")");
    return sqlBuilder.toString();
  }
}

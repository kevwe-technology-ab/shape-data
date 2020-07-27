package net.lulli.shape.data.impl.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IWheresMap;
import net.lulli.shape.data.dao.DaoUtils;
import net.lulli.shape.data.dto.Dto;
import org.apache.log4j.Logger;

public class UpdateHandler {
  static Logger log = Logger.getLogger("UpdateHandler");

  private UpdateHandler() {}

  public static int updateLegacy(IDto dto, IWheresMap wheres, Connection conn) throws SQLException {
    int updatedNo = -1;
    String sql = createUpdateQuery(dto, wheres);

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      updatedNo = DaoUtils.executePreparedStatement(dto, pstmt);
    }
    return updatedNo;
  }

  private static String createUpdateQuery(IDto dto, IWheresMap wheres) {
    Dto requestDto = (Dto) dto;
    Set<String> keys = dto.keySet();

    String sql = "UPDATE " + dto.tableName() + " set ";

    Set<String> whereKeysP1 = wheres.keySet();
    boolean isFirstP1 = true;
    for (String whereKey : whereKeysP1) {
      if (isFirstP1) {
        sql = sql + " WHERE " + whereKey + " = ? ";
        isFirstP1 = false;
      } else {
        sql = sql + "  AND " + whereKey + " = ? ";
      }
    }
    log.debug("SQL=[" + sql + "]");
    return sql;
  }
}

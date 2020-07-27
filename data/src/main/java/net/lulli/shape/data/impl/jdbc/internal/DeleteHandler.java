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

public class DeleteHandler {
  static Logger log = Logger.getLogger("DeleteHandler");

  private DeleteHandler() {}

  public static int deleteLegacy(IDto dto, IWheresMap wheres, Connection conn) throws SQLException {
    int deletedNo = -1;
    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM " + dto.tableName() + " ");
    Set<String> whereKeysP1 = wheres.keySet();
    IDto deleteDto = Dto.of(dto.tableName());
    boolean isFirst = true;
    for (String whereKey : whereKeysP1) {
      if (isFirst) {
        sb.append(" where " + whereKey + " = ? ");
        isFirst = false;
      } else {
        sb.append("  AND " + whereKey + " = ? ");
      }
      if (!whereKey.isBlank()) {
        deleteDto.put(whereKey, wheres.getString(whereKey));
      }
    }

    try (PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {
      deletedNo = DaoUtils.executePreparedStatement(deleteDto, pstmt);
    }
    return deletedNo;
  }
}

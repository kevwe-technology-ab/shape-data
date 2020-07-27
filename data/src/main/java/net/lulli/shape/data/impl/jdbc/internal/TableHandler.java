package net.lulli.shape.data.impl.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.dto.Dto;
import org.apache.log4j.Logger;

public class TableHandler<T> {
  static Logger log = Logger.getLogger("TableHandler");
  public static final TableHandler INSTANCE = new TableHandler();

  private TableHandler() {}

  public static boolean createTable(String tableName, List<String> fields, Connection conn)
      throws SQLException {
    String sqlString;
    boolean isCreated = false;
    StringBuilder sb = new StringBuilder();
    sb.append("CREATE TABLE " + tableName + " (");
    String firstField;
    String nthField;
    Iterator<String> fieldIterator = fields.iterator();
    if (fields.isEmpty()) {
      return false;
    } else {
      firstField = fieldIterator.next();
      sb.append(" " + firstField + " text");
    }
    while (fieldIterator.hasNext()) {
      nthField = fieldIterator.next();
      sb.append(", " + nthField + " text");
    }
    sb.append(" );");
    sqlString = sb.toString();
    log.debug("SQL:[" + sqlString + "]");
    try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
      pstmt.execute();
      isCreated = true; // NB: Execute returns false for successful create !
    }
    return isCreated;
  }

  public static IDto descTable(String tableName, Connection conn) {
    Dto responseDto = null;
    String sqlString = "select * from ? limit 1";

    log.debug("sqlString = [" + sqlString + "]");
    try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
      pstmt.setString(1, tableName);

      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          log.debug("rs.next()");
          responseDto = Dto.of(tableName);
          Set<String> keys;

          ResultSetMetaData md = rs.getMetaData();
          keys = new TreeSet<String>();
          for (int i = 1; i <= md.getColumnCount(); i++) {
            keys.add(md.getColumnLabel(i));
          }
          Iterator<String> keysIterator2 = keys.iterator();
          String key;
          String value;
          while (keysIterator2.hasNext()) {
            key = keysIterator2.next();
            value = rs.getString(key);
            responseDto.put(key, rs.getString(key));
          }
        }
        if (null != rs) {
          rs.close();
        }
      }

      if (null != pstmt) {
        pstmt.close();
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return responseDto;
  }
}

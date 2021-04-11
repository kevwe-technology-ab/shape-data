package net.lulli.shape.data.impl.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IWheresMap;
import net.lulli.shape.data.dto.Dto;
import net.lulli.shape.data.dto.Wheres;
import org.apache.log4j.Logger;

public class SelectHandler {
  static Logger log = Logger.getLogger("SelectHandler");

  private SelectHandler() {}

  public static List<IDto> runQuery(String sqlInputString, Connection conn) {
    List<IDto> listOfDto = new ArrayList<>();
    PreparedStatement pstmt = null;
    Dto responseDto = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(sqlInputString);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        responseDto = Dto.of(Dto.DEFAULT_RESULTSET_NAME);
        Set<String> keys;

        ResultSetMetaData md = rs.getMetaData();
        keys = new TreeSet<>(); // < --------------------------- This guy changes the ordering
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
        listOfDto.add(responseDto);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
      try {
        pstmt.close();
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    return listOfDto;
  }

  public static String selectCountLegacy(IDto requestDto, IWheresMap wheres, Connection conn) {
    PreparedStatement pstmt = null;
    Dto responseDto = null;
    ResultSet rs = null;
    String CONTEGGIO = "";
    try {
      String sqlString = "SELECT count(*) as CNTN FROM " + requestDto.tableName() + " WHERE ";
      Set<String> whereFields = ((Wheres) wheres).keySet();
      int idx = 0;
      for (String where : whereFields) {
        if (idx > 0) {
          sqlString += "AND ";
        } else {
          sqlString += " ";
        }
        idx++;
        if (null == wheres.get(where) || (wheres.get(where).toString().equals(""))) {
          sqlString += " " + where + " is null ";
        } else {
          sqlString += " " + where;
          sqlString += " = ? ";
        }
      }
      log.debug("sqlString = [" + sqlString + "]");
      pstmt = conn.prepareStatement(sqlString);
      int paramIdx = 1;
      String whereValue;
      Set<String> whereFields_2ndRound = ((Wheres) wheres).keySet();
      for (String where : whereFields_2ndRound) {
        try {
          if (null == wheres.get(where) || (wheres.get(where).toString().equals(""))) {
            log.debug("skipping null value for field: [" + where + "]");
          } else {
            whereValue = wheres.get(where).toString();
            pstmt.setString(paramIdx, whereValue);
            paramIdx++;
          }
        } catch (Exception e) {
          log.error("Could not set where condition");
        }
      }
      rs = pstmt.executeQuery();
      while (rs.next()) {
        CONTEGGIO = rs.getString("CNTN");
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      try {
        if (null != rs) rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
      try {
        if (null != pstmt) pstmt.close();
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    return CONTEGGIO;
  }

  public List select(
      Dto requestDto, Wheres wheres, boolean definedAttributes, Connection dataConnection) {
    Connection conn = (java.sql.Connection) dataConnection;

    List listOfDto = new ArrayList();
    PreparedStatement pstmt = null;
    Dto responseDto = null;
    ResultSet rs = null;
    try {
      String sqlString = "SELECT * FROM " + requestDto.tableName() + " WHERE ";
      Set<String> whereFields = wheres.keySet();
      int idx = 0;
      for (String where : whereFields) {
        if (idx > 0) {
          sqlString += "AND ";
        } else {
          sqlString += " ";
        }
        idx++;
        if (null == wheres.get(where) || (wheres.get(where).toString().equals(""))) {
          sqlString += " " + where + " is null ";
        } else {
          sqlString += " " + where;
          sqlString += " = ? ";
        }
      }

      log.debug("sqlString = [" + sqlString + "]");
      pstmt = conn.prepareStatement(sqlString);
      int paramIdx = 1;
      String whereValue;
      Set<String> whereFields_2ndRound = wheres.keySet();
      for (String where : whereFields_2ndRound) {
        try {
          if (null == wheres.get(where) || (wheres.get(where).toString().equals(""))) {
            log.debug("skipping null value for field: [" + where + "]");
          } else {
            whereValue = wheres.get(where).toString();
            pstmt.setString(paramIdx, whereValue);
            paramIdx++;
          }
        } catch (Exception e) {
          log.error("Could not set where condition");
        }
      }

      rs = pstmt.executeQuery();

      while (rs.next()) {
        log.debug("rs.next()");
        responseDto = Dto.of(requestDto.tableName());
        Set<String> keys;
        if (definedAttributes) {
          // requestDto contiene i nomi delle colonne da stampare
          keys = requestDto.keySet();
        } else {
          ResultSetMetaData md = rs.getMetaData();
          keys = new TreeSet<String>();
          for (int i = 1; i <= md.getColumnCount(); i++) {
            keys.add(md.getColumnLabel(i));
            log.debug("FIELD_NAME:[" + md.getColumnLabel(i) + "]");
          }
        }
        Iterator<String> keysIterator2 = keys.iterator();
        String key;
        String value;
        while (keysIterator2.hasNext()) {
          key = keysIterator2.next();
          value = rs.getString(key);
          responseDto.put(key, rs.getString(key));
          idx++;
        }
        listOfDto.add(responseDto);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      if (null != rs) {
        try {
          rs.close();
        } catch (SQLException e) {
          log.error(e.getMessage());
        }
      }
      try {
        if (null != pstmt) pstmt.close();
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    return listOfDto;
  }

  public static String selectIdWhere(
      IDto requestDto,
      IWheresMap wheres,
      Connection dataConnection,
      boolean definedAttributes,
      Integer batchSize) {
    Connection conn = (java.sql.Connection) dataConnection;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String id = null;
    try {
      String sqlString = "SELECT id FROM " + requestDto.tableName() + " WHERE ";
      Set<String> whereFields = ((Wheres) wheres).keySet();
      int idx = 0;
      for (String where : whereFields) {
        if (idx > 0) {
          sqlString += "AND ";
        } else {
          sqlString += " ";
        }
        idx++;
        if (null == wheres.get(where) || (wheres.get(where).toString().equals(""))) {
          sqlString += " " + where + " is null ";
        } else {
          sqlString += " " + where;
          sqlString += " = ? ";
        }
      }
      if (null != batchSize) {
        sqlString += " limit " + batchSize.toString();
      }
      log.debug("sqlString = [" + sqlString + "]");
      pstmt = conn.prepareStatement(sqlString);
      int paramIdx = 1;
      String whereValue;
      Set<String> whereFields_2ndRound = ((Wheres) wheres).keySet();
      for (String where : whereFields_2ndRound) {
        try {
          if (null == wheres.get(where) || (wheres.get(where).toString().equals(""))) {
            log.debug("skipping null value for field: [" + where + "]");
          } else {
            whereValue = wheres.get(where).toString();
            pstmt.setString(paramIdx, whereValue);
            paramIdx++;
          }
        } catch (Exception e) {
          log.error("Could not set where condition");
        }
      }
      rs = pstmt.executeQuery();

      while (rs.next()) {
        id = rs.getString("id");
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    } finally {
      try {
        if (null != rs) rs.close();
      } catch (SQLException e) {
        log.error(e.getMessage());
      }
      try {
        if (null != pstmt) pstmt.close();
      } catch (Exception e) {
        log.error(e.getMessage());
      }
    }
    return id;
  }
}

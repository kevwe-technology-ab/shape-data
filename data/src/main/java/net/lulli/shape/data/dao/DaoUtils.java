package net.lulli.shape.data.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Formatter;

import org.apache.log4j.Logger;

import net.lulli.shape.data.api.IDto;


public class DaoUtils {
  private DaoUtils() {
  }

  static Logger log = Logger.getLogger("DaoUtil");

  public static void removeNullKey(IDto dto) {
    if (dto.containsKey(null)) {
      dto.remove(null);
    }
  }

  public static PreparedStatement createStatement(Connection conn, String sqlString) throws SQLException {
    return conn.prepareStatement(sqlString);
  }

  public static String getHash(String password) {
    String sha1 = "";
    try {
      MessageDigest crypt = MessageDigest.getInstance("SHA-1");
      crypt.reset();
      crypt.update(password.getBytes("UTF-8"));
      sha1 = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return sha1;
  }

  private static String byteToHex(final byte[] hash) {
    Formatter formatter = new Formatter();
    for (byte b : hash) {
      formatter.format("%02x", b);
    }
    String result = formatter.toString();
    formatter.close();
    return result;
  }

  public static int executePreparedStatement(IDto dto, PreparedStatement pstmt) throws SQLException {
    int paramIndex = 1;
    for (String key : dto.keySet()) {
      Object kvalue = dto.get(key);
      String value = "";
      if (null != kvalue) {
        value = kvalue.toString();
      }
      pstmt.setString(paramIndex, value);
      paramIndex++;
    }
    return pstmt.executeUpdate();
  }
}

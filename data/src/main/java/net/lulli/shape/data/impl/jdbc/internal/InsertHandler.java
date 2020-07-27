package net.lulli.shape.data.impl.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import net.lulli.shape.data.dao.DaoUtils;
import net.lulli.shape.data.dto.Dto;

public class InsertHandler extends InsertHandlerInterface<Connection> {
  static Logger log = Logger.getLogger("InsertHandler");

  public static final InsertHandler INSTANCE = new InsertHandler();

  private InsertHandler() {
  }
  
}

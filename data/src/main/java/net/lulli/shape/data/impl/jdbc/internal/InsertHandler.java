package net.lulli.shape.data.impl.jdbc.internal;

import java.sql.Connection;
import org.apache.log4j.Logger;

public class InsertHandler extends InsertHandlerInterface<Connection> {
  static Logger log = Logger.getLogger("InsertHandler");

  public static final InsertHandler INSTANCE = new InsertHandler();

  private InsertHandler() {}
}

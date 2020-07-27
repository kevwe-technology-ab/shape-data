package net.lulli.shape.data.api.roles;

import java.util.List;
import net.lulli.shape.data.api.IDto;

public interface IDaoAdmin<T> {
  public IDto descTable(String tableName, T dbConnection);

  public boolean dropTable(String tableName, T dbConnection);

  public boolean createTable(String tableName, List<String> fields, T dbConnection);
}

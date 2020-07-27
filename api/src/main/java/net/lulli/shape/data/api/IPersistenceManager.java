package net.lulli.shape.data.api;

import java.util.List;

public interface IPersistenceManager extends IDialect {

  public Integer insert(IDto dto);

  public Integer update(IDto dto, IWheresMap wheres);

  public Integer delete(IDto dto, IWheresMap wheres);

  public Integer save(IDto dto, IWheresMap wheres);

  public Integer selectCount(IDto requestDto, IWheresMap wheres, boolean definedAttributes);

  public List<String> descTable(String tableName);

  public boolean createTable(String tableName, List<String> fields);

  public boolean dropTable(String tableName);

  public void setSQLDialect(String sqlDialect);

  public List<IDto> query(String sql, String ... args);

  public int execute(String sql, String ... args);

}

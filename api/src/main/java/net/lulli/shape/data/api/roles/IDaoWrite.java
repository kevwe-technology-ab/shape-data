package net.lulli.shape.data.api.roles;

import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IWheresMap;

public interface IDaoWrite<T> {
  public Integer insert(IDto dto, T dbConnection);

  public Integer delete(IDto dto, IWheresMap wheres, T dbConnection);

  public Integer update(IDto dto, IWheresMap wheres, T dbConnection);
}

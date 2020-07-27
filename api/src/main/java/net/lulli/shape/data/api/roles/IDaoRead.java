package net.lulli.shape.data.api.roles;

import java.util.List;

import net.lulli.shape.data.api.IDto;

public interface IDaoRead<T> {
  public List<IDto> runQuery(String sqlInputString, T dbConnection);

  public String getIdField();
}

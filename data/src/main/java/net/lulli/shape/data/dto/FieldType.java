package net.lulli.shape.data.dto;

import net.lulli.shape.data.api.IDto;

public interface FieldType {
  public Boolean check(IDto dto, String fieldName);
}

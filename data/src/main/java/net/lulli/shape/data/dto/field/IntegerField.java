package net.lulli.shape.data.dto.field;

import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.dto.FieldType;

public class IntegerField implements FieldType {
  public IntegerField() {}

  @Override
  public Boolean check(IDto dto, String fieldName) {
    if (Integer.valueOf(dto.get(fieldName)) < Integer.MAX_VALUE) {
      return true;
    }
    return false;
  }
}

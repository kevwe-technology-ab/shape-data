package net.lulli.shape.data.dto.field;

import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.dto.FieldType;

public final class TextField implements FieldType{
  private final Integer maxLength;
  
  public TextField(Integer maxLength) {
    this.maxLength = maxLength;
  }

  @Override
  public Boolean check(IDto dto, String fieldName) {
    if (dto.get(fieldName).length() < maxLength) {
      return true;
    }
    return false;
  }
}

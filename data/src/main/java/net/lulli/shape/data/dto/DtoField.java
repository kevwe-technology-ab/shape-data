package net.lulli.shape.data.dto;

public final class DtoField{
  private final String name;
  private final FieldType type;
  private final Boolean isNullable;
  
  public DtoField(String name, FieldType type) {
    this.name = name;
    this.type = type;
    this.isNullable = true;
  }
  
  public DtoField(String name, FieldType type, Boolean isNullable) {
    this.name = name;
    this.type = type;
    this.isNullable = isNullable;
  }

  public String getName() {
    return name;
  }

  public FieldType getType() {
    return type;
  }

  public Boolean getIsNullable() {
    return isNullable;
  }
  
}

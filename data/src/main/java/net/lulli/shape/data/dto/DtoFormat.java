package net.lulli.shape.data.dto;

import java.util.ArrayList;
import java.util.List;
import net.lulli.shape.data.dto.field.IntegerField;
import net.lulli.shape.data.dto.field.TextField;

public class DtoFormat {
  private static String tableName;
  private String recordType;
  private String id;
  private List<DtoField> fieldList;

  private DtoFormat() {
    fieldList = new ArrayList<DtoField>();
  }

  public DtoFormat(String tableName, List<DtoField> fieldList) {
    this.setTableName(tableName);
    this.fieldList = fieldList;
  }

  public static DtoFormat of(String tableName) {
    DtoFormat dtoFormat = new DtoFormat();
    dtoFormat.setTableName(tableName);
    return dtoFormat;
  }

  public DtoFormat withField(DtoField field) {
    fieldList.add(field);
    return this;
  }

  public DtoFormat withId(String fieldName) {
    id = fieldName;
    return this;
  }

  public DtoFormat withText(String fieldName, Integer textLength) {
    fieldList.add(new DtoField(fieldName, new TextField(textLength)));
    return this;
  }

  public DtoFormat withInteger(String fieldName, Integer textLength) {
    fieldList.add(new DtoField(fieldName, new IntegerField()));
    return this;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public static String getTableName() {
    return tableName;
  }

  public String getRecordType() {
    return recordType;
  }

  public List<DtoField> getFieldList() {
    return fieldList;
  }
}

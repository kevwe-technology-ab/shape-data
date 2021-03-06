package net.lulli.shape.data.dto;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.lulli.shape.data.api.IDto;

public class Dto implements IDto {
  public static final String DEFAULT_RESULTSET_NAME = "resultset";
  public final String table;
  public String recordType;
  public String idField;

  private final LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();

  private Dto(String tableName) {
    this.table = tableName;
  }

  public String tableName() {
    return this.table;
  }

  // TODO
  public static Dto fromFormat(DtoFormat dtoFormat) {
    return new Dto(dtoFormat.getTableName());
  }

  public static Dto of(String tableName) {
    return new Dto(tableName);
  }
  
  public void put(IDto dto) {
    hashMap.putAll(dto.getMap());
  }

  public void put(String key, String value) {
    hashMap.put(key, value);
  }

  public void put(String key, Object value) {
    hashMap.put(key, value);
  }

  public String get(String key) {
    Object o = hashMap.get(key);
    if (null != o) {
      return o.toString();
    } else {
      return null;
    }
  }

  public boolean containsKey(String key) {
    return hashMap.containsKey(key);
  }

  public void remove(String key) {
    hashMap.remove(key);
  }

  public Set<String> keySet() {
    return hashMap.keySet();
  }

  public Collection<Object> values() {
    return hashMap.values();
  }
  
  @Override
  public boolean containsKey(Object key) {
    return hashMap.containsKey(key);
  }

  public Map<String, Object> getMap() {
    return hashMap;
  }
}

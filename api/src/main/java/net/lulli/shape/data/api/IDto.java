package net.lulli.shape.data.api;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDto extends Cloneable {
  String tableName();

  void put(String key, String value);
  
  void put(IDto dto);

  String get(String key);

  Set<String> keySet();
  
  Collection<Object> values();

  boolean containsKey(Object key);

  void remove(String key);
  
  public Map<String, Object> getMap();
}

package net.lulli.shape.data.api;

import java.util.Set;

public interface IDto extends Cloneable {
  String tableName();

  void put(String key, String value);

  String get(String key);

  Set<String> keySet();

  boolean containsKey(Object key);

  void remove(String key);
}

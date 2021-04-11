package net.lulli.shape.data.dto;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import net.lulli.shape.data.api.IWheresMap;

public class Wheres implements IWheresMap {

  private final Hashtable<String,String> hashtable = new Hashtable<>();

  @Override
  public String getString(String key) {
    return get(key).toString();
  }

  @Override
  public int size() {
    return hashtable.size();
  }

  @Override
  public boolean isEmpty() {
    return hashtable.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return hashtable.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return hashtable.containsValue(value);
  }

  @Override
  public String get(Object key) {
    return hashtable.get(key);
  }

  @Override
  public String remove(Object key) {
    return hashtable.remove(key);
  }

  @Override
  public void putAll(Map m) {
    hashtable.putAll(m);
  }

  @Override
  public void clear() {
    hashtable.clear();
  }

  @Override
  public Set keySet() {
    return hashtable.keySet();
  }

  @Override
  public Collection values() {
    return hashtable.values();
  }

  @Override
  public Set entrySet() {
    return hashtable.entrySet();
  }

  @Override
  public String put(String key, String value) {
    return hashtable.put(key, value);
  }
}

package net.lulli.shape.data.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.lulli.shape.data.api.IDto;

public final class XDto implements IDto {
  public final String tableName;
  private final Map<String, String> fields;
  private final Map<String, String> payload;

  public XDto(String tablename, Map<String, String> fields) {
    this.tableName = tablename;
    this.fields = fields;

    payload = new HashMap<String, String>();
  }

  public String tableName() {
    return this.tableName;
  }

  public Map<String, String> fields() {
    return Collections.unmodifiableMap(fields);
  }

  public static class Builder {
    private String tablename;
    private Map<String, String> fields = new HashMap<>();

    public Builder withName(String tablename) {
      this.tablename = tablename;
      return this;
    }

    public Builder addField(String key, String value) {
      fields.put(key, value);
      return this;
    }

    public XDto build() {
      return new XDto(this.tablename, this.fields);
    }
  }

  @Override
  public void put(String key, String value) {
    payload.put(key, value);
  }

  @Override
  public String get(String key) {
    return (payload.get(key) == null) ? null : payload.get(key).toString();
  }

  @Override
  public Set<String> keySet() {
    return payload.keySet();
  }

  @Override
  public boolean containsKey(Object key) {
    return payload.containsKey(key);
  }

  @Override
  public void remove(String key) {
    payload.remove(key);
  }
}

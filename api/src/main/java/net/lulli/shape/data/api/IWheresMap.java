package net.lulli.shape.data.api;

import java.util.Map;

public interface IWheresMap extends Map<String,String> {
  public String getString(String key);
}

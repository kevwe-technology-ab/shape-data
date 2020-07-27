package net.lulli.shape.data.helper.generic;

import java.util.Hashtable;

public class TypeHelper {

  private TypeHelper() {}

  public static Hashtable<String, String> add(Hashtable<String, String> h, String k, String v) {
    if (h == null) {
      h = new Hashtable<String, String>();
    }
    h.put(k, v);
    return h;
  }
}

package net.lulli.shape.data.helper;

public final class StrictCheck {
  public static boolean isNotNull(String input) {
    if (!Check.isNotNull(input)) {
      throw new IllegalArgumentException("with argument:" + input);
    } else {
      return true;
    }
  }

  public static boolean isNull(String input) {
    if (!Check.isNull(input)) {
      throw new IllegalArgumentException("with argument:" + input);
    } else {
      return true;
    }
  }

  public static boolean isEmpty(String input) {
    if (!Check.isEmpty(input)) {
      throw new IllegalArgumentException("with argument:" + input);
    } else {
      return true;
    }
  }

  public static boolean isNotEmpty(String input) {
    if (!Check.isNotEmpty(input)) {
      throw new IllegalArgumentException("with argument:" + input);
    } else {
      return true;
    }
  }

  public static boolean isInt(String input) {
    if (!Check.isInt(input)) {
      throw new IllegalArgumentException("cannot parse Integer:" + input);
    }
    return true;
  }

  public static boolean isLong(String input) {
    if (!Check.isLong(input)) {
      throw new IllegalArgumentException("cannot parse Long:" + input);
    }
    return true;
  }

  public static boolean isDouble(String input) {
    if (!Check.isDouble(input)) {
      throw new IllegalArgumentException("cannot parse Double:" + input);
    }
    return true;
  }

  public static boolean hasMaxLength(String input, int size) {
    if (Check.hasMaxLength(input, size)) {
      return true;
    }
    return false;
  }

  public static boolean hasMinLength(String input, int size) {
    if (Check.hasMinLength(input, size)) {
      return true;
    }
    return false;
  }

  public static boolean hasLength(String input, int size) {
    if (Check.hasLength(input, size)) {
      return true;
    }
    return false;
  }

  public static boolean isNumeric(String input) {
    if (Check.isNumeric(input)) {
      return true;
    }
    return false;
  }

}

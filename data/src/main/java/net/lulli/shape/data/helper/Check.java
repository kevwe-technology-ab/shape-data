package net.lulli.shape.data.helper;

public final class Check // implements CheckInterface
{
  private Check() {
  }

  public static boolean isNull(String input) {
    return (input == null) ? true : false;
  }

  public static boolean isNotNull(String input) {
    return (input == null) ? false : true;
  }

  public static boolean isEmpty(String input) {
    return (isNull(input) || (input.equals(""))) ? true : false;
  }

  public static boolean isNotEmpty(String input) {
    return (isNotNull(input) && (!input.equals(""))) ? true : false;
  }

  public static boolean isInt(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  public static boolean isLong(String input) {
    try {
      Long.parseLong(input);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  public static boolean isDouble(String input) {
    try {
      Double.parseDouble(input);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  public static boolean isNumeric(String input) {
    return ((isInt(input)) || (isLong(input)) || (isDouble(input))) ? true : false;
  }

  public static boolean hasMaxLength(String input, int size) {
    if (isNull(input)) {
      return false;
    }
    return (input.length() <= size) ? true : false;
  }

  public static boolean hasMinLength(String input, int size) {
    if (isNull(input)) {
      return false;
    }
    return (input.length() >= size) ? true : false;
  }

  public static boolean hasLength(String input, int size) {
    if (!isNotNull(input)) {
      return false;
    }
    return input.length() == size ? true : false;
  }
}

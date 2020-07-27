package net.lulli.shape.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import net.lulli.shape.data.api.IDto;


public class DtoUtils {
  static Logger log = Logger.getLogger("DtoUtils");

  private DtoUtils() {

  }

  public static List<String> dtoToCsvRows(List<IDto> listOfDto, boolean containsHeader, String fieldSeparator,
      String recordSeparator) {
    ArrayList<String> righe = new ArrayList<>();
    String riga = null;
    boolean headerToAdd = true;
    String headerRow = "";
    if (containsHeader) {

      if (headerToAdd) {
        try {
          IDto firstDto = listOfDto.get(0);
          Set<String> fieldNames = firstDto.keySet();

          for (String filedName : fieldNames) {
            headerRow += filedName;
            headerRow += fieldSeparator;
          }
          headerRow += recordSeparator;
          righe.add(headerRow);
          log.debug("==============================================================");
          log.debug(headerRow);
          headerToAdd = false;
        } catch (Exception e) {
          log.error("listOfDto does not contain elements" + e);
          return new ArrayList<>(); // Empty
        }
      }
    }
    for (IDto dto : listOfDto) {
      riga = new String("");
      Set<String> keys = dto.keySet();

      for (String key : keys) {
        riga += dto.get(key);
        riga += fieldSeparator;
      }
      riga += recordSeparator;
      righe.add(riga);
    }
    return righe;
  }

  public static String dtoToCsvSingleRow(IDto dto, String fieldSeparator, String recordSeparator) {
    String riga = "";
    Set<String> keys = dto.keySet();
    for (String key : keys) {
      riga += dto.get(key);
      riga += fieldSeparator;
    }
    riga += recordSeparator;
    return riga;
  }

  public static String stringListToCsvSingleRow(List<String> list, String fieldSeparator, String recordSeparator) {
    String riga = "";
    for (String field : list) {
      riga += field;
      riga += fieldSeparator;
    }
    riga += recordSeparator;
    return riga;
  }

  public static String dtoToCsv(List<IDto> listOfDto, String fieldSeparator, String recordSeparator) {
    String csv = "";
    String riga = "";
    for (IDto dto : listOfDto) {
      Set<String> keys = dto.keySet();
      for (String key : keys) {
        riga += dto.get(key);
        riga += fieldSeparator;
      }
      riga += recordSeparator;
      csv += riga;
    }
    return csv;
  }

  public static IDto csvToDto(String row, List<String> fields, String tableName, String fieldSeparator,
      String recordSeparator) {
    IDto singleRow = null;
    try {
      singleRow = Dto.of(tableName);
      String rows[] = row.split(fieldSeparator);

      String key;
      String value;
      for (int i = 0; i < (rows.length - 1); i++) {
        key = fields.get(i);
        value = rows[i];
        log.trace("i=" + i + ", key=" + key + ", value=" + value);
        if (i == (rows.length - 1)) {
          log.debug("value.length() =" + value.length());
          log.debug("lineSeparator.length() =" + recordSeparator.length());
          value = value.substring(0, value.length() - recordSeparator.length());
        } else {
          singleRow.put(key, value);
        }
        log.debug("key=[" + key + "] value: [" + value + "]" + "index: [" + i + "]");
      }

    } catch (Exception e) {
      log.error("1" + e);
    }
    return singleRow;
  }

  public static List<String> csvFieldsToList(String row, String fieldSeparator, String recordSeparator) {
    List<String> fields = null;
    try {

      fields = new ArrayList<>();
      String rows[] = row.split(fieldSeparator);
      String value;
      for (int i = 0; i < (rows.length - 1); i++) {
        value = rows[i];
        if (i == (rows.length - 1)) {
          log.debug("lineSeparator.length() =" + recordSeparator.length());
          value = value.substring(0, value.length() - recordSeparator.length());
          log.debug("AFTER");
        }
        fields.add(value);
      }
    } catch (Exception e) {
      log.error(e);
    }
    return fields;
  }

}

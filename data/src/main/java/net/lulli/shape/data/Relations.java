package net.lulli.shape.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IPersistenceManager;
import net.lulli.shape.data.dto.Dto;

public class Relations {
  private final IPersistenceManager persistenceManager;
  
  private Relations(IPersistenceManager persistenceManager) {
    this.persistenceManager = persistenceManager;
  }
  
  public static Relations with(IPersistenceManager persistenceManager) {
    return new Relations(persistenceManager);
  }

  public List<IDto> join(String A, String B, Map fields) {
    List<IDto> retList = new ArrayList<>();
    Collection<String> sourceFields = fields.keySet();

    List<IDto> sourceKeysList = persistenceManager.query("select %s from %s", 
        commaSeparated(sourceFields), 
        A);
    
    //TODO: squash to DISTINCT
    
    for (IDto resultPair : sourceKeysList) {
      
      String q2 = String.format("select * from %s where %s", B, commaKeyValuePairs(new ArrayList(fields.values()), new ArrayList(resultPair.values())));
      List<IDto> listA = persistenceManager.query(q2, "");
      String q3 = String.format("select * from %s where %s", A, commaKeyValuePairs(resultPair));
      List<IDto> listB = persistenceManager.query(q3, "");

      if ((null != listA)
          && (null != listB)) {
        List<IDto> combinedList = combineLists(listA, listB);
        retList.addAll(combinedList);
      }
    }

    return retList;
  }

  private List<IDto> combineLists(List<IDto> listA, List<IDto> listB) {
    List<IDto> combinedList = new ArrayList();
    for (IDto a : listA) {
      for (IDto b : listB) {
        a.put(b);
        combinedList.add(a);
      }
    }
    return combinedList;
  }

  private static String commaSeparated(Collection<String> values) {
    boolean isFirst = true;
    StringBuilder sb = new StringBuilder();
    for (String value : values) {
      if (!isFirst)
        sb.append(",");
      sb.append(value);
      isFirst = false;
    }
    return sb.toString();
  }

  private String commaKeyValuePairs(IDto fields) {
    Collection<String> sourceFields = fields.keySet();
    //Collection targetFields = fields.values();
    boolean isFirst = true;
    StringBuilder sb = new StringBuilder();
    for (String key : sourceFields) {
      if (!isFirst)
        sb.append(" and ");
      sb.append(key + "='" + fields.get(key) + "'");
      isFirst = false;
    }
    return sb.toString();
  }

  private String commaKeyValuePairs(List<String> fieldNameList, List<String> fieldValueList) {
    boolean isFirst = true;
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < fieldNameList.size(); i++) {
      if (!isFirst)
        sb.append(" and ");
      sb.append(fieldNameList.get(i) + "='" + fieldValueList.get(i) + "'");
      isFirst = false;
    }
    return sb.toString();
  }
}

package net.lulli.shape.data.impl.cassandra;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;

import net.lulli.shape.data.api.IDao;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IWheresMap;
import net.lulli.shape.data.impl.jdbc.internal.TableHandler;

public class CassandraDao implements IDao<CqlSession> {

  @Override
  public IDto descTable(String tableName, CqlSession dbConnection) {
    // dbConnection.execute("");
    throw new IllegalStateException("Not Implemented");
  }

  @Override
  public boolean dropTable(String tableName, CqlSession dataConnection) {
    throw new IllegalStateException("not implemented");
    /*
    boolean isDropped = false;
    String dropSql = String.format("DROP TABLE %s", tableName);
    try {
      dataConnection.execute(dropSql);
      isDropped = true;
    } catch (Exception e) {
      // Do nothing
    }
    return isDropped;
    */
  }

  @Override
  public boolean createTable(String tableName, List<String> fields, CqlSession dbConnection) {
    throw new IllegalStateException("not implemented");
  }
  
  @Override
  public List<IDto> runQuery(String sqlInputString, CqlSession dbConnection) {
    throw new IllegalStateException("Not Implemented");
  }

  @Override
  public String getIdField() {
    throw new IllegalStateException("Not Implemented");
  }

  @Override
  public Integer insert(IDto dto, CqlSession dbConnection) {
    throw new IllegalStateException("Not Implemented");
  }

  @Override
  public Integer delete(IDto dto, IWheresMap wheres, CqlSession dbConnection) {
    throw new IllegalStateException("Not Implemented");
  }

  @Override
  public Integer update(IDto dto, IWheresMap wheres, CqlSession dbConnection) {
    throw new IllegalStateException("Not Implemented");
  }

//  private void createKeyspace(Session session) {
//      
//      String keyspaceName, String replicationStrategy, int replicationFactor) {
//      StringBuilder sb = 
//        new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
//          .append(keyspaceName).append(" WITH replication = {")
//          .append("'class':'").append(replicationStrategy)
//          .append("','replication_factor':").append(replicationFactor)
//          .append("};");
//             
//        String query = sb.toString();
//        ResultSet execute = session.execute(query);
//    }
//  

}
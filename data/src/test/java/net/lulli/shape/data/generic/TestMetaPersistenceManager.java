package net.lulli.shape.data.generic;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import net.lulli.shape.data.AbstractPersistenceManager;
import net.lulli.shape.data.api.IDialect;
import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.api.IPersistenceManager;
import net.lulli.shape.data.dao.DaoFactory;
import net.lulli.shape.data.dto.Dto;
import net.lulli.shape.data.dto.Wheres;
import net.lulli.shape.data.helper.DbConnectionParameters;
import net.lulli.shape.data.helper.generic.GenericPersistenceManager;
import net.lulli.shape.data.impl.jdbc.Dao;

public class TestMetaPersistenceManager {

  private static IPersistenceManager getPersistanceManager() throws IOException {

    File tempFile = File.createTempFile("metadao-test-XXXXX", System.getenv("java.io.tmpdir"));

    DbConnectionParameters.Builder builder = new DbConnectionParameters.Builder();
    builder
    .setDriverClassName("org.sqlite.JDBC")
    .setDialect(IDialect.SQLITE)
    .setPassword("")
    .setUser("")
    .setJdbcUrl("jdbc:sqlite:" + tempFile.getAbsolutePath());

    return new GenericPersistenceManager(builder.build());
  }

  @Test
  public void testPersistenceManager() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    Assert.assertNotNull(pm);
  }

  @Test
  public void testCreateAndDropTable() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    String tableName = "randomName";
    Assert.assertTrue(pm.createTable(tableName, Arrays.asList("one", "two", "three")));
    Assert.assertTrue(pm.dropTable(tableName));
  }

  private void insertHelp(IPersistenceManager pm, String tableName, String one, String two, String three) {
    IDto insertDto = Dto.of(tableName);
    insertDto.put("one", one);
    insertDto.put("two", two);
    insertDto.put("three", three);
    Assert.assertEquals(Integer.valueOf(1), pm.insert(insertDto));
  }

  private void insertHelpMulti(Connection connection, AbstractPersistenceManager pm, String tableName, String one,
      String two, String three) {
    IDto insertDto = Dto.of(tableName);
    insertDto.put("one", one);
    insertDto.put("two", two);
    insertDto.put("three", three);
    //
    Assert.assertEquals(Integer.valueOf(1), pm.insert(insertDto));
  }

  @Test
  public void testInsert() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    String tableName = "tinsert";
    pm.createTable(tableName, Arrays.asList("one", "two", "three"));
    insertHelp(pm, tableName, "11111111111111111", "222222222222222", "333");
    pm.dropTable(tableName);
  }

  @Test
  public void testInsertMulti() throws IOException {
    String tableName = "tinsertmulti";

    AbstractPersistenceManager pm = (AbstractPersistenceManager)getPersistanceManager();
    Connection connection = pm.getDbConnectionManager().getConnection();
    
    Dao dao =  DaoFactory.getNewMetaDao(tableName);
    pm.createTable(tableName, Arrays.asList("one", "two", "three"));
    
    dao.insertMulti(prepareDto(tableName, "onem", "twom", "threem"), connection);
    dao.insertMulti(prepareDto(tableName, "onen", "twon", "threen"), connection);
    dao.insertMulti(prepareDto(tableName, "oneo", "twoo", "threeo"), connection);
    dao.insertMulti(prepareDto(tableName, "onep", "twop", "threep"), connection);
    
    pm.dropTable(tableName);
  }

  private IDto prepareDto(String tableName, String one, String two, String three) {
    IDto dto1 =  Dto.of(tableName);
    dto1.put("one", one);
    dto1.put("two", two);
    dto1.put("three", three);
    return dto1;
  }

  @Test
  public void testInsertThree() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    String tableName = "tinsert";
    pm.createTable(tableName, Arrays.asList("one", "two", "three"));
    insertHelp(pm, tableName, "11111111111111111", "222222222222222", "333");
    insertHelp(pm, tableName, "xxx", "yy", "zz");
    insertHelp(pm, tableName, "www", "ggg", "uuuu");
    List<IDto> dtoList = pm.query("select * from " + tableName);
    Assert.assertEquals(3, dtoList.size());
    pm.dropTable(tableName);
  }

  @Test
  public void testUpdate() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    String tableName = "tupdate";
    pm.createTable(tableName, Arrays.asList("one", "two", "three"));
    insertHelp(pm, tableName, "11111111111111111", "222222222222222", "333");
    insertHelp(pm, tableName, "xxx", "yy", "zz");
    insertHelp(pm, tableName, "www", "ggg", "uuuu");
    List<IDto> dtoList = pm.query("select * from " + tableName);
    Assert.assertEquals(3, dtoList.size());
    pm.dropTable(tableName);
  }

  @Test
  public void testDelete() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    String tableName = "tdelete";
    pm.createTable(tableName, Arrays.asList("one", "two", "three"));
    insertHelp(pm, tableName, "11111111111111111", "222222222222222", "333");
    insertHelp(pm, tableName, "xxx", "yy", "zz");
    insertHelp(pm, tableName, "www", "ggg", "uuuu");

    Wheres wheresMap = new Wheres();
    wheresMap.put("one", "11111111111111111");

    IDto delendaValue = Dto.of(tableName);
    pm.delete(delendaValue, wheresMap);
    Assert.assertEquals(2, pm.query("select * from " + tableName).size());

    wheresMap.put("one", "xxx");
    pm.delete(delendaValue, wheresMap);

    Assert.assertEquals(1, pm.query("select * from " + tableName).size());
    pm.dropTable(tableName);
  }
  
  
  @Test
  public void readFormatted() throws IOException {
    IPersistenceManager pm = getPersistanceManager();
    String tableName = "tinserttwo";
    pm.createTable(tableName, Arrays.asList("one", "two", "three"));
    insertHelp(pm, tableName, "11111111111111111", "222222222222222", "333");
    insertHelp(pm, tableName, "xxx", "yy", "zz");
    insertHelp(pm, tableName, "www", "ggg", "uuuu");
    List<IDto> dtoList = pm.query("select * from  %s" , tableName);
    Assert.assertEquals(3, dtoList.size());
    pm.dropTable(tableName);
  }
}

# Shape Data

An opinionated approach to Data Persistence

*	For java 11 and upwards
*	Provide an easy persistence API to manage String-based data
*	Optimized towards developer's productivity

## Api

An interface for multiple persistence backends 

```
public interface IPersistenceManager {

  public Integer insert(IDto dto);

  public Integer update(IDto dto, IWheresMap wheres);

  public Integer delete(IDto dto, IWheresMap wheres);

  public Integer save(IDto dto, IWheresMap wheres);

  public Integer selectCount(IDto requestDto, IWheresMap wheres, boolean definedAttributes);

  public List<String> descTable(String tableName);

  public boolean createTable(String tableName, List<String> fields);

  public boolean dropTable(String tableName);

  public void setSQLDialect(String sqlDialect);

  public List<IDto> query(String sql, String... args);

  public int execute(String sql, String... args);
}
```

## Data

Implementation Project

## Examples

Example insert:

```
    IPersistenceManager manager
..
    Dto dto = Dto.of(tableName);
    dto.put("name", name);
    dto.put("surname", surname);
    dto.put("email", email);
    manager.insert(dto);
```

Example update:
```
    Wheres wheres = new Wheres();
    wheres.put("name", "Donald");

    Dto updatedDto = Dto.of(tableName);
    updatedDto.put("name", "Osvald");
    updatedDto.put("surname", "Strange");
    updatedDto.put("email", "nowhere@test.com");
    
    pm.update(updatedDto, wheres);
```


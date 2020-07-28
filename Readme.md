# Shape Data

An opinionated approach to Data Persistence

*	For java 11 and upwards
*	Provide an easy persistence API to manage String-based data
*	Optimized towards developer's productivity

## Api
Interfaces


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


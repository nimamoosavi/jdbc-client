# Generic Repository
This project use JPA and generic specifications of Spring Data JPA to query and fetch data from relational database 

## Use
To Use this library in your project add dependencies in maven:
```xml
<dependency>
            <groupId>app.ladderproject</groupId>
            <artifactId>crud</artifactId>
            <version>0.0.1-SNAPSHOT</version>
</dependency>
<dependency>
            <groupId>app.ladderproject</groupId>
            <artifactId>Jdbc-client</artifactId>
            <version>0.0.1-SNAPSHOT</version>
</dependency>
```
> 	$ git clone https://github.com/nimamoosavi/jdbc-client.git
> 	$ cd jdbc-clients
> 	$ mvn clean install

## Operations
This table shows a list of the current supported operations.

| Operation				   | Code |
| ------------------------ | ---- |
| equal 				   | EQUALS   |
| not equal 			   | NOT_EQUALS  |
| greater than 			   | GREATER_THAN	  |
| greater than or equal to | GREATER_OR_EQUALS  |
| less than 			   | LESS_THAN	  |
| less than or equal to	   | LESS_OR_EQUALS  |
| in 					   | CONTAINS   |
| start with			   | GREATER_THAN	  |
| end with | END_WITH  |
| not contain 			   | NOT_CONTAIN	  |
| not start with	   | NOT_START_WITH  |
| not end with 					   | NOT_END_WITH   |
| blank 				   | BLANK  |
| not blank 				   | NOT_BLANK  |
| null 					   | NULL |
| not null 					   | NOT_NULL |



## Implementation
### Using criteria object
 To be able to custom filter you must just extend from our JdbcRepository:

```java
@Repository
import app.ladderproject.sample.domain.entity.User;
import app.ladderproject.jdbcclient.repository.JdbcRepository;
import org.springframework.stereotype.Repository;
public interface UserRepository extends JdbcRepository<User, Long> {
}
```

if you extend from BaseController then call `http://localhost:8080/sample/v1/users/all/query` and set data in body:

```json
{
  "criteria":{
    "operator": "EQUALS",
    "fieldName": "name",
    "value": "John"},
  "sorts": ["name","lastName"]
}
```
###TODOs
* add operation for check operation like equality between two field
* add condition on object like user.userInfo.age > 10

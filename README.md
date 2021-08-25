[[/images/jdbc.png |"Jdbc"]]

this interface is the implementation of JpaRepository of Spring Data And JPA and generic specifications of Spring Data
JPA to query any parameter, you can find know about it in {@link <a https://spring.io/projects/spring-data-jpa</a>}
you must create an interface and extended of it then generate a bean of your interface and use all method that Spring
Data implement it And then you Access the Spring Data Query Method and Used It in your Interface

## Requirement

The library works with Java 8+, ladder Core 1.0.1+ and implemented Crud Project

## [Core](https://github.com/nimamoosavi/core/wiki)

## [Crud](https://github.com/nimamoosavi/crud/wiki)

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

| Operation                   | Code |
| ------------------------ | ---- |
| equal                   | EQUALS   |
| not equal               | NOT_EQUALS  |
| greater than               | GREATER_THAN      |
| greater than or equal to | GREATER_OR_EQUALS  |
| less than               | LESS_THAN      |
| less than or equal to       | LESS_OR_EQUALS  |
| in                       | CONTAINS   |
| start with               | GREATER_THAN      |
| end with | END_WITH  |
| not contain               | NOT_CONTAIN      |
| not start with       | NOT_START_WITH  |
| not end with                       | NOT_END_WITH   |
| blank                   | BLANK  |
| not blank                   | NOT_BLANK  |
| null                       | NULL |
| not null                       | NOT_NULL |

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
  "criteria": {
    "operator": "EQUALS",
    "fieldName": "name",
    "value": "John"
  },
  "sorts": [
    "name",
    "lastName"
  ]
}
```

### TODOs

* add operation for check operation like equality between two field
* add condition on object like user.userInfo.age > 10

# Sample Project

this project used Crud Project And Jdbc Project And Core And ...

- [Sample Project](https://github.com/nimamoosavi/sample-project-crud)

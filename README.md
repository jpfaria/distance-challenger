## Requirements ##

- JDK 1.8
- mongodb 3.4

## Compile ##

mvn clean package

## run ##

`cd target/`

** with memory database **

`java -jar distance-challenge-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=word_memory`

** with mongo database **

`java -jar distance-challenge-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=word_mongo -Dspring.data.mongodb.host=localhost -Dspring.data.mongodb.port=27017 -Dspring.data.mongodb.database=dchallenge -Dspring.data.mongodb.username=dchallenge -Dspring.data.mongodb.password=dchallenge`
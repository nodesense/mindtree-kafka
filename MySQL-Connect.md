mysql -uroot

GRANT  ALL ON *.* TO 'team'@'%' identified by 'team1234';



mysql -uroot

CREATE DATABASE gk;
USE gk;

create table products (id int, name varchar(255), price int, create_ts timestamp DEFAULT CURRENT_TIMESTAMP , update_ts timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP );

 insert into products (id, name, price) values(1,'moto phone', 1000);
 update products set price=2200 where id=1;
select * from products;


outside mysql shell

/root/gopal >       touch gk-mysql-product-source.json

```json

{
  "name": "gk-mysql-product-source",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
    "key.converter": "io.confluent.connect.avro.AvroConverter",
    "key.converter.schema.registry.url": "http://localhost:8081",
    "value.converter": "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url": "http://localhost:8081",
    "connection.url": "jdbc:mysql://localhost:3306/gk?user=team&password=team1234",
    "_comment": "Which table(s) to include",
    "table.whitelist": "products",
    "mode": "timestamp",
     "timestamp.column.name": "update_ts",
    "validate.non.null": "false",
    "_comment": "The Kafka topic will be made up of this prefix, plus the table name  ",
    "topic.prefix": "db-"
  }
}
  
```
  
  
  confluent load gk-mysql-product-source -d gk-mysql-product-source.json
  
  confluent status gk-mysql-product-source
  
  
  
  kafka-avro-console-consumer  \
    --bootstrap-server localhost:9092 \
    --property schema.registry.url=http://localhost:8081 \
    --property print.key=true \
    --from-beginning  \
    --topic db-products
  
    
    
    
    touch gk-mysql-product-sink.properties
    
 ```json    
    
 name=gk-mysql-product-sink
connector.class=io.confluent.connect.jdbc.JdbcSinkConnector
tasks.max=1
topics=products
connection.url=jdbc:mysql://localhost:3306/gk?user=team&password=team1234
auto.create=true
key.converter=io.confluent.connect.avro.AvroConverter
key.converter.schema.registry.url=http://localhost:8081
value.converter=io.confluent.connect.avro.AvroConverter
value.converter.schema.registry.url=http://localhost:8081

```


confluent load gk-mysql-product-sink -d gk-mysql-product-sink.properties



kafka-avro-console-producer --broker-list localhost:9092 --topic products --property value.schema='{"type":"record","name":"product","fields":[{"name":"id","type":"int"},{"name":"name", "type": "string"}, {"name":"price", "type": "int"}]}'

  
  {"id": 999, "name": "adam pro", "price": 100}

  
  
  
  
}

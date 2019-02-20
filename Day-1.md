c:\tmp\kafka-logs



Open new Command prompt

create a file zookeeper.bat, place the command inside, save the file


%KAFKA_HOME%/bin/windows/zookeeper-server-start %KAFKA_HOME%/etc/kafka/zookeeper.properties


For Kafka Broker (kafka-0.bat)

%KAFKA_HOME%/bin/windows/kafka-server-start %KAFKA_HOME%/etc/kafka/server.properties



For Kafka Broker (kafka-1.bat)

%KAFKA_HOME%/bin/windows/kafka-server-start %KAFKA_HOME%/etc/kafka/server-1.properties


For Kafka Broker (kafka-2.bat)

%KAFKA_HOME%/bin/windows/kafka-server-start %KAFKA_HOME%/etc/kafka/server-2.properties


For Kafka Broker (kafka-3.bat)

%KAFKA_HOME%/bin/windows/kafka-server-start %KAFKA_HOME%/etc/kafka/server-3.properties



kafka-topics --zookeeper localhost:2181 --create --topic greetings --replication-factor 1 --partitions 3

in one command prompt, producer part


kafka-console-producer --broker-list localhost:9092 --topic greetings



open new commmand prompt, consume the message


kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings


kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --from-beginning


kafka-topics --list --zookeeper localhost:2181

kafka-topics --describe --zookeeper localhost:2181 --topic greetings



   kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --partition 0 --from-beginning

   
      kafka-console-consumer --bootstrap-server localhost:9092 --topic greetings --partition 2 --offset 2

      
      
      
     

kafka-topics --zookeeper localhost:2181 --create --topic logs --replication-factor 1 --partitions 3
       

      
 kafka-console-producer  --broker-list localhost:9092 --topic logs --property "parse.key=true" --property "key.separator=:"
      
      
      
kafka-console-consumer --bootstrap-server localhost:9092 --topic logs --partition 0 --offset 0
      
      
      
      
LINUX

$KAFKA_HOME/bin/zookeeper-server-start $KAFKA_HOME/etc/kafka/zookeeper.properties

$KAFKA_HOME/bin/kafka-server-start $KAFKA_HOME/etc/kafka/server.properties
$KAFKA_HOME/bin/kafka-server-start $KAFKA_HOME/etc/kafka/server-1.properties
$KAFKA_HOME/bin/kafka-server-start $KAFKA_HOME/etc/kafka/server-2.properties
$KAFKA_HOME/bin/kafka-server-start $KAFKA_HOME/etc/kafka/server-3.properties
 
      
      
kafka-topics --zookeeper localhost:2181 --create --topic messages --replication-factor 3 --partitions 3


 kafka-console-producer  --broker-list localhost:9092 --topic words  --partitions 3
 
 //// kafka-topics --zookeeper localhost:2181 --create --topic words-count-output --replication-factor 3 --partitions 3


    kafka-console-consumer --bootstrap-server localhost:9092 --topic words-count-output

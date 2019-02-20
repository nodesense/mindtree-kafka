kafka-topics --zookeeper localhost:2181  --alter --topic emails 
   --partitions 3
   
   
kafka-console-consumer --bootstrap-server localhost:9092 --topic words-count-output  --from-beginning  --formatter kafka.tools.DefaultMessageFormatter --property print.key=true  --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer  --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
    
    
    
    
kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic words-count-output \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
    
    

    
kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic     streams-state-invoices-count \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
    
    
  
kafka-console-consumer --bootstrap-server localhost:9092 \
    --topic         streams-state-amount \
    --from-beginning \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.key=true \
    --property print.value=true \
    --property value.deserializer=org.apache.kafka.common.serialization.DoubleDeserializer
    
    
    

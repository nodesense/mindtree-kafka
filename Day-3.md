
below is content for gk-file-source.properties

name=gk-file-source
connector.class=FileStreamSource
tasks.max=1
file=/root/gopal/input-file.txt
topic=gk-file-content




 touch input-file.txt

 confluent list connectors
 confluent status connectors
 
 confluent load gk-file-source -d gk-file-source.properties
 
 confluent status gk-file-source
 
  confluent unload gk-file-source
  
  
   confluent load gk-file-sink -d gk-file-sink.properties


 
 
 kafka-console-consumer --bootstrap-server localhost:9092 --topic gk-file-content --from-beginning

 
 echo "input line 3" >> input-file.txt

 
 
 
touch gk-file-sink.properties
touch output-file.txt

paste below to gk-file-sink.properties


name=gk-file-sink
connector.class=FileStreamSink
tasks.max=1
file=/root/gopal/output-file.txt
topics=gk-file-content















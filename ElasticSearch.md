To install Elastic Search, follow this step

https://www.digitalocean.com/community/tutorials/how-to-install-elasticsearch-logstash-and-kibana-elastic-stack-on-ubuntu-18-04




touch gk-elasticsearch-sink.json

{
  "name": "gk-elasticsearch-sink",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "1",
    "topics": "products",
    "key.ignore": "true",
    "connection.url": "http://localhost:9200",
    "type.name": "kafka-connect",
    "name": "gk-elasticsearch-sink"
  }
}


confluent load gk-elasticsearch-sink -d gk-elasticsearch-sink.json

confluent status gk-elasticsearch-sink

 
curl -XGET 'http://localhost:9200/products/_search?q=name:phone'

curl -XGET 'http://localhost:9200/products/_search?q=product'
 
curl -XGET 'http://localhost:9200/products/_search?pretty=true&q=*:*'

curl -XGET 'http://localhost:9200/_cat/indices?v'
 



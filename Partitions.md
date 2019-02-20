Partitions :

Increase the partitions will keep the data in different log files which also gives the benefit of increased parallelism along with reducing the log file size(increases the number of files)

at the same this will not reduce the data volume in disk, but split into multiple files.

Prcedure to increase the partition :

# Add new option or change exsiting option 
 
bin/kafka-configs.sh --alter --zookeeper <Zookeeper_server>:2181 --entity-name <topicName> --entity-type topics --add-config cleanup.policy=compact
and then ensure that Partition reassignment script executed with --execute option

bin/kafka-reassign-partitions.sh
more on these utilities with syntax and examples can be found here

Data Retention :

If you don't need to hold the data, that can be purged after reached the retention

this can be set while the topic creation time with the --config option retention.bytes or retention.ms

#Example bin/kafka-configs.sh --zookeeper <zookeeper_server>:2181 --entity-type topics --alter --add-config retention.ms=86400000 --entity-name <topic_name>

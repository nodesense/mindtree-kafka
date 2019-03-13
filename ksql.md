
SHOW STREAMS;
SHOW TABLES;
SHOW TOPICS;
SHOW QUERIES;


ksql-datagen quickstart=users format=avro topic=mindtree_users maxInterval=5000
ksql-datagen quickstart=pageviews format=avro topic=mindtree_pageviews maxInterval=5000


mindtree_users;


{"ROWTIME":1552105167062,
 "ROWKEY":"User_5",
  "registertime":1494126392062,
  "userid":"User_5",
  "regionid":"Region_9",
  "gender":"OTHER"}

CREATE STREAM mindtree_users_stream (userid varchar, regionid varchar, gender varchar) WITH \
(kafka_topic='mindtree_users', value_format='JSON');

Non Persistent

select userid, regionid, gender from mindtree_users_stream where gender='FEMALE';

Ctrl + c to exit

Persistent query, create a kafka topic called mindtree_users_female
with attributes, userid, regionid, persist the records into Kafka Topics

CREATE STREAM mindtree_users_female AS \
SELECT userid AS userid, regionid \
FROM mindtree_users_stream \
where gender='FEMALE';


 1552106259683 | 'User_4' | 'Page_80' ]) ts:1552106260558

topic: mindtree_pageviews



CREATE STREAM mindtree_pageviews_stream (userid varchar, pageid varchar) WITH \
(kafka_topic='mindtree_pageviews', value_format='AVRO');

select * from mindtree_pageviews_stream;


mindtree_users_stream
mindtree_pageviews_stream

Joined output

mindtree_pageviews_enriched_stream


CREATE STREAM mindtree_pageviews_enriched_stream AS \
SELECT mindtree_users_stream.userid AS userid, pageid, regionid, gender \
FROM mindtree_pageviews_stream \
LEFT JOIN mindtree_users_stream \
  WITHIN 1 HOURS \
  ON mindtree_pageviews_stream.userid = mindtree_users_stream.userid;

CREATE STREAM mindtree_pageviews_enriched_stream_males AS \
SELECT mindtree_users_stream.userid AS userid, pageid, regionid, gender \
FROM mindtree_pageviews_stream \
LEFT JOIN mindtree_users_stream \
  WITHIN 1 HOURS \
  ON mindtree_pageviews_stream.userid = mindtree_users_stream.userid \
  WHERE gender = 'MALE';


select *  from mindtree_pageviews_enriched_stream;
select *  from mindtree_pageviews_enriched_stream_males;

CREATE TABLE mindtree_pageviews_region_table \
        WITH (VALUE_FORMAT='avro') AS \
        SELECT gender, regionid, COUNT(*) AS numusers \
        FROM mindtree_pageviews_enriched_stream \
        WINDOW TUMBLING (size 30 second) \
        GROUP BY gender, regionid \
        HAVING COUNT(*) >= 1;

select *  from mindtree_pageviews_region_table;

PERSISTED QUERY FOR TABLE


CREATE TABLE mindtree_pageviews_region_gt_10_table AS \
SELECT gender, regionid, numusers \
FROM mindtree_pageviews_region_table \
where numusers > 10;

select *  from mindtree_pageviews_region_gt_10_table;




KSQL
    STREAM - Produce into Another Kafka Topic, subscribe from source topic
        KSTREAM
    TABLE - Produce into Another Kafka Topic, subscribe from source topic
        KTABLE
    QUERY [TABLE, STREAM]
        Persisted
            Store results into Another Kafka Topic, subscribe from source topic

            SHOW QUERIES;
            EXPLAIN <QUERY_ID>;
            TERMINATE <QUERY_ID>;
        Non-persisted

---


Control Center DEMO


ksql-datagen quickstart=pageviews format=avro topic=mindtree_pageviews maxInterval=50


A Scala Project, to manage Kafka Cluster, Topics, Admin tool
https://github.com/yahoo/kafka-manager




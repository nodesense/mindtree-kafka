// InvoiceStream.java
package workshop.invoice;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import scala.Tuple2;
import workshop.models.Invoice;

import java.util.Collections;
import java.util.Map;



import java.util.Properties;


import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class InvoiceStream {

    static  String bootstrapServers = "localhost:9092";
    //FIXME: chance schema url
    static String schemaUrl = "http://localhost:8081";

    public static Properties getConfiguration() {


        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "product-invoice-stream");
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "product-invoice-stream-client");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);

        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);

        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);


        props.put("schema.registry.url", schemaUrl);
        return props;
    }


    public static void main(final String[] args) throws Exception {
        System.out.println("Running Invoice Stream");

        Properties props = getConfiguration();

        //Serdes ==> SerializationDeserialization

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();
        final Serde<Double> doubleSerde = Serdes.Double();


        final Serde<Invoice> InvoiceAvroSerde = new SpecificAvroSerde<>();

        // When you want to override serdes explicitly/selectively
        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url",
                schemaUrl);
        //
        InvoiceAvroSerde.configure(serdeConfig, true); // `true` for record keys


        // In the subsequent lines we define the processing topology of the Streams application.
        final StreamsBuilder builder = new StreamsBuilder();
        // a Stream is a consumer
        final KStream<String, Invoice> invoiceStream = builder.stream("invoices");

        invoiceStream.foreach(new ForeachAction<String, Invoice>() {
            @Override
            public void apply(String key, Invoice invoice) {
                System.out.println("Invoice Key " + key + "  value id  " + invoice.getId() + ":" + invoice.getAmount() );
                System.out.println("received invoice " + invoice);
            }
        });


        // Aggregation, pre-requisties for the aggregation
        KGroupedStream<String, Invoice> stateGroupStream = invoiceStream.groupBy(
                (key, invoice) -> invoice.getState() // return a key (state)
        );




        // KEY, VALUE, table used for aggregation
        KTable<String, Long> stateGroupCount = stateGroupStream
                .count();
        stateGroupCount.toStream().to("streams-state-invoices-count", Produced.with(stringSerde, longSerde));


        KStream<String, Long> stateAmountStream = invoiceStream
                .map((key, invoice)-> { // transform
                    return new KeyValue<String, Long>(invoice.getState(), Long.valueOf(invoice.getAmount()));
                });

        /*
        KTable<String, Long> stateTotalAmountTable = stateAmountStream
                                                        .groupByKey()
                .aggregate(
                        new Initializer<Long>() {
                            @Override
                            public Long apply() {
                                return 0L;
                            }
                        },
                        new Aggregator<String, String, Long>() {
                            @Override
                            public Long apply(String aggKey, String newValue, Long aggValue) {
                                return aggValue + newValue.length();
                            }
                        },
                        new Aggregator<byte[], String, Long>() {
                            @Override
                            public Long apply(byte[] aggKey, String oldValue, Long aggValue) {
                                return aggValue - oldValue.length();
                            }
                        },
                        Materialized.as("aggregated-stream-store")
                                .withValueSerde(Serdes.Long());

    */

        /*
        class Tuple2<T1, T2> {
            public T1 value1;
            public T2 value2;

            Tuple2(T1 v1, T2 v2) {
                value1 = v1;
                value2 = v2;
            }
        }


        final Serde< Tuple2<Long, Long> > tuple2SerdeSerde;



// first step: compute count and sum in a single aggregation step and emit 2-tuples <count,sum> as aggregation result values
        final KTable<String,Tuple2<Long,Long>> countAndSum = stateAmountStream
                .groupByKey()
                .aggregate(
                        new Initializer<Tuple2<Long, Long>>() {
                            @Override
                            public Tuple2<Long, Long> apply() {
                                return new Tuple2<>(0L, 0L);
                            }
                        },
                        new Aggregator<String, Long, Tuple2<Long, Long>>() {
                            @Override
                            public Tuple2<Long, Long> apply(final String key, final Long value, final Tuple2<Long, Long> aggregate) {
                                ++aggregate.value1;
                                aggregate.value2 += value;
                                return aggregate;
                            }
                        }); // omitted for brevity

// second step: compute average for each 2-tuple
        final KTable<String,Double> average = countAndSum.mapValues(
                new ValueMapper<Tuple2<Long, Long>, Double>() {
                    @Override
                    public Double apply(Tuple2<Long, Long> value) {
                        return value.value2 / (double) value.value1;
                    }
                });

        average.toStream()
                .to("streams-state-amount", Produced.with(stringSerde, doubleSerde));


        */


        KStream<String, Invoice> invoiceQtyGt3Stream = invoiceStream
                .filter((key, invoice) ->  invoice.getQty() > 3);

        KStream<String, Invoice> invoiceQtyGt3WithDiscountStream = invoiceQtyGt3Stream
                .map((key, invoice)-> { // transform
                    // immutable
                    invoice.setAmount(invoice.getAmount() - 100); //mutatble, bad one
                    return new KeyValue<>(key,invoice);
                });

        // Dropping invoice object, return key,value (string)
        KStream<String, String> invoiceQtyGt3WithDiscountKeyValueStream = invoiceQtyGt3WithDiscountStream
                .map((key, invoice)-> { // transform
                    return new KeyValue<>(key,invoice.getAmount().toString());
                });

        // publish to a new topic
        // (id, 100/150)
        invoiceQtyGt3WithDiscountKeyValueStream
                .to("streams-qty-amount-str", Produced.with(stringSerde, stringSerde));


        // Dropping invoice object, return key,value (string)
        KStream<String, Long> invoiceQtyGt3WithDiscountKeyValueLongStream = invoiceQtyGt3WithDiscountStream
                .map((key, invoice)-> { // transform
                    return new KeyValue<>(key,invoice.getAmount().longValue());
                });

        // publish to a new topic
        // (id, 100/150)
        invoiceQtyGt3WithDiscountKeyValueLongStream
                .to("streams-qty-amount-long", Produced.with(stringSerde, longSerde));



        invoiceQtyGt3WithDiscountStream.foreach(new ForeachAction<String, Invoice>() {
            @Override
            public void apply(String key, Invoice invoice) {
                System.out.println("Invoice Qty > 3 & - 100 " + key + " In value id  " + invoice.getId() + ":" + invoice.getAmount() );

            }
        });

        // collection of streams put together
        final KafkaStreams streams = new KafkaStreams(builder.build(), props);


        // streams.cleanUp();
        streams.start();

        System.out.println("Stream started");

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
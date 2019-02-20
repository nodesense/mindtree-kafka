// EmailProducer.java
package workshop;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.RETRIES_CONFIG;

public class EmailProducer {
    //public static String BOOTSTRAP_SERVERS = "116.203.31.40:9092";

    public static String BOOTSTRAP_SERVERS = "localhost:9092";
    public static String TOPIC = "emails";


    public static String[] emailMessages = new String[] {
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",
            "Good afternoon!!",
            "Good morning!!",
            "How are you?",
            "Hope this email finds you well!!",
            "I hope you enjoyed your weekend!!",
            "I hope you're doing well!!",
            "I hope you're having a great week!!",
            "I hope you're having a wonderful day!!",
            "It's great to hear from you!!",
            "I'm eager to get your advice on...",
            "I'm reaching out about...",
            "Thank you for your help",
            "Thank you for the update",
            "Thanks for getting in touch",
            "Thanks for the quick response",
            "Happy Diwali",
            "Happy New Year",
            "Wish you a merry Christmas",

    };

    public static String[] emailIds = new String[]{
        "someone@exmaple.com",
            "someone@hotmail.com",
            "someone@gmail.com"
    };




    public static void main(String[] args) throws  Exception {
        System.out.println("Welcome to producer");

        Properties props = new Properties();

        props.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 16000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // Key as string, value as string
        Producer<String, String> producer = new KafkaProducer<>(props);


        Random r = new Random();

        int counter = 0;
        for (String message:emailMessages) {
            // producer record, topic, key (null), value (message)
            // send message, not waiting for ack

            String email = emailIds[r.nextInt(3)];
            producer.send(new ProducerRecord<>(TOPIC, email, counter + " " + message));
            System.out.printf("Greeting %s sent\n", message);
            Thread.sleep(5000); // Demo only,
            counter++;
        }

        producer.close();


    }


}
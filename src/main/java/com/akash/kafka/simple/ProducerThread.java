package com.akash.kafka.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerThread implements Runnable {

    private Producer<String, String> producer;
    private String topicName;

    public ProducerThread(Application application) {
        this.topicName = application.getTopicName();
        Properties props = new Properties();
        props.put("bootstrap.servers", application.getBrokerUrl());
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void run() {
        for (int index = 1; index < 100; index++) {
            final ProducerRecord<String, String> record = new ProducerRecord<>(topicName,
                    Integer.toString(index), Integer.toString(index));
            try {
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            } catch (ExecutionException e) {
                System.out.println("Error in sending record :"+e);
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                System.out.println("Error in sending record : "+e);
                throw new RuntimeException(e);
            }
        }
    }
}
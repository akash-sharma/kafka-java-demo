package com.akash.kafka.simple;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerThread implements Runnable {

    private Consumer<String, String> consumer;

    public ConsumerThread() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "ConsumerGroup1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("my-topic"));
    }

    @Override
    public void run() {
        int noMessageToFetch = 0;
        while (noMessageToFetch > 5) {
            final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofNanos(20));
            if (consumerRecords.count() == 0) {
                noMessageToFetch++;
            }
            for (ConsumerRecord<String, String> record : consumerRecords) {
                System.out.printf("offset = %d, key = %s, value = %s%n, partition =%d ",
                        record.offset(), record.key(), record.value(), record.partition());
            }
            consumer.commitAsync();
        }
    }
}
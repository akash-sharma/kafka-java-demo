package com.akash.kafka.simple;

public class Application {
	
	public static void main(String[] args) throws InterruptedException {
		final String topicName = "simpleTopic";
		new Thread(new ProducerThread(topicName)).start();
		new Thread(new ConsumerThread(topicName)).start();
	}
}
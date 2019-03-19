package com.akash.kafka.simple;

public class Application {

	/**
	 * This is simple example with
	 * one partition in topic and
	 * one consumer in consumer group
	 */

	private String topicName = "simpleTopic";
	private String consumerGrp = "consumerGrp";
	private String brokerUrl = "localhost:9092";

	public static void main(String[] args) throws InterruptedException {
		Application application = new Application();
		new Thread(new ProducerThread(application)).start();
		new Thread(new ConsumerThread(application)).start();
	}

	public String getTopicName() {
		return topicName;
	}

	public String getConsumerGrp() {
		return consumerGrp;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}
}
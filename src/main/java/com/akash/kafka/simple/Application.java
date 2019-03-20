package com.akash.kafka.simple;

public class Application {

	private String topicName = "simpleTopic";
	private String consumerGrp = "consumerGrp";
	private String brokerUrl = "localhost:9092";

	public static void main(String[] args) throws InterruptedException {
		Application application = new Application();
		new Thread(new ProducerThread(application), "Producer : ").start();
		new Thread(new ConsumerThread(application), "Consumer1 : ").start();

		//for multiple consumers in same group, start new consumer threads
		//new Thread(new ConsumerThread(application), "Consumer2 : ").start();
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

/**
 * For single partition create 1 partition for a topic
 * 		bin/kafka-topics.sh --create --zookeeper localhost:2181
 * 			--replication-factor 1 --partitions 1 --topic simpleTopic
 *
 *
 * For multiple partitions create more than one partitions for a topic
 * 		bin/kafka-topics.sh --create --zookeeper localhost:2181
 * 			--replication-factor 1 --partitions 4 --topic complexTopic
 *
 * 		Example data with 1 consumer 4 partitions :
 *
 * 		offset = 0, key = 1, value = 1, partition =3
 * 		offset = 0, key = 2, value = 2, partition =0
 * 		offset = 0, key = 4, value = 4, partition =1
 * 		offset = 0, key = 5, value = 5, partition =2
 * 		offset = 1, key = 6, value = 6, partition =2
 *
 *
 * For multiple consumers in single consumer group,start more threads of consumer with
 * same consumer group name
 *
 * 		Example data with 2 consumer 4 partitions :
 *
 * 		Producer : Record sent with key 1 to partition 3 with offset 32
 * 		Producer : Record sent with key 2 to partition 0 with offset 30
 * 		Consumer2 : offset = 32, key = 1, value = 1, partition =3
 * 		Producer : Record sent with key 3 to partition 3 with offset 33
 * 		Consumer1 : offset = 30, key = 2, value = 2, partition =0
 * 		Consumer2 : offset = 33, key = 3, value = 3, partition =3
 * 		Producer : Record sent with key 4 to partition 1 with offset 17
 * 		Producer : Record sent with key 5 to partition 2 with offset 20
 * 		Producer : Record sent with key 6 to partition 2 with offset 21
 *		Consumer1 : offset = 17, key = 4, value = 4, partition =1
 *		Producer : Record sent with key 7 to partition 3 with offset 34
 *		Consumer2 : offset = 20, key = 5, value = 5, partition =2
 */
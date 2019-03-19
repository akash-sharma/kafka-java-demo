package com.akash.kafka.simple;

public class Application {
	
	public static void main(String[] args) {
		new Thread(new ProducerThread()).start();
		new Thread(new ConsumerThread()).start();
	}
}
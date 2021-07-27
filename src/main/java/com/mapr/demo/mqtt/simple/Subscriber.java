package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

	protected static MyMqttClient client;
	
  public static void main(String[] args) throws MqttException {

	  
    System.out.println("== START SUBSCRIBER ==");

   // MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
    //MqttClient client=new MqttClient("tcp://10.2.191.28:1883", MqttClient.generateClientId());
   // client.setCallback( new SimpleMqttCallBack() );
   // client.connect();

   // client.subscribe("tags");
	
   restartListener();
  }
  
  
  public static void restartListener() {
  	//client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
  	//client.setCallback( new TheMqttCallBack() );
  	//client.connect();
  	//client.subscribe("tags");
  	try {
  		client = new MyMqttClient();
		//client.setCallback( new SimpleMqttCallBack() );
  	} catch (Exception e) { 
  		System.err.println(e.toString());
  	}

  }

}

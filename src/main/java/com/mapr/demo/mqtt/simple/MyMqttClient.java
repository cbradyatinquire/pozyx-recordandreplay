package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqttClient extends MqttClient {
  
  public MyMqttClient() throws MqttException {
	  //super("tcp://localhost:1883", MqttClient.generateClientId());
	  super("tcp://10.2.191.28:1883", MqttClient.generateClientId());
	  setCallback( new SimpleMqttCallBack() );
	  connect();
	  subscribe("tags");
  }
}
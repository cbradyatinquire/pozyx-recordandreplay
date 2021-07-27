package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;

import java.io.File;  
import java.io.IOException;

import java.io.FileWriter;


public class SimpleMqttCallBack implements MqttCallback {

	//File myOut;
	FileWriter myWriter;
	
	public SimpleMqttCallBack() {
		try {
			System.out.println("OPENING");
		     // myOut = new File("output.txt");
		     // if (myOut.createNewFile()) {
		     //   System.out.println("File created: " + myOut.getName());
		     // } else {
		     //   System.out.println("File already exists.");
		     // }
			  myWriter = new FileWriter("output.txt");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		}

  public void connectionLost(Throwable throwable) {
    System.out.println("Connection to MQTT broker lost!");
	try {
		myWriter.close();
	}
	catch (Exception e ) { e.printStackTrace(); }
	Subscriber.restartListener();
  }

  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
   // System.out.println("Message received:\t"+ new String(mqttMessage.getPayload()) );
	//System.out.println("Positioning update:" + new String(mqttMessage.getPayload()) );
	try {
		String proc = new String(mqttMessage.getPayload(), StandardCharsets.UTF_8);
		
		try {
		      myWriter.write(proc + "\n");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		
		proc = proc.substring(1, proc.length() - 1);
		//System.out.println(proc);
		Gson gson = new Gson();
  
		        // Converting json to object
		        // first parameter should be prpreocessed json
		        // and second should be mapping class
		
		Datum dat = gson.fromJson(proc, Datum.class);
	
		Coordinates cs = dat.data.coordinates;
	
		System.out.println(dat.tagId + ": (" + cs.x + ", " + cs.y + ")" );
		Velocity v = dat.data.velocity;
		System.out.println(dat.tagId + " velocity: (" + v.x + ", " + v.y  + ")" );
		System.out.println(dat.data.tagData.accelerometer);

		/*
		        // return object
		System.out.println(dat.tagID);
		System.out.println(dat.data);
	
		*/
	}
	catch (Exception e ){ e.printStackTrace(); }
  }

  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
  }
  
 
}

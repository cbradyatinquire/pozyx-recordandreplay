package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

	
public class Publisher {


  public static void main(String[] args) throws MqttException {

	  double SLOMO_FACTOR = 1;
	  int NUMREPEATS = 1;  
    System.out.println("== START PUBLISHER ==");

    MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
 //   MqttClient client = new MqttClient("tcp://10.2.191.28:1883", MqttClient.generateClientId());
	

    client.connect();
	
	
	try {
	      File myObj = new File("input.txt");
	      Scanner myReader = new Scanner(myObj);
		  
		double past = -1;
		double pres = 0;
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        System.out.println(data);
	      
					
				String pr = data.substring(50,60);
				System.out.println(pr);
				try {
					pres = Double.parseDouble(pr);
				}
				catch (Exception e) { e.printStackTrace(); }
				if (past > -1) {
					double diff = pres - past;
					System.err.println("DIFF = " + diff);
					if (diff > 0)
					{
						int microdiff = (int) (SLOMO_FACTOR * 1000000 * diff);
						System.out.println(" going to wait " + microdiff + " microseconds" + "= " + diff + " sec");
			
						try {
							TimeUnit.MICROSECONDS.sleep( microdiff );
						}
						catch (java.lang.InterruptedException e) { e.printStackTrace(); }
					}
				}
		
				System.out.println("sending one: " );
			
			    MqttMessage message = new MqttMessage();
			    message.setPayload(data.getBytes());
			    client.publish("tags", message);
				if (pres > past) {
					past = pres;
				}
			
			}//while
	  	
		myReader.close();
	    client.disconnect();
	  }
	catch (FileNotFoundException e) {
	  System.out.println("An error occurred.");
	  e.printStackTrace();
	}
    System.out.println("== END PUBLISHER ==");
  }


}

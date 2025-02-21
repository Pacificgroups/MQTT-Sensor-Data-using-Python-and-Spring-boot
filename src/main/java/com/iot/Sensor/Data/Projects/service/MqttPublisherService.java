package com.iot.Sensor.Data.Projects.service;

import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {
    private static final String BROKER = "tcp://localhost:1883";  // MQTT Broker URL
    private static final String CLIENT_ID = "SpringBootPublisher";
    private static final String TOPIC = "device/config";  // Topic for sending config updates

    private MqttClient client;

    public MqttPublisherService() {
        connectMqttClient();
    }

    private void connectMqttClient() {
        try {
            client = new MqttClient(BROKER, CLIENT_ID, null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);  // Auto-reconnect if connection drops
            options.setConnectionTimeout(10);  // Timeout in seconds
            client.connect(options);
            System.out.println("Connected to MQTT Broker: " + BROKER);
        } catch (MqttException e) {
            System.err.println("Failed to connect to MQTT Broker: " + e.getMessage());
        }
    }

    public void sendConfig(String configData) {
        try {
            if (!client.isConnected()) {
                System.out.println("MQTT Client disconnected. Reconnecting...");
                connectMqttClient();
            }

            MqttMessage message = new MqttMessage(configData.getBytes());
            message.setQos(1);
            client.publish(TOPIC, message);
            System.out.println("✅ Config sent: " + configData);
        } catch (MqttException e) {
            System.err.println("❌ Error sending config: " + e.getMessage());
        }
    }

    @PreDestroy  // Ensures clean disconnection when the app shuts down
    public void disconnect() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                System.out.println("Disconnected from MQTT Broker.");
            }
        } catch (MqttException e) {
            System.err.println("Error while disconnecting: " + e.getMessage());
        }
    }
}

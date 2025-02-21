package com.iot.Sensor.Data.Projects.service;

import com.iot.Sensor.Data.Projects.entity.SensorData;
import com.iot.Sensor.Data.Projects.repository.SensorDataRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

@Service
public class MqttSubscriber {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    private final String brokerUrl = "tcp://localhost:1883";
    private final String topic = "factory/sensor/data";
    private final String clientId = "springBootClient";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MqttClient mqttClient;

    @PostConstruct // Ensures this runs after dependency injection
    public void init() {
        connectAndSubscribe();
    }

    private void connectAndSubscribe() {
        try {
            mqttClient = new MqttClient(brokerUrl, clientId);
            mqttClient.connect();
            System.out.println("✅ Connected to MQTT Broker");

            // 🔴 This is where we subscribe and call saveSensorData()
            mqttClient.subscribe(topic, (topic, message) -> {
                String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                System.out.println("📩 Received MQTT Message: " + payload);
                saveSensorData(payload);  // <-- This is where it is called!
            });

            System.out.println("📡 Subscribed to topic: " + topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void saveSensorData(String payload) {
        try {
            System.out.println("🔍 Parsing JSON: " + payload);
            SensorData data = objectMapper.readValue(payload, SensorData.class);
            System.out.println("💾 Saving to MySQL: " + data);
            sensorDataRepository.save(data);
            System.out.println("✅ Data successfully saved!");
        } catch (Exception e) {
            System.err.println("❌ Error processing MQTT message: " + e.getMessage());
        }
    }
}

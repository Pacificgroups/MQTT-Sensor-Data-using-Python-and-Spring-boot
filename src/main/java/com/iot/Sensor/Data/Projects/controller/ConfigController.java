package com.iot.Sensor.Data.Projects.controller;

import com.iot.Sensor.Data.Projects.service.MqttPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    private static final Logger LOGGER = Logger.getLogger(ConfigController.class.getName());
    private final MqttPublisherService mqttPublisherService;

    public ConfigController(MqttPublisherService mqttPublisherService) {
        this.mqttPublisherService = mqttPublisherService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendConfig(@RequestBody String configData) {
        if (configData == null || configData.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Config data cannot be empty!");
        }

        try {
            mqttPublisherService.sendConfig(configData);
            LOGGER.info("✅ Config sent: " + configData);
            return ResponseEntity.ok("✅ Config sent successfully: " + configData);
        } catch (Exception e) {
            LOGGER.severe("❌ Error sending config: " + e.getMessage());
            return ResponseEntity.internalServerError().body("❌ Failed to send config!");
        }
    }
}

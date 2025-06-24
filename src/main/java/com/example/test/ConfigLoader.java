package com.example.test;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadYamlAsProperties(String yamlFile) {
        Yaml yaml = new Yaml();
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(yamlFile)) {
            Map<String, Object> yamlMap = yaml.load(in);
            Properties props = new Properties();
            flattenYaml("", yamlMap, props);
            return props;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void flattenYaml(String prefix, Map<String, Object> map, Properties props) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                flattenYaml(key, (Map<String, Object>) value, props);
            } else {
                props.put(key, value.toString());
            }
        }
    }
} 
package com.juzi.kafka.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author codejuzi
 */
public class CustomDeserializer implements Deserializer<String> {
    @Override
    public String deserialize(String topic, byte[] data) {
        // 转换 data 即可
        return new String(data);
    }
}

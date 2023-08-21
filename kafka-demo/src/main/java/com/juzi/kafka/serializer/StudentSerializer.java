package com.juzi.kafka.serializer;

import com.juzi.kafka.pojo.Student;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @author codejuzi
 */
public class StudentSerializer implements Serializer<Student> {
    @Override
    public byte[] serialize(String topic, Student data) {
        // 可以使用ObjectMapper来序列化
        return data.getName().getBytes(StandardCharsets.UTF_8);
    }
}

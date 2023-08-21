package com.juzi.kafka;

import com.juzi.kafka.callback.CustomCallback;
import com.juzi.kafka.interceptor.TimeStampInterceptor;
import com.juzi.kafka.partitioner.CustomPartitioner;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author codejuzi
 */
public class Main {
    public static void main(String[] args) {

        // 创建生产者

        // 配置参数
        Properties properties = new Properties();

        // ===== 必要参数 =====
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StudentSerializer.class);

        // 拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, List.of(TimeStampInterceptor.class));
        // 分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

//        try (KafkaProducer<String, Student> producer = new KafkaProducer<>(properties)) {
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 3; i++) {
//                ProducerRecord<String, Student> record
//                        = new ProducerRecord<>("test-topic", "key" + i, new Student("student" + i, i));
                // 组装消息
                ProducerRecord<String, String> record
                        = new ProducerRecord<>("test-topic", "key" + i, "value" + i);
                // 发送消息，添加自定义回调Callback
                producer.send(record, new CustomCallback());
            }
        }

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        try {
            consumer.subscribe(Collections.singletonList("test-topic"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %s, key = %s, value = %s%n",
                            record.offset(), record.key(), record.value());
                }

                // 异步提交偏移量
                consumer.commitAsync((offsets, exception) -> {
                    if (exception != null) {
                        System.err.println("Commit failed for offsets: " + offsets);
                        for (TopicPartition partition : offsets.keySet()) {
                            // 同步提交失败的偏移量
                            consumer.commitSync(Collections.singletonMap(partition, offsets.get(partition)));
                        }
                    }
                });
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                // 关闭消费者
                consumer.close();
            } catch (Exception e) {
                System.err.println("Error closing the Kafka consumer: " + e.getMessage());
            }
        }


    }
}

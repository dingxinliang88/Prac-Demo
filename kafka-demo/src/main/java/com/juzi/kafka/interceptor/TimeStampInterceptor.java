package com.juzi.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * kafka 拦截器
 *
 * @author codejuzi
 */
public class TimeStampInterceptor implements ProducerInterceptor<String, String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 给每条消息的Value加上当前的时间戳
        return new ProducerRecord<>(
                producerRecord.topic(),
                producerRecord.key(),
                producerRecord.value() + "\t" + System.currentTimeMillis()
        );
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
    }
    @Override
    public void close() {
    }
    @Override
    public void configure(Map<String, ?> map) {
    }
}

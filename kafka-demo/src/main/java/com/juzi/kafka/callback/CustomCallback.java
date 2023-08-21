package com.juzi.kafka.callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author codejuzi
 */
public class CustomCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        if(exception != null) {
            System.out.println(exception.getMessage());
        }
    }
}

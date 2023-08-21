package com.juzi.kafka.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.utils.Utils;

import java.util.Map;

/**
 * @author codejuzi
 */
public class CustomPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Integer partitionCounts = cluster.partitionCountForTopic(topic);
        if (key.toString().startsWith("key-1")) return 1;
        int part = Utils.toPositive(Utils.murmur2(keyBytes)) % partitionCounts;
        return part == 1 ? 0 : part;
    }

    @Override
    public void close() {
    }
    @Override
    public void configure(Map<String, ?> configs) {
    }
}

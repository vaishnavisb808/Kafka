package examples;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class SimpleStream {
    public static final Logger logger= LogManager.getLogger();
  
//Stream processing application mainly have 4 steps
  //1. configure the properies
  //2. create topology - TOPOLOGY is a directed acyclic graph of stream processing nodes that represents the stream processing logic of 
        //a Kafka Streams application
  //3. start kafka streams
  //4. close the streams
  
    public static void main(String[] args) {
        Properties props=new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"SimpleStream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass()); //serdes- both serializer and deserilizer
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder streamsBuilder=new StreamsBuilder();
        KStream <Integer,String>kStream= streamsBuilder.stream("simple-stream-topic"); //before running this make sure ypu created this topic name
        kStream.foreach((k,v)->System.out.println("Key:"+k+"value:"+v));

        Topology topology=streamsBuilder.build();
        KafkaStreams kafkaStreams=new KafkaStreams(topology, props);
        logger.info("starting stream");
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread (()-> {
            logger.info("Shutting down");
            kafkaStreams.close();
        }));


    }
}

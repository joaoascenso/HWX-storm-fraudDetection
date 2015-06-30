package topology;

/**
 * Created by austin on 6/30/15.
 */
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import bolts.DetectFraudBolt;
import bolts.SystemOutEchoBolt;
//import spouts.RandomLineSpout;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * This is a simple Storm topology.
 */
public class FraudTopology {

    private static final String SENTENCE_SPOUT_ID = "kafka-spout";
    private static final String TOPOLOGY_NAME = "fraud-detection-topology";
    private static final String DETECT_FRAUD_BOLT_ID = "fraud-detection-bolt";

    /**
    * where all the magic happens
    **/
    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();

        int numSpoutExecutors = 1;
        KafkaSpout kspout = buildKafkaSentenceSpout();
        DetectFraudBolt fraudBolt = new DetectFraudBolt();
        builder.setSpout(SENTENCE_SPOUT_ID, kspout, numSpoutExecutors);
        builder.setBolt(DETECT_FRAUD_BOLT_ID, new SystemOutEchoBolt()).shuffleGrouping(SENTENCE_SPOUT_ID);
        //builder.setBolt(DETECT_FRAUD_BOLT_ID, fraudBolt, 3).shuffleGrouping(SENTENCE_SPOUT_ID);

        Config conf = new Config();
        conf.setDebug(false);
        conf.setNumWorkers(1);
        StormSubmitter.submitTopology(TOPOLOGY_NAME, conf, builder.createTopology());
    }

    /**
    * build a kafka spout using the provided libraries
    **/
    private static KafkaSpout buildKafkaSentenceSpout() {
        
        String zkHostPort = "localhost:2181";
        String topic = "flume";

        String zkRoot = "/kafka-spout";
        String zkSpoutId = "kafka-fraud-spout";
        ZkHosts zkHosts = new ZkHosts(zkHostPort);
        
        SpoutConfig spoutCfg = new SpoutConfig(zkHosts, topic, zkRoot, zkSpoutId);
        KafkaSpout kafkaSpout = new KafkaSpout(spoutCfg);
        return kafkaSpout;
    }
}
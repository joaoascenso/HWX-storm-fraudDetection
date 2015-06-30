package bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by austin on 6/30/15.
 */
public class DetectFraudBolt extends BaseRichBolt {
    OutputCollector _collector;

    public boolean isThreat(String url) {

        List blackList = Arrays.asList("www.google.com", "www.google.co.uk", "www.google.co.in");

        if (blackList.contains(url)) {
            return true;
        } else {
            return false;
        }
    }
        @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {

        String input = tuple.getString(0);
        try {
            URL url = new URL(input);
            String host = url.getHost();
            if (isThreat(host)){
                _collector.emit(tuple, new Values(host + " is in the BLACKLIST"));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        _collector.emit(tuple, new Values(tuple.getString(0)));
        _collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }

}
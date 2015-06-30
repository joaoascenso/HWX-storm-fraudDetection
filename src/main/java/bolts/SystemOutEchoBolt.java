package bolts;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SystemOutEchoBolt extends BaseRichBolt {

  private static final long serialVersionUID = 1L;
  private static final Logger logger = LoggerFactory.getLogger(SystemOutEchoBolt.class);
  //private static final Logger logger = Logger.getLogger(SystemOutEchoBolt.class);

  private OutputCollector m_collector;

  @SuppressWarnings("rawtypes")
  @Override
  public void prepare(Map _map, TopologyContext _conetxt, OutputCollector _collector) {
    m_collector = _collector;
  }

  @Override
  public void execute(Tuple _tuple) {
    System.out.println("Printing tuple with toString(): " + _tuple.toString());
    //System.out.println("Printing tuple with getString(): " + _tuple.getString(0));
    //logger.info("Logging tuple with logger: " + _tuple.getString(0));
    logger.info("Logging tuple with logger: " + _tuple.toString());
    m_collector.ack(_tuple);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer _declarer) {}
}
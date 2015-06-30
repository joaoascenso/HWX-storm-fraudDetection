# WHX-storm-frauddetection

## replace flume 1.5 for 1.6 (if necessary, we need for the kafka libraries)

cd /root
wget http://psg.mtu.edu/pub/apache/flume/1.6.0/apache-flume-1.6.0-bin.tar.gz
tar xvzf apache-flume-1.6.0-bin.tar.gz 
mv apache-flume-1.6.0-bin /usr/hdp/2.2.6.0-2800/
cd /usr/hdp/current
mv flume flume-native
ln -s /usr/hdp/2.2.6.0-2800/apache-flume-1.6.0-bin/ ./flume
mv flume/conf flume/conf-old
ln -s /etc/flume/conf flume/conf


## configuration

### create kafka topics
/usr/hdp/current/kafka/bin/kafka-topics.sh --zookeeper 127.0.0.1 --topic fromFlume --create --partitions 1 --replication-factor 1
/usr/hdp/current/kafka/bin/kafka-topics.sh --zookeeper 127.0.0.1 --list

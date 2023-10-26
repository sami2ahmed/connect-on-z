# connect-on-z
IBM mq connector running within the ZIIP of a Z-series mainframe. Purpose is to emit data straight from this connector running in ZIIP to a Confluent Platform topic, effectively unlocking data from the  

## To post connector to distributed connect worker 
```
curl -i -X PUT -H  "Content-Type:application/json" \
    http://<HOST>:8083/connectors/test-mq-source-connector/config \
    -d '{
        "name": "test-mq-source-connector",
        "connector.class": "io.confluent.connect.ibm.mq.IbmMQSourceConnector",
        "key.converter":"org.apache.kafka.connect.storage.StringConverter",
        "value.converter":"org.apache.kafka.connect.storage.StringConverter",
        "mq.hostname": "",
        "mq.queue.manager": "<qmanager_name>",
        "mq.channel": "*",
        "mq.transport.type": "bindings",

        "jms.destination.name": "<queue_name>,
        "jms.destination.type": "queue",
        "kafka.topic": "mq-source",
        
        "tasks.max": "1",
        "receiver.threads": "5",
        "batch.size": "5000",
        "max.pending.messages": "5000",
        "max.poll.duration": "5000"
    }'  
```

within files, the simple.java is an example of how you might parse tbe data emitted from the MQ source connector (i.e. after data has been written to the topc). <br /> Of course the usefullness of this Java Map is dependent on your unique data. In my case, it enables the extraction of the text key from the payload. Once you've got the text key, you could do things like apply copybook definitions to that data. <br /> In the end, the simple.java should help parse the STR Event that the connector wrote to the topic. 
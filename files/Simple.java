package io.csta.connect.smt;

import java.util.HashMap;

public class Simple
{
    public static void main(final String[] args)
    {

        // Sample MQ to Kafka Source Connect Event Value with no SMTs and basic String handling.
        // Very simple example with a random Text MQ Message Payload of "Your lucky number today is 868"
        final String event = "Struct{messageID=ID:414d5120514d4b41464b4120202020202f8b0763012c0040,messageType=text,timestamp=1661442223888,deliveryMode=2,destination=Struct{destinationType=queue,name=queue:///DEV.QUEUE.1},redelivered=false,expiration=0,priority=4,properties={JMS_IBM_Format=Struct{propertyType=string,string=MQSTR   }, JMS_IBM_PutDate=Struct{propertyType=string,string=20220825}, JMS_IBM_Character_Set=Struct{propertyType=string,string=UTF-8}, JMSXDeliveryCount=Struct{propertyType=integer,integer=1}, JMS_IBM_MsgType=Struct{propertyType=integer,integer=8}, JMSXUserID=Struct{propertyType=string,string=app         }, JMS_IBM_Encoding=Struct{propertyType=integer,integer=273}, JMS_IBM_PutTime=Struct{propertyType=string,string=15434392}, JMSXAppID=Struct{propertyType=string,string=JmsPut (JMS)                }, JMS_IBM_PutApplType=Struct{propertyType=integer,integer=28}},text=Your lucky number today is 868}";


        // Over simplify this, I'm sure iot could be RegEx and Smart but show steps
        final String clean = event.replace("Struct", "").replace("}", "").replace("{", "")
                .replace("propertyType=string,string=", "").replace("propertyType=integer,integer=", "")
                .replace("properties=", "");

        // break on the , delimiters
        final String[] pieces = clean.split(",");

        final HashMap<String, String> map = new HashMap<>();

        for (String piece: pieces)
        {
            // Key/value pairs
            String[] kv = piece.split("=");
            map.put(kv[0].trim(), kv[1].trim());
        }

        //See the key-value Map structure here
        // {JMS_IBM_Character_Set=UTF-8, JMS_IBM_MsgType=8, destination=destinationType, JMSXUserID=app, messageID=ID:414d5120514d4b41464b4120202020202f8b0763012c0040, JMS_IBM_Encoding=273, priority=4, redelivered=false, JMSXAppID=JmsPut (JMS), JMS_IBM_PutApplType=28, JMS_IBM_Format=MQSTR, JMS_IBM_PutDate=20220825, JMSXDeliveryCount=1, messageType=text, deliveryMode=2, name=queue:///DEV.QUEUE.1, expiration=0, JMS_IBM_PutTime=15434392, text=Your lucky number today is 868, timestamp=1661442223888}
        System.out.println(map);


        // And for further parsing we'd just grab the text key say.
        // "Your lucky number today is 868"
        System.out.println(map.get("text"));
    }

}

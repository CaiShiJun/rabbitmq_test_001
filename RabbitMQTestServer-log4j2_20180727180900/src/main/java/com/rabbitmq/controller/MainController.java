package com.rabbitmq.controller;

import com.rabbitmq.service.SingleQueue.producer;
import com.rabbitmq.service.publish.EmitLog;
import com.rabbitmq.service.routing.RoutingSendDirect;
import com.rabbitmq.service.topic.TopicSend;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jialianqing on 2016/10/18 0018.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {

        return "index";
    }

    @RequestMapping(value = "/initProducePublish", method = RequestMethod.GET)
    public String initProducePublish() throws Exception {
        EmitLog pd =new EmitLog();
        //pd.initPubConsumer();
        pd.initPubProducer();
        return "successPublish";
    }
    @RequestMapping(value = "/initProduceTopic", method = RequestMethod.GET)
    public String initProduceTopic() throws Exception {

        //RPCServer pd = new RPCServer();
        TopicSend pd =new TopicSend();
        pd.initTopic(1);
        return "successTopic";
    }
    @RequestMapping(value = "/initProduceTopic1", method = RequestMethod.GET)
    public String initProduceTopic1() throws Exception {

        //RPCServer pd = new RPCServer();
        TopicSend pd =new TopicSend();
        pd.initTopic(0);
        return "successTopic";
    }
    @RequestMapping(value = "/initSingleQueue", method = RequestMethod.GET)
    public String initSingleQueue() throws Exception {
         producer pd=new producer();
        pd.initProducer();
        return "successSingleQueue";
    }

    @RequestMapping(value = "/initRouting", method = RequestMethod.GET)
    public String initRouting() throws Exception {
        RoutingSendDirect pd = new RoutingSendDirect();
        //pd.initRoutingConsumer(1);
        pd.initRoutingProducer(1);
        return "successRouting";
    }
    @RequestMapping(value = "/initRouting1", method = RequestMethod.GET)
    public String initRouting1() throws Exception {
        RoutingSendDirect pd = new RoutingSendDirect();
        pd.initRoutingProducer(0);
        return "successRouting";
    }
}


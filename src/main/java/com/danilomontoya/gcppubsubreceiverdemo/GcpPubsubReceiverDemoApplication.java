package com.danilomontoya.gcppubsubreceiverdemo;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
public class GcpPubsubReceiverDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcpPubsubReceiverDemoApplication.class, args);
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("demoInputChannel") MessageChannel inoutChannel,
            PubSubTemplate pubSubTemplate){
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate,
                "scout-topic-demo");
        adapter.setOutputChannel(inoutChannel);
        return adapter;
    }
    @Bean
    public MessageChannel demoInputChannel(){
        return new DirectChannel();
    }
    @ServiceActivator(inputChannel = "demoInputChannel")
    public void messageReceiver(String message){
        System.out.println("Message arrived ---> " + message);
        System.out.println("let networkQuery = `sum by (job) (increase(network_interface_received_megabytes%7Binstance%3D%27${formattedWorkstationId}%27%7D%5B1h%5D)+%2F+1024)%0Aor%0A++-sum+by+(scrape_job)+(increase(network_interface_sent_megabytes%7Binstance%3D%27${formattedWorkstationId}%27%7D%5B1h%5D)+%2F+1024)`");
    }

}

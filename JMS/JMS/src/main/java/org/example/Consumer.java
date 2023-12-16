package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    public static void main(String[] args) {
        // Etablissement d'une connexion au Broker ActiveMQ
        ConnectionFactory conFact = new ActiveMQConnectionFactory("tcp://localhost:61616");

        try {
            Connection con = conFact.createConnection();
            Session session = con.createSession(true, Session.AUTO_ACKNOWLEDGE);

            // Connexion au fil d'attend avec le nom "myTopic.topic"
            Destination destination = session.createTopic("myTopic.topic");

            //creation du MessageConsumer
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(
                    new MessageListener() {
                        @Override
                        public void onMessage(Message message) {
                            if (message instanceof TextMessage) {
                                TextMessage textMessage = (TextMessage) message;
                                try {
                                    System.out.println(textMessage.getText());
                                } catch (JMSException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        }
                    });

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
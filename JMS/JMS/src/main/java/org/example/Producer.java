package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;

public class Producer {
    public static void main(String[] args) {
        // Configuration du connectionFactory sur l'adresse localhost, port 61616 et protocol TCP
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        try{
            // Creation d'une connexion
            Connection connection = connectionFactory.createConnection();

            // Démarrage du connexion
            connection.start();

            // Creation d'une session transactionnelle et avec une accusé de réception = AUTO_ACKNOWLEDGE
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            // Pour que tous les consummers recoit le même message.
            Destination destination = session.createTopic("myTopic.topic");

            // Préparation du message.
            MessageProducer messageProducer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("Hello World !");

            // Envoi du message.
            messageProducer.send(textMessage);
            session.commit();

            // Fermature du messageProducer, session et la connexion
            messageProducer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.out.println("Erreur : " + e);
        }
    }
}
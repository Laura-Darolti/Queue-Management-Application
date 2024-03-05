package org.example.implementation;

import org.example.client_and_queue.Client;
import org.example.client_and_queue.Queue;
import org.example.implementation.Strategy;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public void addClient(List<Queue> queues, Client c) throws InterruptedException {
        int queueNumber = 0;
        // check which queue has the smallest number of clients
        int OldQueueLength = 100000000;

        for (int i = 0; i < queues.size(); i++) {

            Queue queue = queues.get(i);
            if (queue.getQueue().size() < OldQueueLength) {
                queueNumber = i;
                OldQueueLength = queue.getQueue().size();
            }
        }
        queues.get(queueNumber).addClient(c);
    }
}


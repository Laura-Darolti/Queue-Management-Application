package org.example.implementation;

import org.example.client_and_queue.Client;
import org.example.client_and_queue.Queue;
import org.example.implementation.Strategy;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addClient(List<Queue> queues, Client c) throws InterruptedException {
        int queueNumber = 0;
        // check which queue has the smallest waiting Period
        int OldQueueServiceTime = 100000000;

        for (int i = 0; i < queues.size(); i++) {
            Queue queue = queues.get(i);
            if (queue.getWaitingPeriod().intValue() < OldQueueServiceTime) {
                queueNumber = i;
                OldQueueServiceTime = queue.getWaitingPeriod().intValue();
            }
        }
        queues.get(queueNumber).addClient(c);
    }

    public enum SelectionPolicy {
        SHORTEST_QUEUE, SHORTEST_TIME
    }
}


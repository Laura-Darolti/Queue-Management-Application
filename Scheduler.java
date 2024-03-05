package org.example.implementation;

import org.example.client_and_queue.Client;
import org.example.client_and_queue.Queue;
import org.example.implementation.ConcreteStrategyQueue;
import org.example.implementation.ConcreteStrategyTime;
import org.example.implementation.Strategy;

import java.util.ArrayList;
import java.util.List;
public class Scheduler {
    private List<Queue> queues = new ArrayList<>();
    private int maxNoQueues;
    private static int maxClientsPerServer;
    private Strategy strategy=new ConcreteStrategyQueue();
    public Scheduler(int maxNoQueues, int maxClientsPerServer) {
        this.maxNoQueues = maxNoQueues;
        this.maxClientsPerServer = maxClientsPerServer;
        for (int i = 0; i < maxNoQueues; i++) {
            queues.add(new Queue(maxClientsPerServer));
            Thread t = new Thread(queues.get(i));
            t.start();
        }
    }
    public int getMaxClientsPerServer() {
        return maxClientsPerServer;
    }
    public void changeStrategy(ConcreteStrategyTime.SelectionPolicy policy) {
        if (policy == ConcreteStrategyTime.SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ConcreteStrategyQueue();
        if (policy == ConcreteStrategyTime.SelectionPolicy.SHORTEST_TIME)
            strategy = new ConcreteStrategyTime();

    }
    public void dispatchClient(List<Queue> queues, Client c) throws InterruptedException {

        strategy.addClient(queues,c);
    }
    public List<Queue> getQueues() {
        return this.queues;
    }
}

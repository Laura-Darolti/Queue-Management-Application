package org.example.client_and_queue;

import org.example.client_and_queue.Client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
    private final AtomicInteger waitingPeriod;
    private int maxClientsPerServer;
    private BlockingQueue<Client> queue;
    private BlockingQueue<Client> clients;

    public Queue(int maxClientsPerServer) {
        queue = new ArrayBlockingQueue<>(maxClientsPerServer);
        this.waitingPeriod = new AtomicInteger(0);
        this.maxClientsPerServer = maxClientsPerServer;
    }

    public BlockingQueue<Client> getQueue() {
        return this.queue;
    }

    public void addClient(Client newClient) throws InterruptedException {
        for (int i = 0; i < newClient.getServiceTime(); i++) {
            waitingPeriod.getAndIncrement();
        }
        if (queue.size()==0){
            newClient.setClientServiceTime(newClient.getServiceTime() + 1);
        }
        queue.put(newClient);
    }


    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (queue.size() > 0) {
                // decrease service time by 1

                queue.peek().setClientServiceTime(queue.peek().getServiceTime() - 1);
                // decrease waiting period by 1
                waitingPeriod.decrementAndGet();
                if (queue.peek().getServiceTime() == 0) {
                    queue.remove();
                }
            }
        }
    }

    public static void printClients(BlockingQueue<Client> queue) {
        for (Client client : queue) {
            System.out.print("(Client ID: " + client.getID() + " ,");
            System.out.print("Arrival Time: " + client.getArrivalTime() + " ,");
            System.out.print("Service Time: " + client.getServiceTime() + ")"+",  ");

        }
        System.out.println();
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}

package org.example.implementation;

import org.example.client_and_queue.Client;
import org.example.client_and_queue.Queue;

import java.util.List;

public interface Strategy {
        void addClient(List<Queue> queues, Client c) throws InterruptedException;

}

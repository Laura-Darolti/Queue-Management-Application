package org.example.implementation;
import java.io.*;

import org.apache.commons.io.output.TeeOutputStream;
import org.example.UserInterface.Interface;
import org.example.client_and_queue.Client;
import org.example.client_and_queue.Queue;


import java.util.ArrayList;
import java.util.*;
import java.util.Random;

public class SimulationManager implements Runnable {
    private PrintStream ps;
    private TeeOutputStream tee;

    public int timeLimit;
    public int maxProcessingTime;
    public int minProccesingTime;
    public int numberOfServers;
    public int numberOfClients;
    public int minArrivaltime;


    public synchronized void setMinArrivaltime(int minArrivaltime) {
        this.minArrivaltime = minArrivaltime;
    }

    public int maxArrivaltime;

    public synchronized void setMaxArrivaltime(int maxArrivaltime) {
        this.maxArrivaltime = maxArrivaltime;
    }

    public synchronized void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }

    public synchronized void setMinProccesingTime(int minProccesingTime) {
        this.minProccesingTime = minProccesingTime;
    }

    public synchronized void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public synchronized void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public synchronized void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public ConcreteStrategyTime.SelectionPolicy getSelectionPolicy() {
        return selectionPolicy;
    }

    public ConcreteStrategyTime.SelectionPolicy selectionPolicy = ConcreteStrategyTime.SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;

    public List<Client> generateClients() {
        List listClients = Collections.synchronizedList(new ArrayList<>(numberOfClients));

        Random random = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            Client c = new Client(i, random.nextInt(maxArrivaltime - minArrivaltime + 1) + minArrivaltime, random.nextInt(maxProcessingTime - minProccesingTime + 1) + minProccesingTime);
            listClients.add(i, c);
            listClients.sort((Comparator.comparing(Client::getArrivalTime)));
        }
        return listClients;
    }

    @Override
    public void run() {
        PrintStream console = System.out;
        try {
            ps = new PrintStream(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        tee = new TeeOutputStream(console, ps);
        System.setOut(new PrintStream(tee));

        scheduler = new Scheduler(numberOfServers, numberOfClients);
        ArrayList<Client> waitingClients = new ArrayList<>(generateClients());
        float averageServiceTime = 0;
        int averageServiceTimeCounter = 0;
        float averageWaitingTime = 0;
        int averageWaitingTimeCounter = 0;
        int nClientsInQueues = 1;
        int peakWaitingTime = 0;
        int peakHour = 0;

        int currentTime = 0;
        while (currentTime < timeLimit&& nClientsInQueues != 0) {
           System.out.println("Waiting clients: ");
            for (Client element : waitingClients) {
                System.out.print("(" + element.getID() + "," + element.getArrivalTime() + "," + element.getServiceTime() + ")");
            }
            System.out.println();
            int i = 0;
            while (i == 0) {
                // check if there are still clients waiting
                if (waitingClients.size() > 0) {
                    Client currentClient = waitingClients.get(0);
                    if (currentClient.getArrivalTime().equals(currentTime)) {
                        try {
                            scheduler.dispatchClient(scheduler.getQueues(), currentClient);
                            waitingClients.remove(currentClient);
                            averageServiceTime = averageServiceTime + currentClient.getServiceTime();
                            averageServiceTimeCounter++;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    // check if client arrives in the future, then wait until arival time ==  current time
                    else if (currentClient.getArrivalTime() > currentTime) i = -1;
                }
                // if no more clients get out of while
                if (waitingClients.size() == 0) i = -1;
            }
            System.out.println("Time is " + currentTime);
            currentTime++;
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < scheduler.getQueues().size(); j++) {
                System.out.println("Queue " + j + " :");
                Queue.printClients(scheduler.getQueues().get(j).getQueue());
            }
            System.out.println();
        }
        ps.close();

    }

    public static void main(String[] args) throws FileNotFoundException {
        Interface queueManagementSystemInterface = new Interface();
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}

package org.example.client_and_queue;

public class Client {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }


    public Integer getID() {
        return ID;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setClientID(int ID) {
        this.ID = ID;
    }

    public void setClientArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setClientServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

}

package org.example.UserInterface;

import org.example.implementation.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {

    private final JFrame frame;
    private final JTextField numClientsTextField;
    private final JTextField numQueuesTextField;
    private final JTextField simulationIntervalTextField;
    private final JTextField minArrivalTimeTextField;
    private final JTextField maxArrivalTimeTextField;
    private final JTextField minServiceTimeTextField;
    private final JTextField maxServiceTimeTextField;

    public Interface() {
         frame = new JFrame("Queue Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel numClientsLabel =  new JLabel("Number of Clients (N):");
        JLabel numQueuesLabel = new JLabel("Number of Queues (Q):");
        JLabel simulationIntervalLabel = new JLabel("Simulation Interval (Max):");
        JLabel minArrivalTimeLabel = new JLabel("Minimum Arrival Time (Min/Max):");
        JLabel maxArrivalTimeLabel  = new JLabel("/");
        JLabel minServiceTimeLabel = new JLabel("Minimum Service Time (Min/Max):");
        JLabel maxServiceTimeLabel = new JLabel("/");
        numClientsTextField = new JTextField(10);
        numQueuesTextField = new  JTextField(10);
         simulationIntervalTextField = new JTextField(10);
           minArrivalTimeTextField = new JTextField(5);
        maxArrivalTimeTextField = new JTextField(5);
        minServiceTimeTextField = new JTextField(5);
        maxServiceTimeTextField  = new JTextField(5);

        JButton submitButton = new  JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SimulationManager gen =  null;
                gen = new SimulationManager();
                Thread t = new Thread(gen);
                t.start();
                int numClients = Integer.parseInt(numClientsTextField.getText());

                gen.setNumberOfClients(numClients);
                int numQueues = Integer.parseInt(numQueuesTextField.getText());
                gen.setNumberOfServers(numQueues);
                int simulationInterval = Integer.parseInt(simulationIntervalTextField.getText());
                gen.setTimeLimit(simulationInterval);
                int minArrivalTime = Integer.parseInt(minArrivalTimeTextField.getText());
                gen.setMinArrivaltime(minArrivalTime);
                int maxArrivalTime = Integer.parseInt(maxArrivalTimeTextField.getText());
                gen.setMaxArrivaltime(maxArrivalTime);
                int minServiceTime = Integer.parseInt(minServiceTimeTextField.getText());
                gen.setMinProccesingTime(minServiceTime);
                int maxServiceTime = Integer.parseInt(maxServiceTimeTextField.getText());
                gen.setMaxProcessingTime(maxServiceTime);
                JOptionPane.showMessageDialog(frame, "Input values submitted successfully!");
            }
        });


         frame.setLayout(new FlowLayout());
         frame.add(numClientsLabel);
        frame.add(numClientsTextField);
        frame.add(numQueuesLabel);
        frame.add(numQueuesTextField);
        frame.add(simulationIntervalLabel);
        frame.add(simulationIntervalTextField);
        frame.add(minArrivalTimeLabel);
        frame.add(minArrivalTimeTextField);
        frame.add(maxArrivalTimeLabel);
        frame.add(maxArrivalTimeTextField);
        frame.add(minServiceTimeLabel);
        frame.add(minServiceTimeTextField);
        frame.add(maxServiceTimeLabel);
        frame.add(maxServiceTimeTextField);
        frame.add(submitButton);
          frame.setSize(600, 300);
          frame.setVisible(true);
    }
}


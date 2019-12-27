package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorGUI extends SimulatorValue implements ActionListener {

    private JButton goBtn = new JButton("Go");//button
    private JButton stopBtn = new JButton("Stop");
    private JButton addCarBtn = new JButton("Add Car");

    public SimulatorGUI()
    {
        this.setLocation(250, 0);

        setLayout(new BorderLayout());//Whole layout

        showMap();
        panel1.setBackground(Color.BLACK);

        add(panel1, BorderLayout.CENTER);

        //button panel
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2.add(goBtn);
        panel2.add(stopBtn);
        panel2.add(addCarBtn);


        goBtn.addActionListener(this);
        addCarBtn.addActionListener(this);
        stopBtn.addActionListener(this);

        goBtn.setBackground(paint);
        addCarBtn.setBackground(paint);
        stopBtn.setBackground(paint);

        add(panel2, BorderLayout.PAGE_START);


        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);
        pack();

        addRoad(roadX, roadY, 10, 'E', true);
        addRoad(roadX2, roadY2, 10, 'E',false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == goBtn)
        {
            carTimer.start();
            trafficTimerSwitch(true);
        }
        else if (source == stopBtn)
        {
            carTimer.stop();
            trafficTimerSwitch(false);
        }
        else if(source == addCarBtn)
        {
            spawnCar(carX,carY);
            showCars();
        }
    }
}

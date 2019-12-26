package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorGUI extends SimulatorValue implements ActionListener {

    protected JPanel panel2 = new JPanel();//button panel
    //protected JPanel panel3 = new JPanel();//info panel

    JButton goBtn = new JButton("Go");//button
    JButton addRoadBtn = new JButton("Add Road");
    JButton addCarBtn = new JButton("Add Car");

    public SimulatorGUI()
    {
        this.setLocation(250, 0);

        setLayout(new BorderLayout());//Whole layout

        showMap();
        panel1.setBackground(Color.BLACK);

        add(panel1, BorderLayout.CENTER);

        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel2.add(goBtn);
        panel2.add(addCarBtn);
        panel2.add(addRoadBtn);

        goBtn.addActionListener(this);
        addCarBtn.addActionListener(this);
        addRoadBtn.addActionListener(this);

        goBtn.setBackground(paint);
        addCarBtn.setBackground(paint);
        addRoadBtn.setBackground(paint);

        add(panel2, BorderLayout.PAGE_START);


        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == goBtn)
        {
            moveCars();
            showCars();
        }
        else if (source == addRoadBtn)
        {
            addRoad(roadX, roadY, 10, 'E');
            addRoad(roadX2, roadY2, 10, 'E');
        }
        else if(source == addCarBtn)
        {
            spawnCar(carX,carY);
            showCars();
        }
    }
}

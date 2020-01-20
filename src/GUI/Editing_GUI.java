package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Editing_GUI extends SimulatorValue implements ActionListener
{
    private JButton btn_reset = new JButton("Reset");
    private JButton btn_load = new JButton("Load");
    private JButton btn_save = new JButton("Save");
    private JButton btn_exit = new JButton("Exit");
    private JButton btn_simulate = new JButton ("Simulate");

    private JButton btn_road1 = new JButton("Add Straight Road");
    private JButton btn_road2 = new JButton("Add 4-way Road");
    private JButton btn_road3 = new JButton("Add 3-way Road");


    public Editing_GUI()
    {
        this.setLocation(250, 0);

        setLayout(new BorderLayout());//Whole layout

        //panel1.setBackground()
        JPanel p_btn = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btn_reset.addActionListener(this);
        btn_load.addActionListener(this);
        btn_save.addActionListener(this);
        btn_exit.addActionListener(this);
        btn_simulate.addActionListener(this);

        btn_reset.setBackground(paint);
        btn_load.setBackground(paint);
        btn_save.setBackground(paint);
        btn_exit.setBackground(paint);
        btn_simulate.setBackground(paint);

        p_btn.add(btn_reset);
        p_btn.add(btn_load);
        p_btn.add(btn_save);
        p_btn.add(btn_exit);
        p_btn.add(btn_simulate);

        add(p_btn, BorderLayout.NORTH);

        p_edit.setBackground(Color.black);
        add(p_edit, BorderLayout.CENTER);


        //button panel/
        JPanel p_road_btn = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btn_road1.addActionListener(this);
        btn_road2.addActionListener(this);
        btn_road3.addActionListener(this);

        btn_road1.setBackground(paint);
        btn_road2.setBackground(paint);
        btn_road3.setBackground(paint);

        p_road_btn.add(btn_road1);
        p_road_btn.add(btn_road2);
        p_road_btn.add(btn_road3);

        add(p_road_btn, BorderLayout.SOUTH);

        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

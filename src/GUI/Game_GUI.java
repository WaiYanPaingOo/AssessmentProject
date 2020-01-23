package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game_GUI extends SimulatorValue implements ActionListener, KeyListener
{
    private JButton btn_reset = new JButton("Reset");
    private JButton btn_load = new JButton("Load");
    private JButton btn_save = new JButton("Save");
    private JButton btn_exit = new JButton("Exit");
    private JButton btn_simulate = new JButton ("Simulate");
    private JButton btn_stop = new JButton("Stop Simulate");
    private JButton btn_pause = new JButton("Pause Simulate");
    private JButton btn_go = new JButton("Resume Simulate");

    private JButton btn_road1 = new JButton("Add Straight Road");
    private JButton btn_road2 = new JButton("Add 4-way Road");
    private JButton btn_road3 = new JButton("Add 3-way Road");


    public Game_GUI()
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
        btn_stop.addActionListener(this);
        btn_pause.addActionListener(this);
        btn_go.addActionListener(this);

        btn_reset.setBackground(paint);
        btn_load.setBackground(paint);
        btn_save.setBackground(paint);
        btn_exit.setBackground(paint);
        btn_simulate.setBackground(paint);
        btn_stop.setBackground(paint);
        btn_pause.setBackground(paint);
        btn_go.setBackground(paint);

        btn_pause.setVisible(false);
        btn_go.setVisible(false);
        btn_stop.setVisible(false);

        p_btn.add(btn_reset);
        p_btn.add(btn_load);
        p_btn.add(btn_save);
        p_btn.add(btn_exit);
        p_btn.add(btn_pause);
        p_btn.add(btn_go);
        p_btn.add(btn_simulate);
        p_btn.add(btn_stop);


        add(p_btn, BorderLayout.NORTH);
        showMap();
        panel1.setBackground(Color.black);
        panel1.addKeyListener(this);
        panel1.setFocusable(false);
        add(panel1, BorderLayout.CENTER);


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

        setVisible(true);
        pack();

        addRoad(roadX, roadY, 1, "H");
        addRoad(roadX2, roadY2, 4, "H");
        addRoad(roadX3, roadY3, 1, "H");
        addRoad(roadX4, roadY4, 3, "WE");
        addRoad(roadXVt1, roadYVt1,1,"V");
        addRoad(roadXVt1, roadYVt1+12,1,"V");
        addRoad(roadXVb1, roadYVb1,1,"V");
        addRoad(roadXVb2, roadYVb2,1,"V");

        addRoad(roadXVb1, roadYVb1+12,1,"V");
        addRoad(roadXVb2, roadYVb2+12,1,"V");

        //addRoad(roadX, roadY, 10, 'E', true);
        //addRoad(roadX2, roadY2, 10, 'E',false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btn_reset) {
            clearMap();
        } else if (source == btn_load) {
            //
        }
        else if (source == btn_save) {
            //
        }
        else if (source == btn_exit) {
           new Menu_GUI();
           this.dispose();
        }
        else if (source == btn_simulate) {
            btn_reset.setVisible(false);
            btn_load.setVisible(false);
            btn_save.setVisible(false);
            btn_exit.setVisible(false);
            btn_pause.setVisible(true);
            btn_go.setVisible(false);

            btn_stop.setVisible(true);
            btn_simulate.setVisible(false);

            btn_road1.setVisible(false);
            btn_road2.setVisible(false);
            btn_road3.setVisible(false);

            carTimer.start();
            trafficTimer.start();
            trafficTimerSwitch(true);
        }
        else if (source == btn_stop) {
            btn_reset.setVisible(true);
            btn_load.setVisible(true);
            btn_save.setVisible(true);
            btn_exit.setVisible(true);
            btn_pause.setVisible(false);
            btn_go.setVisible(false);
            btn_stop.setVisible(false);
            btn_simulate.setVisible(true);
            btn_road1.setVisible(true);
            btn_road2.setVisible(true);
            btn_road3.setVisible(true);

            carTimer.stop();
            trafficTimer.stop();
            trafficTimerSwitch(false);
            clearCars();
        }
        else if (source == btn_pause) {
            btn_go.setVisible(true);
            btn_pause.setVisible(false);
            carTimer.stop();
            trafficTimerSwitch(false);
        }
        else if (source == btn_go) {
            btn_pause.setVisible(true);
            btn_go.setVisible(false);
            carTimer.start();
            trafficTimerSwitch(true);
        }
        else if (source == btn_road1) {
            btn_reset.setEnabled(false);
            btn_load.setEnabled(false);
            btn_save.setEnabled(false);
            btn_simulate.setEnabled(false);
            btn_road1.setEnabled(false);
            btn_road2.setEnabled(false);
            btn_road3.setEnabled(false);

            panel1.setFocusable(true);
            panel1.requestFocusInWindow();

            showEditRoad(default_road_spawn_X,default_road_spawn_Y,1, "H");
        }
        else if (source == btn_road2) {
            btn_reset.setEnabled(false);
            btn_load.setEnabled(false);
            btn_save.setEnabled(false);
            btn_simulate.setEnabled(false);
            btn_road1.setEnabled(false);
            btn_road2.setEnabled(false);
            btn_road3.setEnabled(false);

            panel1.setFocusable(true);
            panel1.requestFocusInWindow();

            showEditRoad(default_road_spawn_X,default_road_spawn_Y,4, "H");
        }
        else if (source == btn_road3) {
            btn_reset.setEnabled(false);
            btn_load.setEnabled(false);
            btn_save.setEnabled(false);
            btn_simulate.setEnabled(false);
            btn_road1.setEnabled(false);
            btn_road2.setEnabled(false);
            btn_road3.setEnabled(false);

            panel1.setFocusable(true);
            panel1.requestFocusInWindow();

            showEditRoad(default_road_spawn_X,default_road_spawn_Y,3, "WE");
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        System.out.println("code" + e.getExtendedKeyCode());
        if(key == 'w'){
            moveEditRoad(current_edit_road_type,'N',current_edit_road_rotation);
        }
        else if(key == 'd'){
            moveEditRoad(current_edit_road_type,'E',current_edit_road_rotation);
        }
        else if(key == 's'){
            moveEditRoad(current_edit_road_type,'S',current_edit_road_rotation);
        }
        else if(key == 'a'){
            moveEditRoad(current_edit_road_type,'W',current_edit_road_rotation);
        }
        else if(e.getExtendedKeyCode() == 32){
            undoEditing();
            rotateEditingObj(current_edit_road_type);
        }
        else if(key == 'r'){
            btn_reset.setEnabled(true);
            btn_load.setEnabled(true);
            btn_save.setEnabled(true);
            btn_simulate.setEnabled(true);
            btn_road1.setEnabled(true);
            btn_road2.setEnabled(true);
            btn_road3.setEnabled(true);
            panel1.setFocusable(false);

            undoEditing();
        }
        else if(e.getExtendedKeyCode() == 10){
            btn_reset.setEnabled(true);
            btn_load.setEnabled(true);
            btn_save.setEnabled(true);
            btn_simulate.setEnabled(true);
            btn_road1.setEnabled(true);
            btn_road2.setEnabled(true);
            btn_road3.setEnabled(true);
            panel1.setFocusable(false);

            endEditing(current_edit_road_type);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

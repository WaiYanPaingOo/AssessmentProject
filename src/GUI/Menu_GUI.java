package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Menu_GUI extends SimulatorValue implements ActionListener {
    private JButton btn_play = new JButton("Start Simulator");//button
    private JButton btn_exit = new JButton("Exit Simulator");
    private JLabel lbl_title = new JLabel("Car Traffic Simulator");
    private Color paint = new Color(122, 145, 21);

    public Menu_GUI()
    {
        this.setLocation(600, 350);

        setLayout(new BorderLayout());//Whole layout

        //panel1.setBackground()
        JPanel p_title = new JPanel(new BorderLayout());
        p_title.add(lbl_title);

        add(p_title, BorderLayout.NORTH);

        //button panel/
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel p_btn = new JPanel(new GridLayout(2,1));
        btn_play.addActionListener(this);
        btn_exit.addActionListener(this);

        btn_play.setBackground(paint);
        btn_exit.setBackground(paint);

        p_btn.add(btn_play);
        p_btn.add(btn_exit);

        panel2.add(p_btn);

        add(panel2, BorderLayout.CENTER);


        addWindowListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setVisible(true);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btn_play) {
            new Editing_GUI();
            this.dispose();
        } else if (source == btn_exit) {
            this.dispose();
        }
    }
}

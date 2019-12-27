package GUI;

import Objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class SimulatorValue extends JFrame implements WindowListener {
    //test values
    int roadX=9, roadY=0;
    int roadX2=9, roadY2=10;
    int carX =9, carY=0;
    int trafficX = 8, trafficY = 9;

    int carSpeed = 500;
    int trafficLightSpeed = 4000;
    // for map grid
    private int ROW = 20, COL = 20, GAP = 0;

    JPanel panel1 = new JPanel(new GridLayout(ROW, COL, GAP, GAP));//game panel
    private JLabel[][] ground = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry = new Simulator_Obj[ROW][COL];


    //timers
    Timer carTimer = new Timer(carSpeed, new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            moveCars();
            showCars();
        }
    });

    Timer trafficTimer = new Timer(trafficLightSpeed, new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            int loop_size = trafficAry.size();

            for(int i = 0; i < loop_size; i++)
            {
                int temp_x = trafficAry.get(i).getX_location();
                int temp_y = trafficAry.get(i).getY_location();
                trafficAry.get(i).set_is_red();
                ground[temp_x][temp_y].setIcon(new ImageIcon(objAry[temp_x][temp_y].getPic_location()));
            }
        }
    });


    private ArrayList<Vehicle_Block> vehicleAry = new ArrayList<>();

    private ArrayList<TrafficLight_Obj> trafficAry= new ArrayList<>();
    //protected int available_id = 0;

    Color paint = new Color(122, 145, 21);

    Simulator_Obj underObjectTemp;

    void showMap()
    {
        try
        {
            int i = 0;
            while (i < ROW)
            {
                for (int j = 0; j < COL; j++)
                {
                    objAry[i][j] = new Grass_Block();
                }
                i++;
            }
        }catch (Exception e) {
            System.out.print("Error");
        }

        try {
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    ground[i][j] = new JLabel();
                    ground[i][j].setIcon(new ImageIcon(objAry[i][j].getPic_location()));
                    ground[i][j].setPreferredSize(new Dimension(46, 46));
                    panel1.add(ground[i][j]);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Map Generate Error");
            dispose();
        }
    }

    void showCars()
    {
        int loop_size = vehicleAry.size();
        Vehicle_Block[] temp = new Vehicle_Block[loop_size];

        for(int j = 0; j < temp.length; j++){
            temp[j] = vehicleAry.get(j);
        }



        for(int i = 0; i < loop_size; i++)
        {
            int temp_x = temp[i].getX_location();
            int temp_y = temp[i].getY_location();
            //System.out.println("status" + count);

            if(temp_y >= COL)
            {
                vehicleAry.remove(i);
               //System.out.println("status" + vehicleAry);
            }
            else{
                if(!objAry[temp_x][temp_y].getType().equals("Vehicle Block"))
                {
                    underObjectTemp =  objAry[temp_x][temp_y];
                    objAry[temp_x][temp_y] = temp[i];
                    objAry[temp_x][temp_y].setUnder(underObjectTemp);
                    ground[temp_x][temp_y].setIcon(new ImageIcon(objAry[temp_x][temp_y].getPic_location()));
                }
            }



        }
    }

    void trafficTimerSwitch(boolean status)
    {
        if(status)
        {
            trafficTimer.start();
        }
        else{
            trafficTimer.stop();
        }
    }

    private void moveCars(){
        for(int i = 0; i< vehicleAry.size(); i++)
        {
            int temp_x = vehicleAry.get(i).getX_location();
            int temp_y = vehicleAry.get(i).getY_location();
            System.out.println("Current Location " + vehicleAry.get(i).getY_location());

            if(temp_y == COL-1)
            {
                vehicleAry.get(i).drive();
                objAry[temp_x][temp_y] = objAry[temp_x][temp_y].getUnder();
                ground[temp_x][temp_y].setIcon(new ImageIcon(objAry[temp_x][temp_y].getPic_location()));
            }
            else{
                System.out.println("step 1");
                if(objAry[temp_x][temp_y+1].isDrivable())
                {
                    System.out.println("step 2");
                    if(objAry[temp_x][temp_y].getUnder().get_has_traffic_light())
                    {
                        System.out.println("step 3");
                        if(!objAry[temp_x][temp_y].getUnder().getTraffic_light().get_is_red())
                        {
                            System.out.println("step 4");
                            vehicleAry.get(i).drive();
                            objAry[temp_x][temp_y] = objAry[temp_x][temp_y].getUnder();
                            ground[temp_x][temp_y].setIcon(new ImageIcon(objAry[temp_x][temp_y].getPic_location()));
                        }
                    }
                    else{
                        vehicleAry.get(i).drive();
                        objAry[temp_x][temp_y] = objAry[temp_x][temp_y].getUnder();
                        ground[temp_x][temp_y].setIcon(new ImageIcon(objAry[temp_x][temp_y].getPic_location()));
                    }
                }
            }

            //System.out.println("it moves (2)" + vehicleAry.size() + "/" + vehicleAry.get(i).getY_location());
        }
    }

    void addRoad(int x, int y, int w, char dir, boolean add_trafficLight){
        Road_Obj new_road = new Road_Obj(w, dir);

        for(int i=0; i<new_road.getWidth(); i++)
        {
            underObjectTemp = objAry[x][y];
            objAry[x][y] = new_road.getR_obj_ary(i);
            objAry[x][y].setUnder(underObjectTemp);
            ground[x][y].setIcon(new ImageIcon(objAry[x][y].getPic_location()));
            y++;
        }

        if(add_trafficLight)
        {
            TrafficLight_Obj new_trafficLight = new TrafficLight_Obj(trafficX, trafficY);
            new_road.addTrafficLight(new_trafficLight);
            this.addTrafficLight(trafficX, trafficY, 'E', new_trafficLight);

            trafficAry.add(new_trafficLight);
        }

    }

    private void addTrafficLight(int x, int y, char dir, TrafficLight_Obj obj){
        underObjectTemp = objAry[x][y];
        objAry[x][y] = obj;
        objAry[x][y].setUnder(underObjectTemp);
        objAry[x][y].setTraffic_light(obj);
        ground[x][y].setIcon(new ImageIcon(objAry[x][y].getPic_location()));
    }

    void spawnCar(int x, int y)
    {
        vehicleAry.add(new Vehicle_Block(x, y));
    }

    //Asg1
    /*void changeTraffic(boolean isRed)
    {
        objAry[trafficX][trafficY].getTraffic_light().set_is_red(isRed);
        ground[trafficX][trafficY].setIcon(new ImageIcon(objAry[trafficX][trafficY].getPic_location()));
    }*/


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

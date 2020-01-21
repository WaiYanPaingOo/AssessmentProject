package GUI;

import Objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

public class SimulatorValue extends JFrame implements WindowListener {
    //test values
    int roadX=8, roadY=0;
    int roadX2=8, roadY2=6;
    int roadX3=8, roadY3=12;
    int roadX4=8, roadY4=18;

    int roadXVb1 = 12, roadYVb1 = 8;
    int roadXVb2 = 18, roadYVb2 = 8;

    int roadXVt1 = 0, roadYVt1 = 8;

    int carX =8, carY=0;
    int trafficX = 8, trafficY = 9;

    int carSpeed = 500;
    int trafficLightSpeed = 4000;
    // for map grid
    private int ROW = 24, COL = 24, GAP = 0;

    JPanel panel1 = new JPanel(new GridLayout(ROW, COL, GAP, GAP));//game panel
    private JLabel[][] ground_G = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry_G = new Simulator_Obj[ROW][COL];

    private JLabel[][] ground_E = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry_E = new Simulator_Obj[ROW][COL];
    private Random r = new Random();

    private ArrayList<Vehicle_Block> vehicleAry = new ArrayList<>();

    private ArrayList<TrafficLight_Obj> trafficAry= new ArrayList<>();
    //protected int available_id = 0;

    Color paint = new Color(122, 145, 21);

    Simulator_Obj underObjectTemp;


    //timers
    Timer carTimer = new Timer(carSpeed, new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            moveCars();
            showCars();
            if(vehicleAry.size() < 6) {
                int x = r.nextInt();
                if (x % 8 == 0) {
                    spawnCar(carX, carY);
                    showCars();
                }
            }
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
                ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
            }
        }
    });

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
    void spawnCar(int x, int y)
    {
        vehicleAry.add(new Vehicle_Block(x, y, new char[]{'E'}));
    }

    //map
    void showMap()
    {
        try
        {
            int i = 0;
            while (i < ROW)
            {
                for (int j = 0; j < COL; j++)
                {
                    objAry_G[i][j] = new Grass_Block();
                }
                i++;
            }
        }catch (Exception e) {
            System.out.print("Error");
        }

        try {
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    ground_G[i][j] = new JLabel();
                    ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                    ground_G[i][j].setPreferredSize(new Dimension(46, 46));
                    panel1.add(ground_G[i][j]);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Map Generate Error");
            dispose();
        }
    }
    void clearCars()
    {
        int i = 0, temp_x=0, temp_y=0;
        int loop_size = vehicleAry.size();
        Vehicle_Block[] temp = new Vehicle_Block[loop_size];

        for(int j = 0; j < temp.length; j++){
            temp[j] = vehicleAry.get(j);
        }

        for(i = 0; i < temp.length; i++)
        {
            temp_x = temp[i].getX_location();
            temp_y = temp[i].getY_location();

            underObjectTemp = objAry_G[temp_x][temp_y].getUnder();
            objAry_G[temp_x][temp_y] = underObjectTemp;
            ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
        }
        vehicleAry.clear();
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
                if(!objAry_G[temp_x][temp_y].getType().equals("Vehicle Block"))
                {
                    underObjectTemp =  objAry_G[temp_x][temp_y];
                    objAry_G[temp_x][temp_y] = temp[i];
                    objAry_G[temp_x][temp_y].setUnder(underObjectTemp);
                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                }
            }

        }
    }

    private void moveCars(){
        for(int i = 0; i< vehicleAry.size(); i++)
        {
            int temp_x = vehicleAry.get(i).getX_location();
            int temp_y = vehicleAry.get(i).getY_location();
           // System.out.println("Current Location " + vehicleAry.get(i).getY_location());

            if(temp_y == COL-1)
            {
                vehicleAry.get(i).drive();
                objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
            }
            else{
                //System.out.println("step 1");
                if(objAry_G[temp_x][temp_y+1].isDrivable())
                {
                    //System.out.println("step 2");
                    if(objAry_G[temp_x][temp_y].getUnder().get_has_traffic_light())
                    {
                        //System.out.println("step 3");
                        if(!objAry_G[temp_x][temp_y].getUnder().getTraffic_light().get_is_red())
                        {
                            //System.out.println("step 4");
                            vehicleAry.get(i).drive();
                            objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                            ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                        }
                    }
                    else{
                        vehicleAry.get(i).drive();
                        objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                    }
                }
            }

            //System.out.println("it moves (2)" + vehicleAry.size() + "/" + vehicleAry.get(i).getY_location());
        }
    }

    void addRoad(int x, int y, int ways, char rot){
        Simulator_Obj[][] temp_obj;
        if(ways == 4)
        {
            Road_Obj_4Way new_road_comp = new Road_Obj_4Way(ROW, COL,x,y);
            temp_obj = new_road_comp.get_objAry();
            int i = 0;
            while (i < ROW)
            {
                for (int j = 0; j < COL; j++)
                {
                    if(temp_obj[i][j].getType().equals("Grass Block"))
                    {

                    }
                    else{
                        System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
                        underObjectTemp = objAry_G[i][j];
                        objAry_G[i][j] = temp_obj[i][j];
                        objAry_G[i][j].setUnder(underObjectTemp);
                        ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                    }

                }
                i++;
            }
        }
        else if(ways == 1)
        {
            Road_Obj_1Way new_road_comp = new Road_Obj_1Way(ROW, COL,x, y, rot);
            temp_obj = new_road_comp.get_objAry();
            int i = 0;
            while (i < ROW)
            {
                for (int j = 0; j < COL; j++)
                {
                    if(temp_obj[i][j].getType().equals("Grass Block"))
                    {

                    }
                    else{
                        System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
                        underObjectTemp = objAry_G[i][j];
                        objAry_G[i][j] = temp_obj[i][j];
                        objAry_G[i][j].setUnder(underObjectTemp);
                        ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                    }

                }
                i++;
            }
        }
        else if(ways == 3)
        {

        }

//        Road_Obj new_road = new Road_Obj(w, dir);
//
//        for(int i=0; i<new_road.getWidth(); i++)
//        {
//            underObjectTemp = objAry_G[x][y];
//            objAry_G[x][y] = new_road.getR_obj_ary(i);
//            objAry_G[x][y].setUnder(underObjectTemp);
//            ground_G[x][y].setIcon(new ImageIcon(objAry_G[x][y].getPic_location()));
//            y++;
//        }
//        if(add_trafficLight)
//        {
//            TrafficLight_Obj new_trafficLight = new TrafficLight_Obj(trafficX, trafficY);
//            new_road.addTrafficLight(new_trafficLight);
//            this.addTrafficLight(trafficX, trafficY, 'E', new_trafficLight);
//
//            trafficAry.add(new_trafficLight);
//        }

    }

    private void addTrafficLight(int x, int y, char dir, TrafficLight_Obj obj){
        underObjectTemp = objAry_G[x][y];
        objAry_G[x][y] = obj;
        objAry_G[x][y].setUnder(underObjectTemp);
        objAry_G[x][y].setTraffic_light(obj);
        ground_G[x][y].setIcon(new ImageIcon(objAry_G[x][y].getPic_location()));
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

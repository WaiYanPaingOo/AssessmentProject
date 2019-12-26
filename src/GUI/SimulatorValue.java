package GUI;

import Objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class SimulatorValue extends JFrame implements WindowListener {
    //test values
    int roadX=9, roadY=0;
    int roadX2=9, roadY2=10;
    int carX =9, carY=0;
    int trafficX = 8, trafficY = 9;
    // for map grid
    private int ROW = 20, COL = 20, GAP = 0;

    JPanel panel1 = new JPanel(new GridLayout(ROW, COL, GAP, GAP));//game panel
    private JLabel[][] ground = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry = new Simulator_Obj[ROW][COL];


    private ArrayList<Vehicle_Block> vehicleAry = new ArrayList<>();
    //protected int available_id = 0;

    Color paint = new Color(122, 145, 21);

    Simulator_Obj underObjectTemp;

    public void showMap()
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

    public void showCars()
    {
        int loop_size = vehicleAry.size();
        Vehicle_Block[] temp = new Vehicle_Block[loop_size];

        for(int j = 0; j < temp.length; j++){
            temp[j] = vehicleAry.get(j);
        }


        int count = 0;
        for(int i = 0; i < loop_size; i++)
        {
            int temp_x = temp[i].getX_location();
            int temp_y = temp[i].getY_location();
            System.out.println("status" + count);
            count++;
            if(temp_y >= COL)
            {
                vehicleAry.remove(i);
               System.out.println("status" + vehicleAry);
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

    void moveCars(){
        for(int i = 0; i< vehicleAry.size(); i++)
        {

            System.out.println("it moves" + vehicleAry.size() + "/" + vehicleAry.get(i).getY_location());
            int temp_x = vehicleAry.get(i).getX_location();
            int temp_y = vehicleAry.get(i).getY_location();

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
                    if(objAry[temp_x][temp_y].get_has_traffic_light())
                    {
                        System.out.println("step 3");
                        if(!objAry[temp_x][temp_y].getTraffic_light().get_is_red())
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

    void addRoad(int x, int y, int w, char dir){
        Road_Obj new_road = new Road_Obj(w, dir);
        for(int i=0; i<new_road.getWidth(); i++)
        {
            underObjectTemp = objAry[x][y];
            objAry[x][y] = new_road.getV_obj_ary(i);
            objAry[x][y].setUnder(underObjectTemp);
            ground[x][y].setIcon(new ImageIcon(objAry[x][y].getPic_location()));
            y++;
        }
    }
    public void spawnCar(int x, int y)
    {
        vehicleAry.add(new Vehicle_Block(x, y));
    }

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

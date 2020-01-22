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

    int carSpeed = 100;
    int trafficLightSpeed = 4000;
    // for map grid
    private int ROW = 24, COL = 24, GAP = 0;

    JPanel panel1 = new JPanel(new GridLayout(ROW, COL, GAP, GAP));//game panel
    private JLabel[][] ground_G = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry_G = new Simulator_Obj[ROW][COL];

    private JLabel[][] ground_E = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry_E = new Simulator_Obj[ROW][COL];
    private Random r = new Random();

    private ArrayList<Road_Obj_4Way> roadObj4Ways_traffic = new ArrayList<>();
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
            int loop_size = roadObj4Ways_traffic.size();
            for(int i = 0; i < loop_size; i++){
                System.out.println(" outer loop");
                roadObj4Ways_traffic.get(i).changeTrafficLight();
                TrafficLight_Obj[] temp_ary = roadObj4Ways_traffic.get(i).getTf_ary();
                for(int j = 0; j < temp_ary.length; j++){
                    System.out.println("inner loop");
                    System.out.println("j - "+ j);
                    int temp_x = temp_ary[j].getY_location();
                    int temp_y = temp_ary[j].getX_location();
                    System.out.println("x - "+ temp_x + " y - "+ temp_y);
                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                }
            }
//            int loop_size = trafficAry.size();
//
//            for(int i = 0; i < loop_size; i++)
//            {
//                int temp_x = trafficAry.get(i).getX_location();
//                int temp_y = trafficAry.get(i).getY_location();
//                trafficAry.get(i).set_is_red();
//                ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
//            }
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
                    ground_G[i][j].setPreferredSize(new Dimension(31, 31));
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
            char temp_d = temp[i].getDirection()[0];

            if(temp_x >= COL || temp_x < 0 || temp_y >= ROW || temp_y < 0){
                System.out.println("X value: "+temp_x + " y value: " + temp_y + "i value: "+ i + "array status: "+ vehicleAry.size());
                vehicleAry.remove(i);
                showCars();
                break;
            }
            else{
                System.out.println("X value: "+temp_x + " y value: " + temp_y);
                if(!objAry_G[temp_x][temp_y].getType().equals("Vehicle Block"))
                {
                    underObjectTemp =  objAry_G[temp_x][temp_y];
                    objAry_G[temp_x][temp_y] = temp[i];
                    objAry_G[temp_x][temp_y].setUnder(underObjectTemp);
                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                }
            }



//            if(temp_d == 'E')
//            {
//                if(temp_y >= COL)
//                {
//                    vehicleAry.remove(i);
//                    //System.out.println("status" + vehicleAry);
//                }
//                else{
//                    if(!objAry_G[temp_x][temp_y].getType().equals("Vehicle Block"))
//                    {
//                        underObjectTemp =  objAry_G[temp_x][temp_y];
//                        objAry_G[temp_x][temp_y] = temp[i];
//                        objAry_G[temp_x][temp_y].setUnder(underObjectTemp);
//                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
//                    }
//                }
//            }
//            else if(temp_d == 'N')
//            {
//                if(temp_x < 0)
//                {
//                    System.out.println("status temp_x -" + temp_x + "Remove ___________________________ " + vehicleAry.size());
//                    vehicleAry.remove(i);
//                    System.out.println("status temp_x -" + temp_x + "Remove ___________________________"+ vehicleAry.size());
//                }
//                else{
//                    if(!objAry_G[temp_x][temp_y].getType().equals("Vehicle Block"))
//                    {
//                        underObjectTemp =  objAry_G[temp_x][temp_y];
//                        objAry_G[temp_x][temp_y] = temp[i];
//                        objAry_G[temp_x][temp_y].setUnder(underObjectTemp);
//                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
//                    }
//                }
//            }
//            else if(temp_d == 'S')
//            {
//                if(temp_x >= ROW)
//                {
//                    vehicleAry.remove(i);
//                    //System.out.println("status" + vehicleAry);
//                }
//                else{
//                    if(!objAry_G[temp_x][temp_y].getType().equals("Vehicle Block"))
//                    {
//                        underObjectTemp =  objAry_G[temp_x][temp_y];
//                        objAry_G[temp_x][temp_y] = temp[i];
//                        objAry_G[temp_x][temp_y].setUnder(underObjectTemp);
//                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
//                    }
//                }
//            }
//            else if(temp_d == 'W')
//            {
//                if(temp_y < 0)
//                {
//                    vehicleAry.remove(i);
//                    //System.out.println("status" + vehicleAry);
//                }
//                else{
//                    if(!objAry_G[temp_x][temp_y].getType().equals("Vehicle Block"))
//                    {
//                        underObjectTemp =  objAry_G[temp_x][temp_y];
//                        objAry_G[temp_x][temp_y] = temp[i];
//                        objAry_G[temp_x][temp_y].setUnder(underObjectTemp);
//                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
//                    }
//                }
//            }
        }
    }

    private boolean checkDir(char[] dir, int x, int y, char d){
        for(int i = 0;i < dir.length; i++){
            if(dir[i] == d)
            {
                return true;
            }
        }
        return false;
    }

    private void moveCars(){
        for(int i = 0; i< vehicleAry.size(); i++)
        {
            int temp_x = vehicleAry.get(i).getX_location();
            int temp_y = vehicleAry.get(i).getY_location();
            ArrayList<Character> possible_D = new ArrayList<>();
            if(temp_x >= COL || temp_x < 0 || temp_y >= ROW || temp_y < 0){

            }
            else{
                System.out.println("Current Location " + vehicleAry.get(i).getY_location());
                System.out.println("Step 1: Get Direction of Car");
                if(vehicleAry.get(i).getDirection()[0] == 'E') {
                    if(temp_y == COL-1)
                    {
                        vehicleAry.get(i).drive();
                        objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                    }
                    else{
                        System.out.println("Step 2: Check Drivable");
                        if(objAry_G[temp_x-1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x-1][temp_y].getDirection(),temp_x,temp_y,'E')) {
                                possible_D.add('N');
                                System.out.print("N is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y+1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y+1].getDirection(),temp_x,temp_y,'E')){
                                possible_D.add('E');
                                System.out.print("E is marked");
                            }
                        }
                        if(objAry_G[temp_x+1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x+1][temp_y].getDirection(),temp_x,temp_y,'E')){
                                possible_D.add('S');
                                System.out.print("S is marked");
                            }
                        }
                        if(possible_D.size() > 0){
                            System.out.println("Step 4: Check Traffic Light");
                            if(objAry_G[temp_x][temp_y].getUnder().get_has_traffic_light())
                            {
                                System.out.println("step 5: check traffic status");
                                if(!objAry_G[temp_x][temp_y].getUnder().getTraffic_light().get_is_red())
                                {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  "+ nextStep);
                                    System.out.println("Random Direction "+ possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }
                            else{
                                if(vehicleAry.get(i).isJust_rotate())
                                {
                                    System.out.println("Step 8: Drive Simple");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                                else {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  " + nextStep);
                                    System.out.println("Random Direction " + possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    if(possible_D.get(nextStep) == vehicleAry.get(i).getDirection()[0]){
                                        this.vehicleAry.get(i).setJust_rotate(false);
                                        System.out.println("Straight");
                                    }
                                    else{
                                        this.vehicleAry.get(i).setJust_rotate(true);
                                        System.out.println("Just Rotate");
                                    }
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }

                        }
                    }
                }
                else if(vehicleAry.get(i).getDirection()[0] == 'W'){
                    if(temp_y == 1)
                    {
                        vehicleAry.get(i).drive();
                        objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                    }
                    else{
                        System.out.println("Step 2: Check Drivable");
                        if(objAry_G[temp_x-1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x-1][temp_y].getDirection(),temp_x,temp_y,'W')) {
                                possible_D.add('N');
                                System.out.print("N is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y-1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y-1].getDirection(),temp_x,temp_y,'W')){
                                possible_D.add('W');
                                System.out.print("W is marked");
                            }
                        }
                        if(objAry_G[temp_x+1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x+1][temp_y].getDirection(),temp_x,temp_y,'W')){
                                possible_D.add('S');
                                System.out.print("S is marked");
                            }
                        }
                        if(possible_D.size() > 0){
                            System.out.println("Step 4: Check Traffic Light");
                            if(objAry_G[temp_x][temp_y].getUnder().get_has_traffic_light())
                            {
                                System.out.println("step 5: check traffic status");
                                if(!objAry_G[temp_x][temp_y].getUnder().getTraffic_light().get_is_red())
                                {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  "+ nextStep);
                                    System.out.println("Random Direction "+ possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }
                            else{
                                if(vehicleAry.get(i).isJust_rotate())
                                {
                                    System.out.println("Step 8: Drive Simple");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                                else {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  " + nextStep);
                                    System.out.println("Random Direction " + possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    if(possible_D.get(nextStep) == vehicleAry.get(i).getDirection()[0]){
                                        this.vehicleAry.get(i).setJust_rotate(false);
                                        System.out.println("Straight");
                                    }
                                    else{
                                        this.vehicleAry.get(i).setJust_rotate(true);
                                        System.out.println("Just Rotate");
                                    }
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }

                        }
                    }
                }
                else if(vehicleAry.get(i).getDirection()[0] == 'N'){
                    if(temp_x == 0)//temp_y == COL-1
                    {
                        vehicleAry.get(i).drive();
                        objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                    }
                    else{
                        System.out.println("Step 2: Check Drivable");
                        if(objAry_G[temp_x][temp_y-1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y-1].getDirection(),temp_x,temp_y,'N')) {
                                possible_D.add('W');
                                System.out.print("W is marked");
                            }
                        }
                        if(objAry_G[temp_x-1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x-1][temp_y].getDirection(),temp_x,temp_y,'N')){
                                possible_D.add('N');
                                System.out.print("N is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y+1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y+1].getDirection(),temp_x,temp_y,'N')){
                                possible_D.add('E');
                                System.out.print("E is marked");
                            }
                        }
                        if(possible_D.size() > 0){
                            System.out.println("Step 4: Check Traffic Light");
                            if(objAry_G[temp_x][temp_y].getUnder().get_has_traffic_light())
                            {
                                System.out.println("step 5: check traffic status");
                                if(!objAry_G[temp_x][temp_y].getUnder().getTraffic_light().get_is_red())
                                {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  "+ nextStep);
                                    System.out.println("Random Direction "+ possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }
                            else{
                                if(vehicleAry.get(i).isJust_rotate())
                                {
                                    System.out.println("Step 8: Drive Simple");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                                else {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  " + nextStep);
                                    System.out.println("Random Direction " + possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    if(possible_D.get(nextStep) == vehicleAry.get(i).getDirection()[0]){
                                        this.vehicleAry.get(i).setJust_rotate(false);
                                        System.out.println("Straight");
                                    }
                                    else{
                                        this.vehicleAry.get(i).setJust_rotate(true);
                                        System.out.println("Just Rotate");
                                    }
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }

                        }
                    }
                }
                else if(vehicleAry.get(i).getDirection()[0] == 'S'){
                    if(temp_x == ROW-1)
                    {
                        vehicleAry.get(i).drive();
                        objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                    }
                    else{
                        System.out.println("Step 2: Check Drivable");
                        if(objAry_G[temp_x][temp_y-1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y-1].getDirection(),temp_x,temp_y,'S')) {
                                possible_D.add('W');
                                System.out.print("W is marked");
                            }
                        }
                        if(objAry_G[temp_x+1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x+1][temp_y].getDirection(),temp_x,temp_y,'S')){
                                possible_D.add('S');
                                System.out.print("S is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y+1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y+1].getDirection(),temp_x,temp_y,'S')){
                                possible_D.add('E');
                                System.out.print("E is marked");
                            }
                        }
                        if(possible_D.size() > 0){
                            System.out.println("Step 4: Check Traffic Light");
                            if(objAry_G[temp_x][temp_y].getUnder().get_has_traffic_light())
                            {
                                System.out.println("step 5: check traffic status");
                                if(!objAry_G[temp_x][temp_y].getUnder().getTraffic_light().get_is_red())
                                {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  "+ nextStep);
                                    System.out.println("Random Direction "+ possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }
                            else{
                                if(vehicleAry.get(i).isJust_rotate())
                                {
                                    System.out.println("Step 8: Drive Simple");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                                else {
                                    System.out.println("Step 6: Random block");
                                    Random temp_r = new Random();
                                    int nextStep = temp_r.nextInt(possible_D.size());
                                    System.out.println("Random number  " + nextStep);
                                    System.out.println("Random Direction " + possible_D.get(nextStep));
                                    System.out.println("Step 7: Change Car Direction ");
                                    if(possible_D.get(nextStep) == vehicleAry.get(i).getDirection()[0]){
                                        this.vehicleAry.get(i).setJust_rotate(false);
                                        System.out.println("Straight");
                                    }
                                    else{
                                        this.vehicleAry.get(i).setJust_rotate(true);
                                        System.out.println("Just Rotate");
                                    }
                                    vehicleAry.get(i).setDirection(new char[]{possible_D.get(nextStep)});
                                    System.out.println("Step 8: Drive ");
                                    vehicleAry.get(i).drive();
                                    objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                                }
                            }

                        }
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
            roadObj4Ways_traffic.add(new_road_comp);
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
                        //System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
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
                        //System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
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

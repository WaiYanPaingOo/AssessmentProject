package GUI;

import Objects.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SimulatorValue extends JFrame implements WindowListener {
    // for map grid
    private int ROW = 24, COL = 24, GAP = 0, ROADSPAWNX = 11, ROADSPAWNY = 11;
    //Default Road Spawning Point
    int default_road_spawn_X = ROADSPAWNX;
    int default_road_spawn_Y = ROADSPAWNY;
    int carSpeed = 130;
    private int trafficLightSpeed = 4000;

    JPanel panel1 = new JPanel(new GridLayout(ROW, COL, GAP, GAP));//game panel

    private JLabel[][] ground_G = new JLabel[ROW][COL];//game array
    private Simulator_Obj[][] objAry_G = new Simulator_Obj[ROW][COL];
    Simulator_Obj underObjectTemp;

    //Editing Map Values
    int current_edit_road_type;
    String current_edit_road_rotation;
    private Road_Obj_4Way current_road_4;
    private Road_Obj_3Way current_road_3;
    private Road_Obj_1Way current_road_1;

    //temp array to accept Objects from road objects
    private Simulator_Obj[][] current_edit_obj;
    //random
    private Random r = new Random();
    //array lists to store road objects to save in csv file
    private ArrayList<Road_Obj_4Way> roadObj4Ways_traffic = new ArrayList<>();
    private ArrayList<Road_Obj_3Way> roadObj3Ways_traffic = new ArrayList<>();
    private ArrayList<Road_Obj_1Way> roadObj1Ways_traffic = new ArrayList<>();

    //store vehicles on the map
    private ArrayList<Vehicle_Block> vehicleAry = new ArrayList<>();

    //paint for buttons
    Color paint = new Color(122, 145, 21);
    //paint for panel
    Color paint_background = new Color(70, 70, 72);



    //timers
    Timer carTimer = new Timer(carSpeed, new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            moveCars();
            showCars();
            if(vehicleAry.size() < 12) {
                int x = r.nextInt();
                if (x % 8 == 0) {
                    ArrayList<Integer> possible_X = new ArrayList<>();
                    ArrayList<Integer> possible_Y = new ArrayList<>();
                    ArrayList<Character> possible_C = new ArrayList<>();
                    for(int i = 0; i < ROW -1; i++){
                        if(objAry_G[i][0].getType().equals("Road Block")){
                            if(checkDir(objAry_G[i][0].getDirection(),'E') && objAry_G[i][0].getDirection().length == 1){
                                possible_X.add(i);
                                possible_Y.add(0);
                                possible_C.add('E');
                            }
                        }
                    }
                    for(int i = 0; i < COL -1; i++){
                        if(objAry_G[0][i].getType().equals("Road Block")){
                            if(checkDir(objAry_G[0][i].getDirection(),'S') && objAry_G[0][i].getDirection().length == 1){
                                possible_X.add(0);
                                possible_Y.add(i);
                                possible_C.add('S');
                            }
                        }
                    }
                    for(int i = 0; i < ROW -1; i++){
                        if(objAry_G[i][COL-1].getType().equals("Road Block")){
                            if(checkDir(objAry_G[i][COL-1].getDirection(),'W') && objAry_G[i][COL-1].getDirection().length == 1){
                                possible_X.add(i);
                                possible_Y.add(COL-1);
                                possible_C.add('W');
                            }
                        }
                    }
                    for(int i = 0; i < COL -1; i++){
                        if(objAry_G[ROW-1][i].getType().equals("Road Block")){
                            if(checkDir(objAry_G[ROW-1][i].getDirection(),'N') && objAry_G[ROW-1][i].getDirection().length == 1){
                                possible_X.add(ROW-1);
                                possible_Y.add(i);
                                possible_C.add('N');
                            }
                        }
                    }
                    if(possible_X.size() > 0){
                        Random temp_r = new Random();
                        int block = temp_r.nextInt(possible_X.size());
                        int temp_X = possible_X.get(block);
                        int temp_Y = possible_Y.get(block);
                        char temp_C = possible_C.get(block);
                        spawnCar(temp_X, temp_Y,temp_C);
                        showCars();
                    }

                }
            }
        }
    });
    Timer trafficTimer = new Timer(trafficLightSpeed, new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            int loop_size_4 = roadObj4Ways_traffic.size();
            int loop_size_3 = roadObj3Ways_traffic.size();
            for(int i = 0; i < loop_size_4; i++){
                roadObj4Ways_traffic.get(i).changeTrafficLight();
                TrafficLight_Obj[] temp_ary = roadObj4Ways_traffic.get(i).getTf_ary();
                for(int j = 0; j < temp_ary.length; j++){
                    int temp_x = temp_ary[j].getX_location();
                    int temp_y = temp_ary[j].getY_location();
                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                }
            }
            for(int i = 0; i < loop_size_3; i++){
                System.out.println(" outer loop");
                roadObj3Ways_traffic.get(i).changeTrafficLight();
                TrafficLight_Obj[] temp_ary = roadObj3Ways_traffic.get(i).getTf_ary();
                for(int j = 0; j < temp_ary.length; j++){
                    System.out.println("inner loop");
                    System.out.println("j - "+ j);
                    int temp_x = temp_ary[j].getX_location();
                    int temp_y = temp_ary[j].getY_location();
                    System.out.println("x - "+ temp_x + " y - "+ temp_y);
                    ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                }
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
    void spawnCar(int x, int y, char d)
    {
        String dir;
        if(d == 'W'){
            dir = "/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_W.png";
        }
        else if(d == 'E'){
            dir = "/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_T.png";
        }
        else if(d == 'N'){
            dir = "/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_N.png";
        }
        else{
            dir = "/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_S.png";
        }
        vehicleAry.add(new Vehicle_Block(x, y, new char[]{d},dir));
    }
    //editing
    void showEditRoad(int X, int Y,int type, String dir){
        current_edit_road_type = type;
        current_edit_road_rotation = dir;
        if(type == 1){
            Road_Obj_1Way new_road_comp = new Road_Obj_1Way(ROW, COL,X, Y, dir);
            current_road_1 = new_road_comp;
            current_edit_obj = new_road_comp.get_objAry();
        }
        else if(type == 4){
            Road_Obj_4Way new_road_comp = new Road_Obj_4Way(ROW, COL,X, Y);
            current_road_4 = new_road_comp;
            current_edit_obj = new_road_comp.get_objAry();
        }
        else if(type == 3){
            Road_Obj_3Way new_road_comp = new Road_Obj_3Way(ROW, COL,X, Y, dir);
            current_road_3 = new_road_comp;
            current_edit_obj = new_road_comp.get_objAry();
        }
        int i = 0;
        while (i < ROW)
        {
            for (int j = 0; j < COL; j++)
            {
                if(current_edit_obj[i][j].getType().equals("Grass Block"))
                {

                }
                else{
                    //System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
                    underObjectTemp = objAry_G[i][j];
                    objAry_G[i][j] = current_edit_obj[i][j];
                    objAry_G[i][j].setUnder(underObjectTemp);
                    ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                }

            }
            i++;
        }
    }
    void rotateEditingObj(int type){
        if(type == 1){
            current_road_1.rotate();
            current_edit_obj = current_road_1.get_objAry();
            current_edit_road_rotation = current_road_1.getRotation();
        }
        else if(type == 3){
            current_road_3.rotate();
            current_edit_obj = current_road_3.get_objAry();
            current_edit_road_rotation = current_road_3.getRotation();
        }
        int i = 0;
        while (i < ROW)
        {
            for (int j = 0; j < COL; j++)
            {
                if(current_edit_obj[i][j].getType().equals("Grass Block"))
                {

                }
                else{
                    //System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
                    underObjectTemp = objAry_G[i][j];
                    objAry_G[i][j] = current_edit_obj[i][j];
                    objAry_G[i][j].setUnder(underObjectTemp);
                    ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                }

            }
            i++;
        }
    }

    void undoEditing(){
        int i = 0;
        while (i < ROW)
        {
            for (int j = 0; j < COL; j++)
            {
                if(current_edit_obj[i][j].getType().equals("Grass Block"))
                {

                }
                else{
                    //System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
                    underObjectTemp = objAry_G[i][j].getUnder();
                    objAry_G[i][j] = underObjectTemp;
                    ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                }

            }
            i++;
        }
    }
    void endEditing(int type){
        default_road_spawn_X = ROADSPAWNX;
        default_road_spawn_Y = ROADSPAWNY;
        if(type == 4){
            roadObj4Ways_traffic.add(current_road_4);
        }
        else if(type == 3){
            roadObj3Ways_traffic.add(current_road_3);
        }
        else if(type == 1){
            roadObj1Ways_traffic.add(current_road_1);
        }


    }

    void moveEditRoad(int type, char dir, String rot){
            if(dir == 'N'){
                default_road_spawn_X = default_road_spawn_X - 1;
            }
            else if(dir == 'S'){
                default_road_spawn_X = default_road_spawn_X + 1;
            }
            else if(dir == 'W'){
                default_road_spawn_Y = default_road_spawn_Y - 1;
            }
            else if(dir == 'E'){
                default_road_spawn_Y = default_road_spawn_Y + 1;
            }
            int i = 0;
            while (i < ROW)
            {
                for (int j = 0; j < COL; j++)
                {
                    if(current_edit_obj[i][j].getType().equals("Grass Block"))
                    {

                    }
                    else{
                        //System.out.println("i value " + i + " j value" + j + " type " + temp_obj[i][j].getType());
                        underObjectTemp = objAry_G[i][j].getUnder();
                        objAry_G[i][j] = underObjectTemp;
                        ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
                    }

                }
                i++;
            }
            showEditRoad(default_road_spawn_X,default_road_spawn_Y, type, rot);
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
    void clearMap()
    {
        int i = 0;
        while (i < ROW)
        {
            for (int j = 0; j < COL; j++)
            {
                objAry_G[i][j] = new Grass_Block();
                ground_G[i][j].setIcon(new ImageIcon(objAry_G[i][j].getPic_location()));
            }
            i++;
        }
        roadObj4Ways_traffic.clear();
        roadObj3Ways_traffic.clear();
        roadObj1Ways_traffic.clear();
    }
    void saveMap() throws IOException {
        String filename = JOptionPane.showInputDialog("Enter File Name");
        FileWriter csvWriter = new FileWriter(filename + ".csv");
        for(int i = 0; i < roadObj1Ways_traffic.size(); i++){
            csvWriter.append("1");
            csvWriter.append(",");
            csvWriter.append(Integer.toString(roadObj1Ways_traffic.get(i).getX()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(roadObj1Ways_traffic.get(i).getY()));
            csvWriter.append(",");
            csvWriter.append(roadObj1Ways_traffic.get(i).getRotation());
            csvWriter.append("\n");
        }
        for(int i = 0; i < roadObj4Ways_traffic.size(); i++){
            csvWriter.append("4");
            csvWriter.append(",");
            csvWriter.append(Integer.toString(roadObj4Ways_traffic.get(i).getX()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(roadObj4Ways_traffic.get(i).getY()));
            csvWriter.append(",");
            csvWriter.append("H");
            csvWriter.append("\n");
        }
        for(int i = 0; i < roadObj3Ways_traffic.size(); i++){
            csvWriter.append("3");
            csvWriter.append(",");
            csvWriter.append(Integer.toString(roadObj3Ways_traffic.get(i).getX()));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(roadObj3Ways_traffic.get(i).getY()));
            csvWriter.append(",");
            csvWriter.append(roadObj3Ways_traffic.get(i).getRotation());
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        JOptionPane.showMessageDialog(null, "File Saved As " + filename);

    }
    void loadMap(){
        JFileChooser chooser = new JFileChooser("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment");
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getAbsolutePath());

            File f=new File(chooser.getSelectedFile().getAbsolutePath());
            try{
                Scanner sc=new Scanner(f);
                System.out.println(sc.hasNextLine());
                int count = 0;
                while (sc.hasNextLine()) {
                    count++;
                    sc.nextLine();
                }
                Scanner sc2=new Scanner(f);
                clearMap();
                for(int i = 0; i < count; i++){
                    String str=sc2.nextLine();
                    String[] res = str.split(",");
                    addRoad(Integer.parseInt(res[1]), Integer.parseInt(res[2]), Integer.parseInt(res[0]), res[3]);
                }



            }catch(Exception e)
            {
                System.out.print(e.toString());
            }


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
        }
    }

    private boolean checkDir(char[] dir, char d){
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
                            if(checkDir(objAry_G[temp_x-1][temp_y].getDirection(),'E')) {
                                possible_D.add('N');
                                System.out.print("N is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y+1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y+1].getDirection(),'E')){
                                possible_D.add('E');
                                System.out.print("E is marked");
                            }
                        }
                        if(objAry_G[temp_x+1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x+1][temp_y].getDirection(),'E')){
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
                    if(temp_y == 0)
                    {
                        vehicleAry.get(i).drive();
                        objAry_G[temp_x][temp_y] = objAry_G[temp_x][temp_y].getUnder();
                        ground_G[temp_x][temp_y].setIcon(new ImageIcon(objAry_G[temp_x][temp_y].getPic_location()));
                    }
                    else{
                        System.out.println("Step 2: Check Drivable");
                        if(objAry_G[temp_x-1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x-1][temp_y].getDirection(),'W')) {
                                possible_D.add('N');
                                System.out.print("N is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y-1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y-1].getDirection(),'W')){
                                possible_D.add('W');
                                System.out.print("W is marked");
                            }
                        }
                        if(objAry_G[temp_x+1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x+1][temp_y].getDirection(),'W')){
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
                            if(checkDir(objAry_G[temp_x][temp_y-1].getDirection(),'N')) {
                                possible_D.add('W');
                                System.out.print("W is marked");
                            }
                        }
                        if(objAry_G[temp_x-1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x-1][temp_y].getDirection(),'N')){
                                possible_D.add('N');
                                System.out.print("N is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y+1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y+1].getDirection(),'N')){
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
                            if(checkDir(objAry_G[temp_x][temp_y-1].getDirection(),'S')) {
                                possible_D.add('W');
                                System.out.print("W is marked");
                            }
                        }
                        if(objAry_G[temp_x+1][temp_y].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x+1][temp_y].getDirection(),'S')){
                                possible_D.add('S');
                                System.out.print("S is marked");
                            }
                        }
                        if(objAry_G[temp_x][temp_y+1].isDrivable()){
                            System.out.println("Step 3: Check Direction");
                            if(checkDir(objAry_G[temp_x][temp_y+1].getDirection(),'S')){
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

    void addRoad(int x, int y, int ways, String rot){
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
            roadObj1Ways_traffic.add(new_road_comp);
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
            Road_Obj_3Way new_road_comp = new Road_Obj_3Way(ROW, COL,x, y, rot);
            roadObj3Ways_traffic.add(new_road_comp);
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

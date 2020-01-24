package Objects;

public class Road_Obj_3Way {
    private int ROW, COL,x, y;
    private String rotation;
    private Simulator_Obj[][] objAry;
    private TrafficLight_Obj tf_E;
    private TrafficLight_Obj tf_S;
    private TrafficLight_Obj tf_N;
    private TrafficLight_Obj tf_W;
    private TrafficLight_Obj[] tf_ary;
    private String[] rotationAry;

    public Road_Obj_3Way(int row, int col,int X , int Y, String rot) {
        this.x = X;
        this.y = Y;
        this.ROW = row;
        this.COL = col;
        this.rotation = rot;
        objAry = new Simulator_Obj[ROW][COL];

        rotationAry = new String[4];
        rotationAry[0] = "WE"; rotationAry[1] = "SN"; rotationAry[2] = "EW"; rotationAry[3] = "NS";
        this.build_road_structure();
    }
    public Simulator_Obj[][] get_objAry(){
        return this.objAry;
    }

    public void rotate() {
        if(this.rotation.equals("WE"))
        {
            this.rotation = "SN";
        }
        else if(this.rotation.equals("SN"))
        {
            this.rotation = "EW";
        }
        else if(this.rotation.equals("EW"))
        {
            this.rotation = "NS";
        }
        else if(this.rotation.equals("NS"))
        {
            this.rotation = "WE";
        }
        this.build_road_structure();
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String getRotation(){
        return this.rotation;
    }

    private void build_ary_grass()
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
    }

    public TrafficLight_Obj[] getTf_ary() {
        return tf_ary;
    }

    public void changeTrafficLight(){
        if(this.rotation.equals("WE")){
            this.tf_E.set_is_red();
            this.tf_N.set_is_red();
            this.tf_S.set_is_red();
        }
        else if(this.rotation.equals("EW")){
            this.tf_N.set_is_red();
            this.tf_S.set_is_red();
            this.tf_W.set_is_red();
        }
        else if(this.rotation.equals("SN")){
            this.tf_N.set_is_red();
            this.tf_E.set_is_red();
            this.tf_W.set_is_red();
        }
        else if(this.rotation.equals("NS")){
            this.tf_S.set_is_red();
            this.tf_E.set_is_red();
            this.tf_W.set_is_red();
        }

    }
    private void build_road_structure(){
        this.build_ary_grass();
        if(this.rotation.equals("WE")) {
            this.objAry[x][y] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x][y+1] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x][y+2] = new Road_Block(new char[]{'E','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WN.jpg");
            this.objAry[x][y+3] = new Road_Block(new char[]{'E','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_NE.jpg");
            this.objAry[x+1][y] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x+1][y+1] = new Road_Block(new char[]{'W','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x+1][y+2] = new Road_Block(new char[]{'N','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WS.jpg");
            this.objAry[x+1][y+3] = new Road_Block(new char[]{'S','E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_SE.jpg");
            this.objAry[x-1][y+2] = new Road_Block(new char[]{'E','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-1][y+3] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x-2][y+2] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-2][y+3] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+2][y+2] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x+2][y+3] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+3][y+3] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+3][y+2] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");

            tf_E = new TrafficLight_Obj(x-1,y+1, new char[]{'E'});
            tf_S = new TrafficLight_Obj(x-1,y+4, new char[]{'S'});
            tf_N = new TrafficLight_Obj(x+2,y+1, new char[]{'N'});
            tf_ary = new TrafficLight_Obj[3];
            tf_ary[0] = tf_E; tf_ary[1] = tf_S; tf_ary[2] = tf_N;
            tf_N.set_is_red();
            tf_S.set_is_red();


            this.objAry[x-1][y+1] = this.tf_E;
            this.objAry[x-1][y+4] = this.tf_S;
            this.objAry[x+2][y+1] = this.tf_N;

            this.objAry[x][y+1].setTraffic_light(this.tf_E);
            this.objAry[x][y+1].set_has_traffic_light(true);

            this.objAry[x+2][y+2].setTraffic_light(this.tf_N);
            this.objAry[x+2][y+2].set_has_traffic_light(true);

            this.objAry[x-1][y+3].setTraffic_light(this.tf_S);
            this.objAry[x-1][y+3].set_has_traffic_light(true);
        }
        else if(this.rotation.equals("SN")){
            this.objAry[x][y] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-1][y] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-2][y] = new Road_Block(new char[]{'W','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WS.jpg");
            this.objAry[x-3][y] = new Road_Block(new char[]{'E','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WN.jpg");
            this.objAry[x][y+1] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x-1][y+1] = new Road_Block(new char[]{'W','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x-2][y+1] = new Road_Block(new char[]{'W','E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_SE.jpg");
            this.objAry[x-3][y+1] = new Road_Block(new char[]{'N','E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_NE.jpg");
            this.objAry[x-2][y-1] = new Road_Block(new char[]{'W','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x-3][y-1] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x-2][y-2] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x-3][y-2] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x-2][y+2] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x-3][y+2] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x-3][y+3] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x-2][y+3] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");

            tf_W = new TrafficLight_Obj(x-1,y+2, new char[]{'W'});
            tf_E = new TrafficLight_Obj(x-4,y-1, new char[]{'E'});
            tf_N = new TrafficLight_Obj(x-1,y-1, new char[]{'N'});
            tf_ary = new TrafficLight_Obj[3];
            tf_ary[0] = tf_W; tf_ary[1] = tf_E; tf_ary[2] = tf_N;
            tf_N.set_is_red();

            this.objAry[x-1][y+2] = this.tf_W;
            this.objAry[x-4][y-1] = this.tf_E;
            this.objAry[x-1][y-1] = this.tf_N;

            this.objAry[x-1][y+2].setTraffic_light(this.tf_W);
            this.objAry[x-1][y+2].set_has_traffic_light(true);

            this.objAry[x-4][y-1].setTraffic_light(this.tf_E);
            this.objAry[x-4][y-1].set_has_traffic_light(true);

            this.objAry[x-1][y-1].setTraffic_light(this.tf_N);
            this.objAry[x-1][y-1].set_has_traffic_light(true);

        }
        else if(this.rotation.equals("EW")){
            this.objAry[x][y] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x][y-1] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x][y-2] = new Road_Block(new char[]{'W','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_SE.jpg");
            this.objAry[x][y-3] = new Road_Block(new char[]{'W','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WS.jpg");
            this.objAry[x-1][y] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x-1][y-1] = new Road_Block(new char[]{'E','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x-1][y-2] = new Road_Block(new char[]{'S','N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_NE.jpg");
            this.objAry[x-1][y-3] = new Road_Block(new char[]{'N','W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WN.jpg");
            this.objAry[x+1][y-2] = new Road_Block(new char[]{'W','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+1][y-3] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x+2][y-2] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+2][y-3] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-2][y-2] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x-2][y-3] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-3][y-3] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x-3][y-2] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");

            tf_W = new TrafficLight_Obj(x+1,y-1, new char[]{'W'});
            tf_S = new TrafficLight_Obj(x+1,y-4, new char[]{'S'});
            tf_N = new TrafficLight_Obj(x-2,y-1, new char[]{'N'});
            tf_ary = new TrafficLight_Obj[3];
            tf_ary[0] = tf_W; tf_ary[1] = tf_S; tf_ary[2] = tf_N;
            tf_N.set_is_red();
            tf_S.set_is_red();

            this.objAry[x+1][y-1] = this.tf_W;
            this.objAry[x+1][y-4] = this.tf_N;
            this.objAry[x-2][y-1] = this.tf_S;

            this.objAry[x][y-1].setTraffic_light(this.tf_W);
            this.objAry[x][y-1].set_has_traffic_light(true);

            this.objAry[x-2][y-2].setTraffic_light(this.tf_S);
            this.objAry[x-2][y-2].set_has_traffic_light(true);

            this.objAry[x+1][y-3].setTraffic_light(this.tf_N);
            this.objAry[x+1][y-3].set_has_traffic_light(true);

        }
        else if(this.rotation.equals("NS")){
            this.objAry[x][y] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+1][y] = new Road_Block(new char[]{'S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_NS.jpg");
            this.objAry[x+2][y] = new Road_Block(new char[]{'E','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_NE.jpg");
            this.objAry[x+3][y] = new Road_Block(new char[]{'W','S'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_SE.jpg");
            this.objAry[x][y-1] = new Road_Block(new char[]{'N'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x+1][y-1] = new Road_Block(new char[]{'N','E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_NS.jpg");
            this.objAry[x+2][y-1] = new Road_Block(new char[]{'W','E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WN.jpg");
            this.objAry[x+3][y-1] = new Road_Block(new char[]{'S','W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/center_road_WS.jpg");
            this.objAry[x+2][y+1] = new Road_Block(new char[]{'S','E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x+3][y+1] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x+2][y+2] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x+3][y+2] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x+2][y-2] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");
            this.objAry[x+3][y-2] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x+3][y-3] = new Road_Block(new char[]{'W'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_R_WE.jpg");
            this.objAry[x+2][y-3] = new Road_Block(new char[]{'E'},"/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/road_L_WE.jpg");

            tf_W = new TrafficLight_Obj(x+4,y+1, new char[]{'W'});
            tf_E = new TrafficLight_Obj(x+1,y-2, new char[]{'E'});
            tf_S = new TrafficLight_Obj(x+1,y+1, new char[]{'S'});
            tf_ary = new TrafficLight_Obj[3];
            tf_ary[0] = tf_W; tf_ary[1] = tf_E; tf_ary[2] = tf_S;
            tf_S.set_is_red();

            this.objAry[x+4][y+1] = this.tf_W;
            this.objAry[x+1][y-2] = this.tf_E;
            this.objAry[x+1][y+1] = this.tf_S;

            this.objAry[x+4][y+1].setTraffic_light(this.tf_W);
            this.objAry[x+4][y+1].set_has_traffic_light(true);

            this.objAry[x+4][y+1].setTraffic_light(this.tf_E);
            this.objAry[x+4][y+1].set_has_traffic_light(true);

            this.objAry[x+1][y+1].setTraffic_light(this.tf_S);
            this.objAry[x+1][y+1].set_has_traffic_light(true);
        }
    }
}

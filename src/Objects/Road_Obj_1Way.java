package Objects;

public class Road_Obj_1Way {
    private int ROW, COL,x, y;
    private char rotation;
    private Simulator_Obj[][] objAry;

    public Road_Obj_1Way(int row, int col,int X , int Y, char rot) {
        this.x = X;
        this.y = Y;
        this.rotation = rot;
        this.ROW = row;
        this.COL = col;
        objAry = new Simulator_Obj[ROW][COL];
        this.build_road_structure();
    }
    public Simulator_Obj[][] get_objAry(){
        return this.objAry;
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
    private void build_road_structure(){
        this.build_ary_grass();
        if(this.rotation == 'H'){
            this.objAry[x][y] = new Road_Block(new char[]{'E'});
            this.objAry[x][y+1] = new Road_Block(new char[]{'E'});
            this.objAry[x][y+2] = new Road_Block(new char[]{'E'});
            this.objAry[x][y+3] = new Road_Block(new char[]{'E'});
            this.objAry[x][y+4] = new Road_Block(new char[]{'E'});
            this.objAry[x][y+5] = new Road_Block(new char[]{'E'});
            this.objAry[x+1][y] = new Road_Block(new char[]{'W'});
            this.objAry[x+1][y+1] = new Road_Block(new char[]{'W'});
            this.objAry[x+1][y+2] = new Road_Block(new char[]{'W'});
            this.objAry[x+1][y+3] = new Road_Block(new char[]{'W'});
            this.objAry[x+1][y+4] = new Road_Block(new char[]{'W'});
            this.objAry[x+1][y+5] = new Road_Block(new char[]{'W'});
        }
        else{
            this.objAry[x][y] = new Road_Block(new char[]{'N'});
            this.objAry[x+1][y] = new Road_Block(new char[]{'N'});
            this.objAry[x+2][y] = new Road_Block(new char[]{'N'});
            this.objAry[x+3][y] = new Road_Block(new char[]{'N'});
            this.objAry[x+4][y] = new Road_Block(new char[]{'N'});
            this.objAry[x+5][y] = new Road_Block(new char[]{'N'});
            this.objAry[x][y+1] = new Road_Block(new char[]{'S'});
            this.objAry[x+1][y+1] = new Road_Block(new char[]{'S'});
            this.objAry[x+2][y+1] = new Road_Block(new char[]{'S'});
            this.objAry[x+3][y+1] = new Road_Block(new char[]{'S'});
            this.objAry[x+4][y+1] = new Road_Block(new char[]{'S'});
            this.objAry[x+5][y+1] = new Road_Block(new char[]{'S'});
        }
    }
}

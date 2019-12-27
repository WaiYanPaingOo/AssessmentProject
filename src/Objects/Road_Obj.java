package Objects;

public class Road_Obj {
    private int width;
    private Road_Block[] r_obj_ary;
    private char direction;
    private TrafficLight_Obj obj;

    public Road_Obj(int width, char direction) {
        this.width = width;
        this.direction = direction;
        this.r_obj_ary = new Road_Block[width];
        this.build_ary();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Road_Block[] getR_obj_ary() {
        return r_obj_ary;
    }

    public void setR_obj_ary(Road_Block[] r_obj_ary) {
        this.r_obj_ary = r_obj_ary;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Road_Block getR_obj_ary(int index)
    {
        return this.r_obj_ary[index];
    }

    public void build_ary(){
        for(int i=0; i<r_obj_ary.length; i++){
            r_obj_ary[i] = new Road_Block();
            r_obj_ary[i].setDirection(this.direction);
        }
    }
    public void addTrafficLight(TrafficLight_Obj obj)
    {
        this.obj = obj;
        int pos = this.r_obj_ary.length - 1;
        r_obj_ary[pos].set_has_traffic_light(true);
        r_obj_ary[pos].setTraffic_light(this.obj);
    }
}

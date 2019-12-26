package Objects;

public class Road_Obj {
    protected int width;
    protected Road_Block[] r_obj_ary;
    protected char direction;

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

    public Road_Block getV_obj_ary(int index)
    {
        return this.r_obj_ary[index];
    }

    public void build_ary(){
        for(int i=0; i<r_obj_ary.length; i++){
            r_obj_ary[i] = new Road_Block();
            r_obj_ary[i].setDirection(this.direction);
        }
    }
}

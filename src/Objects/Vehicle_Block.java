package Objects;

public class Vehicle_Block extends Simulator_Obj{
    private int x_location;
    private int y_location;
    private boolean just_rotate = false;
    public Vehicle_Block(int x_location, int y_location, char[] direction, String dir){
        super("Vehicle Block", dir,
                false, null, direction, false, null);
        this.x_location = x_location;
        this.y_location = y_location;
    }

    public int getX_location() {
        return x_location;
    }

    public void setX_location(int x_location) {
        this.x_location = x_location;
    }

    public int getY_location() {
        return y_location;
    }

    public void setY_location(int y_location) {
        this.y_location = y_location;
    }

    public boolean isJust_rotate() {
        return just_rotate;
    }

    public void setJust_rotate(boolean just_rotate) {
        this.just_rotate = just_rotate;
    }

    public void drive()
    {
        if(this.getDirection()[0] == 'E')
        {
            this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_T.png");
            this.y_location++;
        }
        else if(this.getDirection()[0] == 'W')
        {
            this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_W.png");
            this.y_location--;
        }
        else if(this.getDirection()[0] == 'N')
        {
            this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_N.png");
            this.x_location--;
        }
        else if(this.getDirection()[0] == 'S')
        {
            this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_S.png");
            this.x_location++;
        }
    }
}

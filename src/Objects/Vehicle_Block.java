package Objects;

public class Vehicle_Block extends Simulator_Obj{
    private int x_location;
    private int y_location;
    public Vehicle_Block(int x_location, int y_location){
        super("Vehicle Block", "/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car.png",
                false, null, 'E', false, null);
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

    public void drive()
    {
        if(this.getDirection() == 'E')
        {
            this.y_location++;
        }
        else if(this.getDirection() == 'W')
        {
            this.y_location--;
        }
        else if(this.getDirection() == 'N')
        {
            this.x_location++;
        }
        else if(this.getDirection() == 'S')
        {
            this.y_location--;
        }
    }
}

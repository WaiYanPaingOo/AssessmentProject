package Objects;

import java.util.Random;

public class Vehicle_Block extends Simulator_Obj{
    private int x_location;
    private int y_location;
    private String type;
    private boolean just_rotate;
    public Vehicle_Block(int x_location, int y_location, char[] direction, String dir){
        super("Vehicle Block", dir,
                false, null, direction, false, null);
        this.just_rotate = false;
        Random r = new Random();
        int x = r.nextInt(4);
        if(x == 1){
            this.type = "Bic";
        }
        else if(x == 2){
            this.type = "Bus";
        }
        else{
            this.type = "Car";
        }
        this.x_location = x_location;
        this.y_location = y_location;
    }

    public int getX_location() {
        return x_location;
    }

    public int getY_location() {
        return y_location;
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
            if(this.type.equals("Car")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_T.png");
            }
            else if(this.type.equals("Bus")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bus_E.png");
            }
            else if(this.type.equals("Bic")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bik_E.png");
            }
            this.y_location++;
        }
        else if(this.getDirection()[0] == 'W')
        {
            if(this.type.equals("Car")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_W.png");
            }
            else if(this.type.equals("Bus")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bus_W.png");
            }
            else if(this.type.equals("Bic")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bik_W.png");
            }
            this.y_location--;
        }
        else if(this.getDirection()[0] == 'N')
        {
            if(this.type.equals("Car")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_N.png");
            }
            else if(this.type.equals("Bus")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bus_N.png");
            }
            else if(this.type.equals("Bic")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bik_N.png");
            }
            this.x_location--;
        }
        else if(this.getDirection()[0] == 'S')
        {
            if(this.type.equals("Car")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/car_S.png");
            }
            else if(this.type.equals("Bus")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bus_S.png");
            }
            else if(this.type.equals("Bic")){
                this.setPic_location("/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/bik_S.png");
            }
            this.x_location++;
        }
    }
}

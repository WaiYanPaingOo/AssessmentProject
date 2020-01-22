package Objects;

public class Road_Block extends Simulator_Obj{

    public Road_Block(char[] direction, String pic_location){
        super("Road Block", pic_location,
                true, null, direction, false, null);
    }
}

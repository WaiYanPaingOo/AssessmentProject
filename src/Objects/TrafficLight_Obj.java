package Objects;

public class TrafficLight_Obj extends Simulator_Obj{
    protected boolean is_red;

    public TrafficLight_Obj() {
        super("Traffic Light Block", "/Users/waiyanpaingoo/Desktop/Second Sem/Java/Assessment/src/Photo/TrafficLight.png",
                false, null, 'n', false, null);
    }

    public boolean get_is_red() {
        return is_red;
    }

    public void set_is_red(boolean status) {
        this.is_red = status;
    }
}

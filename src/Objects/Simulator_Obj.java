package Objects;

public class Simulator_Obj {
    private String type, pic_location;
    private boolean drivable;
    private Simulator_Obj under;
    private char[] direction;
    private boolean has_traffic_light;
    private TrafficLight_Obj traffic_light;

    public Simulator_Obj(String type, String pic_location, boolean drivable, Simulator_Obj under, char[] direction,
                         boolean has_traffic_light, TrafficLight_Obj traffic_light) {
        this.type = type;
        this.pic_location = pic_location;
        this.drivable = drivable;
        this.under = under;
        this.direction = direction;
        this.has_traffic_light = has_traffic_light;
        this.traffic_light = traffic_light;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic_location() {
        return pic_location;
    }

    public void setPic_location(String pic_location) {
        this.pic_location = pic_location;
    }

    public boolean isDrivable() {
        return drivable;
    }

    public void setDrivable(boolean drivable) {
        this.drivable = drivable;
    }

    public Simulator_Obj getUnder() {
        return under;
    }

    public void setUnder(Simulator_Obj under) {
        this.under = under;
    }

    public char[] getDirection() {
        return direction;
    }

    public void setDirection(char[] direction) {
        this.direction = direction;
    }

    public boolean get_has_traffic_light() {
        return has_traffic_light;
    }

    public void set_has_traffic_light(boolean has_traffic_light) {
        this.has_traffic_light = has_traffic_light;
    }

    public TrafficLight_Obj getTraffic_light() {
        return traffic_light;
    }

    public void setTraffic_light(TrafficLight_Obj traffic_light) {
        this.traffic_light = traffic_light;
    }
}

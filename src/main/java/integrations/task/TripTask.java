package integrations.task;

public class TripTask {
    protected String departure;
    protected String destination;
    protected String stop;
    protected String[] vehicles;
    protected String[] facilities;
    protected Boolean wheelchair_accessible;

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(String[] vehicles) {
        this.vehicles = vehicles;
    }

    public String[] getFacilities() {
        return facilities;
    }

    public void setFacilities(String[] facilities) {
        this.facilities = facilities;
    }

    public Boolean getWheelchair_accessible() {
        return wheelchair_accessible;
    }

    public void setWheelchair_accessible(Boolean wheelchair_accessible) {
        this.wheelchair_accessible = wheelchair_accessible;
    }

}

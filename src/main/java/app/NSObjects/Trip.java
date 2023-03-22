package app.NSObjects;

import java.util.List;

public class Trip {
    private final String uid;
    private final int plannedDurationInMinutes;
    private final int transfers;
    private final String status;
    private final List<Leg> legs;
    private final String crowdForecast;
    private final boolean optimal;

    public Trip(String uid, int plannedDurationInMinutes, int transfers, String status, List<Leg> legs, String crowdForecast, boolean optimal) {
        this.uid = uid;
        this.plannedDurationInMinutes = plannedDurationInMinutes;
        this.status = status;
        this.legs = legs;
        this.transfers = transfers;
        this.crowdForecast = crowdForecast;
        this.optimal = optimal;
    }

    @Override
    public String toString() {
        return "\nTrip{" +
                "\nuid='" + uid + '\'' +
                ", \nplannedDurationInMinutes=" + plannedDurationInMinutes +
                ", \ntransfers=" + transfers +
                ", \nstatus='" + status + '\'' +
                ", \nlegs=" + legs +
                ", \ncrowdForecast='" + crowdForecast + '\'' +
                ", \noptimal=" + optimal +
                "\n}";
    }
}

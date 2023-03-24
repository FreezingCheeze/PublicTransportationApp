package app.NSObjects;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Trip {
    @Getter @Setter
    private final String uid;
    @Getter @Setter
    private final int plannedDurationInMinutes;
    @Getter @Setter
    private final int transfers;
    @Getter @Setter
    private final String status;
    @Getter @Setter
    private final List<Leg> legs;
    @Getter @Setter
    private final String crowdForecast;
    @Getter @Setter
    private final boolean optimal;

    @Getter @Setter
    private Double fare;


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

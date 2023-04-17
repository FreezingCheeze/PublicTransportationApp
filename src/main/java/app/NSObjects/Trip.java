package app.NSObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private final int idx;

    @Getter @Setter
    private Double fare;


    public Trip(int idx,String uid, int plannedDurationInMinutes, int transfers, String status, List<Leg> legs, String crowdForecast, boolean optimal) {
        this.idx = idx;
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

    public String getArrivalTime(){
        return legs.get(legs.size()-1).getArrivalTime();
    }

    public String getDepTime() {
        return legs.get(0).getDepTime();
    }

    public String getVia(){

        String via = "";
        if (legs.size()==1){
           return via;
        }
        else{
            for(int i=0;i<legs.size()-1;i++){
                Leg l = legs.get(i);
                via = via + l.getDestination() + ", ";

            }
            return via;
        }
    }

    public String getOrigin(){
        return legs.get(0).getOrigin();
    }

    public String getDestination(){
        return legs.get(legs.size()-1).getDestination();
    }
}

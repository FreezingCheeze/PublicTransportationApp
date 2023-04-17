package app;

import app.NSObjects.Trip;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class TripService {
    private List<Trip> TripList = new ArrayList<>();


    public void addTrip(Trip trip){
        TripList.add(trip);

    }

    public Trip findOptimalTrip(){
        //Return optimal Trip from the list of trips
        return TripList.stream().filter(item -> item.isOptimal()).findFirst().get();
    }

    public void clearTrip(){
        TripList = new ArrayList<>();
    }

    public Trip getTripbyidx(int idx){
        return TripList.stream().filter(item -> item.getIdx() == idx).findFirst().orElse(null);
    }


}

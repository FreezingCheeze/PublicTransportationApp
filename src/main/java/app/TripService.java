package app;
import app.NSObjects.Trip;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


}

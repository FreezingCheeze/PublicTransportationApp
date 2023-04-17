package app.controller;

import app.NSAPI;
import app.NSObjects.Trip;
import app.NSObjects.TripExp;
import app.TripRepo;
import app.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.EncodeException;
import java.io.IOException;


@Controller


public class IndexController{
    @Autowired private NSAPI nsapi;
    @Autowired private TripService ts;
    @Autowired private TripRepo tr;

    @GetMapping("/")
    public String showInput(Model model) {
        return "echo-form";
    }


    @RequestMapping(value="/submit", method = RequestMethod.POST)
    public String processForm(Model model, @ModelAttribute(value="departure") String dep,@ModelAttribute(value="destination") String dest, @ModelAttribute(value="via") String via) throws IOException, EncodeException{
        if(via.length()>0){
            nsapi.setViaStation(via);
        }
        else{
            nsapi.setViaStation(null);
        }
        nsapi.setDeparture(dep);
        nsapi.setDestination(dest);
        ts.clearTrip();
        nsapi.getTrip();
        model.addAttribute("trip",ts.getTripList());
        return "result";
    }

    @PostMapping("/addTrip")
    public ResponseEntity<?> addTrip(@RequestBody @Valid Trip trip){
        ts.addTrip(trip);
        System.out.println("Success");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saveTrip/{id}")
    public String saveTrip(@PathVariable int id,Model model){
        Trip t = ts.getTripbyidx(id);
        String uid = t.getUid();
        String origin = t.getOrigin();
        String dest = t.getDestination();
        String via = t.getVia();
        String arr_time = t.getArrivalTime();
        String dep_time = t.getDepTime();
        tr.save(new TripExp(uid,origin,dest,via,arr_time,dep_time));
        System.out.println("Saved Trip");
        model.addAttribute("trip",ts.getTripList());
        return "result";
    }

    @GetMapping("/savedTrips")
    public String getSavedTrips(Model model){
        model.addAttribute("ST",tr.findAll());
        return "save-result";
    }

}
package app.controller;
import app.NSObjects.Trip;
import app.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import app.NSAPI;

import javax.validation.Valid;
import javax.websocket.EncodeException;
import java.io.IOException;


@Controller


public class IndexController{
    @Autowired private NSAPI nsapi;
    @Autowired private TripService ts;

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

}
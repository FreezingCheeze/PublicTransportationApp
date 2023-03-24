package app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import app.NSAPI;

import javax.websocket.EncodeException;
import java.io.IOException;



@Controller
public class IndexController{
    @Autowired private NSAPI nsapi;

    @GetMapping("/")
    public String showInput(Model model) {
        return "echo-form";
    }

    @RequestMapping(value = "/submit", method= RequestMethod.POST)
    public String processForm(Model model, @ModelAttribute(value="departure") String dep,@ModelAttribute(value="destination") String dest) throws IOException, EncodeException {
        nsapi.setDeparture(dep);
        nsapi.setDestination(dest);
        model.addAttribute("trip",nsapi.getTrip());

        return "result";
    }

}
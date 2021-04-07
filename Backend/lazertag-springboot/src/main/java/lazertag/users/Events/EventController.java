package lazertag.users.Events;

import lazertag.users.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lazertag.users.Team.TeamRepository;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TeamRepository teamRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/events")
    List<Event> getAllEvents(){ return eventRepository.findAll(); }

    @GetMapping(path = "/events/{id}")
    Event getEventById(@PathVariable int id){
        return eventRepository.findById(id);
    }

    @GetMapping(path = "/eventsn/{name}")
    Event getEventById(@PathVariable String name){
        return eventRepository.findByName(name);
    }

    @PostMapping(path = "/events")
    String createUser(@RequestBody Event event){
        if (event == null)
            return failure;
        eventRepository.save(event);
        return success;
    }

    @PutMapping("/events/{id}")
    Event updateEvent(@PathVariable int id, @RequestBody Event request){
        Event event = eventRepository.findById(id);
        if(event == null)
            return null;
        if(request.getName() != null && request.getName() != "")
            event.changeName(request.getName());
        if(request.getLocation() != null && request.getLocation() != "")
            event.changeLocation(request.getLocation());
        if(request.getTime() != null && request.getTime() != "")
            event.changeTime(request.getTime());
        if(request.getOrganizer() != null)
            event.changeOrganizer(request.getOrganizer());
        eventRepository.save(event);
        return eventRepository.findById(id);
    }
}

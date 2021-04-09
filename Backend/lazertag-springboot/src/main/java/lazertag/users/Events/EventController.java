package lazertag.users.Events;

import io.swagger.annotations.Api;
import lazertag.users.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lazertag.users.Team.TeamRepository;

import java.util.List;

@Api(value = "EventController", description = "REST APIs related to Event Entity")
@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //returns all events in database
    @GetMapping(path = "/events")
    List<Event> getAllEvents(){ return eventRepository.findAll(); }

    //returns a event by id numbers
    @GetMapping(path = "/events/{id}")
    Event getEventById(@PathVariable int id){
        return eventRepository.findById(id);
    }

    //returns events that match given name
    @GetMapping(path = "/eventsn/{name}")
    Event getEventById(@PathVariable String name){
        return eventRepository.findByName(name);
    }

    //creates a new event
    @PostMapping(path = "/events")
    String createUser(@RequestBody Event event){
        if (event == null)
            return failure;
        eventRepository.save(event);
        return success;
    }

    //updates an event with given event id
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
        if(request.getOrganizer() != 0)
            event.changeOrganizer(request.getOrganizer());
        if(request.getGameDescription() !=null && request.getGameDescription() != "")
            event.changeGameDescription(request.getGameDescription());
        if(request.getTeam1() != null && request.getTeam1() != "")
            event.changeTeam1(request.getTeam1());
        if(request.getTeam2() != null && request.getTeam2() != "")
            event.changeTeam2(request.getTeam2());
        eventRepository.save(event);
        return eventRepository.findById(id);
    }

    //updates an event based on name
    @PutMapping("/events/{name}")
    Event updateEventN(@PathVariable String name, @RequestBody Event request){
        Event event = eventRepository.findByName(name);
        if(event == null)
            return null;
        if(request.getName() != null && request.getName() != "")
            event.changeName(request.getName());
        if(request.getLocation() != null && request.getLocation() != "")
            event.changeLocation(request.getLocation());
        if(request.getTime() != null && request.getTime() != "")
            event.changeTime(request.getTime());
        if(request.getOrganizer() != 0)
            event.changeOrganizer(request.getOrganizer());
        if(request.getGameDescription() !=null && request.getGameDescription() != "")
            event.changeGameDescription(request.getGameDescription());
        if(request.getTeam1() != null && request.getTeam1() != "")
            event.changeTeam1(request.getTeam1());
        if(request.getTeam2() != null && request.getTeam2() != "")
            event.changeTeam2(request.getTeam2());
        eventRepository.save(event);
        return eventRepository.findByName(name);
    }

    //delete an event with a given id
    @DeleteMapping(path = "/events/{id}")
    String deleteEvent(@PathVariable int id){
        eventRepository.deleteById(id);
        return success;
    }
}

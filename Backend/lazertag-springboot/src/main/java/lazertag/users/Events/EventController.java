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
        if(request.getOrganizer() != 0)
            event.changeOrganizer(request.getOrganizer());
        if(request.getGameDescription() !=null && request.getGameDescription() != "")
            event.changeGameDescription(request.getGameDescription());
        if(request.getTeam1Id() != 0)
            event.changeTeam1(request.getTeam1Id());
        if(request.getTeam2Id() != 0)
            event.changeTeam2(request.getTeam2Id());
        eventRepository.save(event);
        return eventRepository.findById(id);
    }

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
        if(request.getTeam1Id() != 0)
            event.changeTeam1(request.getTeam1Id());
        if(request.getTeam2Id() != 0)
            event.changeTeam2(request.getTeam2Id());
        eventRepository.save(event);
        return eventRepository.findByName(name);
    }

    @DeleteMapping(path = "/events/{id}")
    String deleteEvent(@PathVariable int id){
        eventRepository.deleteById(id);
        return success;
    }
}

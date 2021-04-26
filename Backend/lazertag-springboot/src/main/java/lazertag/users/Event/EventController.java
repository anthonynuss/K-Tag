package lazertag.users.Event;

import io.swagger.annotations.Api;
import lazertag.users.Team.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Api(value = "EventController", description = "REST APIs related to Event Entity")
@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;
    
    @Autowired
    TeamRepository teamRepository;

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
    Event getEventByName(@PathVariable String name){
        return eventRepository.findByName(name);
    }
    
    @GetMapping(path = "/eventst/{teamid}")
    List<Event> getEventsByTeam(@PathVariable int teamid){
    	List<Event> list1 = eventRepository.findByTeam1(teamRepository.findById(teamid));
    	List<Event> list2 = eventRepository.findByTeam2(teamRepository.findById(teamid));
    	List<Event> newList = new ArrayList<>();
    	Stream.of(list1, list2).forEach(newList::addAll);
        return newList;
    }
    
    @GetMapping(path = "/eventstw/{teamid}")
    List<Event> getEventsByWinner(@PathVariable int teamid){
        return eventRepository.findByWinner(teamid);
    }

    //creates a new event
    @PostMapping(path = "/events")
    String createEvent(@RequestBody Event event){
        if (event == null)
            return failure;
        eventRepository.save(event);
        return success;
    }

    @PutMapping("/eventsw/{id}/{teamid}")
    String setWinner(@PathVariable int id,@PathVariable int teamid, @RequestBody Event request){
        Event event = eventRepository.findById(id);
        if(event.setWinner(teamid).equals("failure")) {
        	return failure;
        }
        eventRepository.save(event);
        return success+": "+teamRepository.findById(teamid).getName()+" won "+event.getName();
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
        if(request.getTeam1() != null && request.getTeam1() != null)
            event.changeTeam1(request.getTeam1());
        if(request.getTeam2() != null && request.getTeam2() != null)
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
        if(request.getTeam1() != null)
            event.changeTeam1(request.getTeam1());
        if(request.getTeam2() != null)
            event.changeTeam2(request.getTeam2());
        eventRepository.save(event);
        return eventRepository.findByName(name);
    }
    
    @PutMapping("/eventstadd1/{id}/{teamid}")
    String setTeam1(@PathVariable int id,@PathVariable int teamid, @RequestBody Event request){
        Event event = eventRepository.findById(id);
        event.changeTeam1(teamRepository.findById(teamid));
        eventRepository.save(event);
        return success+": "+teamRepository.findById(teamid).getName()+" added to "+event.getName();
    }
    
    @PutMapping("/eventstadd2/{id}/{teamid}")
    String setTeam2(@PathVariable int id,@PathVariable int teamid, @RequestBody Event request){
        Event event = eventRepository.findById(id);
        event.changeTeam2(teamRepository.findById(teamid));
        eventRepository.save(event);
        return success+": "+teamRepository.findById(teamid).getName()+" added to "+event.getName();
    }

    //delete an event with a given id
    @DeleteMapping(path = "/events/{id}")
    String deleteEvent(@PathVariable int id){
        eventRepository.deleteById(id);
        return success;
    }
}

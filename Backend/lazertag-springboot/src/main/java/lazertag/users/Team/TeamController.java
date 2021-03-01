package lazertag.users.Team;

import lazertag.users.User.User;
import lazertag.users.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class TeamController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/teams")
    List<Team> getAllLaptops(){
        return teamRepository.findAll();
    }

    @GetMapping(path = "/teams/{id}")
    Team getTeamById(@PathVariable int id){
        return teamRepository.findById(id);
    }

    @PostMapping(path = "/teams")
    String createTeam(@RequestBody Team Team){
        if (Team == null)
            return failure;
        teamRepository.save(Team);
        return success;
    }

    @PutMapping(path = "/teams/{id}")
    Team updateTeam(@PathVariable int id, @RequestBody Team request){
        Team team = teamRepository.findById(id);
        if(team == null)
            return null;
        teamRepository.save(request);
        return teamRepository.findById(id);
    }

    @PutMapping("/teams/{teamId}/user/{userId}")
    String assignUserToTeam(@PathVariable int userId,@PathVariable int teamId){
        User user = userRepository.findById(userId);
        Team team = teamRepository.findById(teamId);
        if(user == null || team == null)
            return failure;
        team.addUser(user);
        user.setTeam(team);
        userRepository.save(user);
        return success;
    }

    @DeleteMapping(path = "/teams/{id}")
    String deleteTeam(@PathVariable int id){
        teamRepository.deleteById(id);
        return success;
    }
}

package lazertag.users.User;

import java.util.List;

import lazertag.users.Team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(user == null)
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }   

    /* add
    @PutMapping("/users/{userId}/team/{teamId}")
    String assignTeamToUser(@PathVariable int userId,@PathVariable int teamId){
        User user = userRepository.findById(userId);
        Team team = teamRepository.findById(teamId);
        if(user == null || team == null)
            return failure;
        team.addUser(user);
        user.setTeam(team);
        userRepository.save(user);
        return success;
    }*/

    /*
    @DeleteMapping(path = "/users/{id}")
    String deleteTeam(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }*/
}

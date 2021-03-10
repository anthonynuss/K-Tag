package lazertag.users.User;

import java.util.List;

//import lazertag.users.Team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    //team things
    //@Autowired
    //TeamRepository teamRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable int id){
        return userRepository.findById(id);
    }
    
    @GetMapping("/user/{usern}")
    User getUserbyName(@PathVariable String usern, @RequestBody User request){
    	if(userRepository.count()==0) {
    		User temp = null;
    		return temp;
    	}
    	User user = userRepository.findById(1);
    	int id = 0;
    	for(int i=1;i<userRepository.count()+1;i++) {
    		user = userRepository.findById(i);
    		if(user.getName().equals(usern)) {
    			id = i;
    			break;
    		}
    	}
    	if(id==0) {
    		if (request == null)
                return null;
            return null;
    	}
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
        if(request.getName() != null && request.getName() != "") 
        	user.setName(request.getName());
        if(request.getPassword() != null && request.getPassword() != "") 
        	user.setPassword(request.getPassword());
        if(request.getLongitude() != 0.0) 
        	user.setLongitude(request.getLongitude());
        if(request.getLatitude() != 0.0) 
        	user.setLatitude(request.getLatitude());
        userRepository.save(user);
        return userRepository.findById(id);
    }   
    
    @PutMapping("/user/{usern}")
    User updateOrCreateUserName(@PathVariable String usern, @RequestBody User request){
    	if(userRepository.count()==0) {
    		if (request == null)
                return null;
            userRepository.save(request);
            return request;
    	}
    	User user = userRepository.findById(1);
    	int id = 0;
    	for(int i=1;i<userRepository.count();i++) {
    		user = userRepository.findById(i);
    		if(user.getName().equals(usern)) {
    			id = i;
    			break;
    		}
    	}
    	if(id==0) {
    		if (request == null)
                return null;
            userRepository.save(request);
            return request;
    	}
    	else {
    		if(request.getName() != null && request.getName() != "") 
            	user.setName(request.getName());
            if(request.getPassword() != null && request.getPassword() != "") 
            	user.setPassword(request.getPassword());
            if(request.getLongitude() != 0.0) 
            	user.setLongitude(request.getLongitude());
            if(request.getLatitude() != 0.0) 
            	user.setLatitude(request.getLatitude());
            userRepository.save(user);
            return userRepository.findById(id);
    	}
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

    
    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }
}

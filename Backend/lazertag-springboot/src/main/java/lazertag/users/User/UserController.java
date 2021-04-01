package lazertag.users.User;

import java.util.ArrayList;
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
import io.swagger.annotations.*;

@Api(value = "UserController", description = "REST APIs related to User Entity")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get list of users in the System ", response = User.class, tags = "getUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),@ApiResponse(code = 401, message = "not authorized"),@ApiResponse(code = 403, message = "forbidden"),@ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "Get a user in the system with an ID ", response = User.class, tags = "getUserID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable int id){
        return userRepository.findById(id);
    }
    
    @ApiOperation(value = "Get list of users in the System ", response = User.class, tags = "getUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @GetMapping("/user/{usern}")
    User getUserbyName(@PathVariable String name){
    	return userRepository.findByName(name);
    }   
    
    @ApiOperation(value = "Create a user in the system ", response = User.class, tags = "createUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @ApiOperation(value = "Updates user coordinates data with given user ID", response = User.class, tags = "updateUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
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
    
    @ApiOperation(value = "Updates user coordinates data with given username", response = User.class, tags = "updateUser")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @PutMapping("/user/{name}")
    User updateName(@PathVariable String name, @RequestBody User request){
    	User user = userRepository.findByName(name);
    	if(user == null) {
    		return null;
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
            return user;
    	}
    }   

    @ApiOperation(value = "Deletes a user with a given user ID", response = User.class, tags = "updateUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }
    
    /*
    @ApiOperation(value = "Get this user's friendslist", response = User.class, tags = "getFriends")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/friends/{id}")
    ArrayList<User> getFriendsOfId(@PathVariable int id){
        return userRepository.findById(id).getFriends();
    }
    
    @ApiOperation(value = "Adds a friend to the user's friendslist with given ID", response = User.class, tags = "addFriend")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @PutMapping(path = "/friends/{id}/{id2}")
    String addFriendOfId(@PathVariable int id, @PathVariable int id2){
    	if(userRepository.findById(id)==null) {
    		return "User id "+id+" not found";
    	}
    	else if(userRepository.findById(id2)==null) {
    		return "User id "+id2+" not found";
    	}
    	userRepository.findById(id).addFriend(userRepository.findById(id2));
    	userRepository.findById(id2).addFriend(userRepository.findById(id));
        return success;
    }
    
    @ApiOperation(value = "removes a friend from both user's friendslists with given IDs", response = User.class, tags = "addFriend")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @DeleteMapping(path = "/friends/{id}/{id2}")
    String removeFriendOfId(@PathVariable int id, @PathVariable int id2){
    	if(userRepository.findById(id)==null) {
    		return "User id "+id+" not found";
    	}
    	else if(userRepository.findById(id2)==null) {
    		return "User id "+id2+" not found";
    	}
    	userRepository.findById(id).removeFriend(userRepository.findById(id2));
    	userRepository.findById(id2).removeFriend(userRepository.findById(id));
        return success;
    }*/
}

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
import io.swagger.annotations.*;

@Api(value = "UserController", description = "REST APIs related to User Entity")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    //team things
    //@Autowired
    //TeamRepository teamRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /*
     * GET:
     * returns the data of all users in the database
     */
    @ApiOperation(value = "Get list of users in the System ", response = Iterable.class, tags = "getUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /*
     * GET:
     * Returns the data of the user with the given id
     */
    @ApiOperation(value = "Get a user in the system with an ID ", response = Iterable.class, tags = "getUserID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable int id){
        return userRepository.findById(id);
    }
    
    /*
     * GET:
     * Searches through the database for the given username and returns the user data of said username.
     */
    @ApiOperation(value = "Get list of users in the System ", response = Iterable.class, tags = "getUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
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
    
    /*
     * POST:
     * Creates a user based on the JSON object given
     */
    @ApiOperation(value = "Create a user in the system ", response = Iterable.class, tags = "createUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    /*
     * PUT:
     * Updates the non-null, non-0 data in the user with the given id
     */
    @ApiOperation(value = "Updates user coordinates data with given user ID", response = Iterable.class, tags = "updateUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
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
    
    /*
     * PUT:
     * Updates the non-null, non-0 data in the user with the given username. If a user with said username does not exist,
     * it will create a new user with the given data
     */
    @ApiOperation(value = "Updates user coordinates data with given username", response = Iterable.class, tags = "updateUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @PutMapping("/user/{usern}")
    User updateOrCreateUserName(@PathVariable String usern, @RequestBody User request){
    	if(userRepository.count()==0) {
    		if (request == null)
                return null;
            userRepository.save(request);
            return request;
    	}
    	/*
    	 * finds first user in database with given name
    	 */
    	User user = userRepository.findById(1);
    	int id = 0;
    	for(int i=1;i<userRepository.count();i++) {
    		user = userRepository.findById(i);
    		if(user.getName().equals(usern)) {
    			id = i;
    			break;
    		}
    	}
    	/*
    	 * if user with given username is not found, a new user created if the RequestBody is not null
    	 */
    	if(id==0) {
    		if (request == null)
                return null;
            userRepository.save(request);
            return request;
    	}
    	/*
    	 * the actual PUT function that replaces non-null and non-0 values with the ones given in the RequestBody
    	 */
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
    
    /*
     * PUT:
     * This is supposed to set the user's team. Teams do not work yet. This will be implemented later
     */
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
     * DELETE:
     * deletes the user with the given id. Shouldn't be used yet
     */
    @ApiOperation(value = "Deletes a user with a given user ID", response = Iterable.class, tags = "updateUser")
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
}

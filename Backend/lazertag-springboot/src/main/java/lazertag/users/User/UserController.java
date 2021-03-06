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
import lazertag.users.Team.Team;
import lazertag.users.Team.TeamRepository;

@Api(value = "UserController", description = "REST APIs related to User Entity")
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TeamRepository teamRepository;

	private String success = "{\"message\":\"success\"}";
	private String failure = "{\"message\":\"failure\"}";

	@ApiOperation(value = "Get a list of all users in the system", response = User.class, tags = "getUsers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@GetMapping(path = "/users")
	List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@ApiOperation(value = "Get a user in the system that has the given ID", response = User.class, tags = "getUserID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@GetMapping(path = "/users/{id}")
	User getUserById(@PathVariable int id) {
		return userRepository.findById(id);
	}

	@ApiOperation(value = "Get a user in the system that has the given name", response = User.class, tags = "getUserName")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@GetMapping("/usersn/{name}")
	User getUserbyName(@PathVariable String name) {
		return userRepository.findByName(name);
	}

	@ApiOperation(value = "Gets a user's team ID given the user's ID", response = User.class, tags = "getUserTeam")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@GetMapping("/userst/{id}")
	int getUsersTeam(@PathVariable int id) {
		return userRepository.findById(id).getTeam().getId();
	}

	@ApiOperation(value = "Create a user in the system", response = User.class, tags = "createUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@PostMapping(path = "/users")
	String createUser(@RequestBody User user) {
		if (user == null)
			return failure;
		userRepository.save(user);
		return success;
	}

	@ApiOperation(value = "Updates the user with the given ID's data", response = User.class, tags = "updateUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@PutMapping("/users/{id}")
	User updateUser(@PathVariable int id, @RequestBody User request) {
		User user = userRepository.findById(id);
		if (user == null)
			return null;
		if (request.getName() != null && request.getName() != "")
			user.setName(request.getName());
		if (request.getPassword() != null && request.getPassword() != "")
			user.setPassword(request.getPassword());
		if (request.getLongitude() != 0.0)
			user.setLongitude(request.getLongitude());
		if (request.getLatitude() != 0.0)
			user.setLatitude(request.getLatitude());
		if (!(request.getWins() < 0))
			user.setWins(request.getWins());
		if (!(request.getLosses() < 0))
			user.setLosses(request.getLosses());
		if (!(request.getTags() < 0))
			user.setTags(request.getTags());
		if (!(request.getKnockouts() < 0))
			user.setKnockouts(request.getKnockouts());
		userRepository.save(user);
		return userRepository.findById(id);
	}

	@ApiOperation(value = "Updates the user with the given name's data", response = User.class, tags = "updateUserName")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@PutMapping("/usersn/{name}")
	User updateName(@PathVariable String name, @RequestBody User request) {
		User user = userRepository.findByName(name);
		if (user == null) {
			return null;
		} else {
			if (request.getName() != null && request.getName() != "")
				user.setName(request.getName());
			if (request.getPassword() != null && request.getPassword() != "")
				user.setPassword(request.getPassword());
			if (request.getLongitude() != 0.0)
				user.setLongitude(request.getLongitude());
			if (request.getLatitude() != 0.0)
				user.setLatitude(request.getLatitude());
			if (!(request.getWins() < 0))
				user.setWins(request.getWins());
			if (!(request.getLosses() < 0))
				user.setLosses(request.getLosses());
			if (!(request.getTags() < 0))
				user.setTags(request.getTags());
			if (!(request.getKnockouts() < 0))
				user.setKnockouts(request.getKnockouts());
			userRepository.save(user);
			return user;
		}
	}

	@ApiOperation(value = "Deletes a user with a given user ID", response = User.class, tags = "deleteUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
	@DeleteMapping(path = "/users/{id}")
	String deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		return success;
	}

}

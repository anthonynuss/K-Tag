package lazertag.users.Friends;

import java.util.ArrayList;
import java.util.List;

//import lazertag.users.Team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lazertag.users.User.User;
import lazertag.users.User.UserRepository;

@Api(value = "FriendsController", description = "REST APIs related to Friends Entity")
@RestController
public class FriendsController {

    @Autowired
    FriendsRepository friendsRepository;
    
    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Gets a list of all friendships in the system", response = User.class, tags = "getFriends")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/friends")
    List<Friends> getAllFriends(){
        return friendsRepository.findAll();
    }

    @ApiOperation(value = "Gets a list of all friends of a user given their ID", response = User.class, tags = "getFriendslist")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/friends/{id}")
    List<Friends> getFriendsById(@PathVariable int id){
        return friendsRepository.getByPerson(id);
    }
    
    @ApiOperation(value = "Creates a friendship between two users with the given IDs, adding each to the other's friendslist", response = User.class, tags = "createFriendship")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
    @PostMapping(path = "/friends/{id1}/{id2}")
    String createFriendship(@PathVariable int id1, @PathVariable int id2){
        Friends friends1 = new Friends(userRepository.findById(id1),userRepository.findById(id2));
        Friends friends2 = new Friends(userRepository.findById(id2),userRepository.findById(id1));
        friendsRepository.save(friends1);
        friendsRepository.save(friends2);
        return userRepository.findById(id1).getName()+" and "+userRepository.findById(id2).getName()+" are now friends.";
    }
    
    @ApiOperation(value = "Deletes a friendship between users with the given user IDs, delete each off the other's friendslist", response = User.class, tags = "deleteUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"),
			@ApiResponse(code = 404, message = "not found") })
    @DeleteMapping(path = "/friends/{id1}/{id2}")
    String deleteFriendship(@PathVariable int id1, @PathVariable int id2){
        Friends friends1 = friendsRepository.getByPersonAndFriend(id1, id2);
        Friends friends2 = friendsRepository.getByPersonAndFriend(id2, id1);
        friendsRepository.deleteById(friends1.getId());
        friendsRepository.deleteById(friends2.getId());
        return userRepository.findById(id1).getName()+" and "+userRepository.findById(id2).getName()+" are no longer friends. :(";
    }
}

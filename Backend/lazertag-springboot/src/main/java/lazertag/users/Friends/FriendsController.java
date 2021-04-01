package lazertag.users.Friends;

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
import lazertag.users.User.UserRepository;

@RestController
public class FriendsController {

    @Autowired
    FriendsRepository friendsRepository;
    
    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/friends")
    List<Friends> getAllFriends(){
        return friendsRepository.findAll();
    }

    @GetMapping(path = "/friends/{id}")
    List<Friends> getFriendsById(@PathVariable int id){
        return friendsRepository.findByPerson(id);
    }
    
    @PostMapping(path = "/friends/{id1}/{id2}")
    String createFriendship(@PathVariable int id1, @PathVariable int id2){
        Friends friends1 = new Friends(userRepository.findById(id1),userRepository.findById(id2));
        Friends friends2 = new Friends(userRepository.findById(id2),userRepository.findById(id1));
        friendsRepository.save(friends1);
        friendsRepository.save(friends2);
        return userRepository.findById(id1).getName()+" and "+userRepository.findById(id2).getName()+" are now friends.";
    }
}

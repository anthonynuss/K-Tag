package lazertag.users.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface FriendsRepository extends JpaRepository<Friends, Long> {
	Friends getByPersonAndFriend(int person, int friend);
	List<Friends> getByPerson(int person);
    void deleteById(int id);
}
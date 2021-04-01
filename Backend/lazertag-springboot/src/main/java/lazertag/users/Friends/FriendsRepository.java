package lazertag.users.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface FriendsRepository extends JpaRepository<Friends, Long> {
	List<Friends> findByPerson(int person);
    void deleteById(int id);
}
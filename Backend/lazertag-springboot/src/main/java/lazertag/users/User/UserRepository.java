package lazertag.users.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
	User findById(int id);
    void deleteById(int id);
    
}

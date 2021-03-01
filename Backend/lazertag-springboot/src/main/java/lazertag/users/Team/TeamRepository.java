package lazertag.users.Team;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findById(int id);
    void deleteById(int id);
}

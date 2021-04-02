package lazertag.users.Team;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
	Team findById(int id);
    void deleteById(int id);
}

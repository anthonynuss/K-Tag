package lazertag.users.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lazertag.users.Team.Team;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
    List<Event> findByTeam1(Team team);
    List<Event> findByTeam2(Team team);
    List<Event> findByWinner(int winner);
    Event findById(int id);
    void deleteById(int id);
}

package lazertag.websocket.gps;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long>{
	Location findByUserId(int id);
}

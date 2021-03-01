package lazertag.users.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findById(int id);
    void deleteById(int id);
}

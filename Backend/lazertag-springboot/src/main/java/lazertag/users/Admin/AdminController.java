package lazertag.users.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Vivek Bengre
 *
 */

@RestController
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/admins")
    List<Admin> getAlladmins(){
        return adminRepository.findAll();
    }

    @GetMapping(path = "/admins/{id}")
    Admin getadminById( @PathVariable int id){
        return adminRepository.findById(id);
    }

    @PostMapping(path = "/admins")
    String createadmin(@RequestBody Admin admin){
        if (admin == null)
            return failure;
        adminRepository.save(admin);
        return success;
    }

    @PutMapping("/admins/{id}")
    Admin updateadmin(@PathVariable int id, @RequestBody Admin request){
        Admin admin = adminRepository.findById(id);
        if(admin == null)
            return null;
        adminRepository.save(request);
        return adminRepository.findById(id);
    }

}

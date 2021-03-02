package lazertag.users;

import lazertag.users.Team.Team;
import lazertag.users.Team.TeamRepository;
import lazertag.users.User.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lazertag.users.User.UserRepository;

import java.util.Date;

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 users with their machines and phones
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, TeamRepository teamRepository) {
        return args -> {


            //Team team1 = new Team("The Pirates", new Date());

            User user1 = new User("John", "password", new Date());
            User user2 = new User("Jane", "password3", new Date());
            User user3 = new User("Justin", "justin432", new Date());



            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            //teamRepository.save(team1);

            /*
            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user3.setLaptop(laptop3);
            user1.addPhones(phoneRepository.findById(1));
            user1.addPhones(phoneRepository.findById(2));            
            user1.addPhones(phoneRepository.findById(6));
            user2.addPhones(phoneRepository.findById(3));
            user2.addPhones(phoneRepository.findById(4)); 
            user3.addPhones(phoneRepository.findById(5));
            user3.addPhones(phoneRepository.findById(7));
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
             */
        };
    }

}
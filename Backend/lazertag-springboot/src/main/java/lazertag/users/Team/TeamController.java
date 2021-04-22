package lazertag.users.Team;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.*;
import lazertag.users.User.User;
import lazertag.users.User.UserRepository;

@Api(value = "TeamController", description = "REST APIs related to Team Entity")
@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;
    
    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get list of all teams in the System", response = Team.class, tags = "getTeam")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"),@ApiResponse(code = 401, message = "not authorized"),@ApiResponse(code = 403, message = "forbidden"),@ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/teams")
    List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    @ApiOperation(value = "Get a team in the system with the given ID ", response = Team.class, tags = "getTeamID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @GetMapping(path = "/teams/{id}")
    Team getTeamById(@PathVariable int id){
        return teamRepository.findById(id);
    }
    
    
    @ApiOperation(value = "Gets a team with the given name", response = Team.class, tags = "getTeamName")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @GetMapping("/teamsn/{name}")
    Team getTeambyName(@PathVariable String name){
    	return teamRepository.findByName(name);
    }
    
    @ApiOperation(value = "Gets a list of teammates in the given team", response = Team.class, tags = "getTeammates")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @GetMapping("/teamsm/{id}")
    List<User> getTeammatesById(@PathVariable int id){
    	return teamRepository.findById(id).getTeammates();
    }   
    
    @ApiOperation(value = "Creates a team in the system", response = Team.class, tags = "createTeam")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @PostMapping(path = "/teams")
    String createTeam(@RequestBody Team team){
        if (team == null)
            return failure;
        teamRepository.save(team);
        return success;
    }

    @ApiOperation(value = "Updates the data of the team with the given ID", response = Team.class, tags = "updateTeam")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @PutMapping("/teams/{id}")
    Team updateTeam(@PathVariable int id, @RequestBody Team request){
        Team team = teamRepository.findById(id);
        if(team == null)
            return null;
        if(request.getName() != null && request.getName() != "") 
        	team.setName(request.getName());
        if(request.getCaptain() != 0) 
        	team.setCaptain(request.getId());
        if(!(request.getWins() < 0)) 
        	team.setWins(request.getWins());
        if(!(request.getLosses() < 0)) 
        	team.setLosses(request.getLosses());
        teamRepository.save(team);
        return teamRepository.findById(id);
    }   
    
    @ApiOperation(value = "Updates the team with the given name's data", response = Team.class, tags = "updateTeamByName")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Suceess|OK"), @ApiResponse(code = 401, message = "not authorized"), @ApiResponse(code = 403, message = "forbidden"), @ApiResponse(code = 404, message = "not found") })
    @PutMapping("/teams/{name}")
    Team updateTeamByName(@PathVariable String name, @RequestBody Team request){
    	Team team = teamRepository.findByName(name);
    	if(team == null) {
    		return null;
    	}
    	if(request.getName() != null && request.getName() != "") 
        	team.setName(request.getName());
        if(request.getCaptain() != 0) 
        	team.setCaptain(request.getId());
        if(!(request.getWins() < 0)) 
        	team.setWins(request.getWins());
        if(!(request.getLosses() < 0)) 
        	team.setLosses(request.getLosses());
        teamRepository.save(team);
        return teamRepository.findById(team.getId());
    }   

    @ApiOperation(value = "Adds a user with the given userid to a team with a given ID", response = Team.class, tags = "addUserTeam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @PutMapping("/teams/{id}/{userid}")
    Team addUserToTeam(@PathVariable int id, @PathVariable int userid){
    	Team team = teamRepository.findById(id);
    	User user = userRepository.findById(userid);
    	
    	user.setTeam(team);
    	team.addTeammate(user);
    	
    	userRepository.save(user);
        teamRepository.save(team);
        return teamRepository.findById(team.getId());
    }
    
    @ApiOperation(value = "Removes a user with the given userid from a team with a given ID", response = Team.class, tags = "removeUserTeam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @DeleteMapping("/teams/{id}/{userid}")
    Team removeUserToTeam(@PathVariable int id, @PathVariable int userid){
    	Team team = teamRepository.findById(id);
    	User user = userRepository.findById(userid);
    	
    	user.setTeam(null);
    	team.removeTeammate(user);
    	
    	userRepository.save(user);
        teamRepository.save(team);
        return teamRepository.findById(team.getId());
    }   
    
    @ApiOperation(value = "Deletes a team with a given ID", response = Team.class, tags = "removeTeam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found") })
    @DeleteMapping(path = "/teams/{id}")
    String deleteTeam(@PathVariable int id){
        teamRepository.deleteById(id);
        return success;
    }
}

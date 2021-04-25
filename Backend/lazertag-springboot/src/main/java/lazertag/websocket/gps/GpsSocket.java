package lazertag.websocket.gps;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lazertag.users.User.UserRepository;

@Controller // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/location/{userId}") // this is Websocket url
public class GpsSocket {

	private static UserRepository usrRepo;

	// cannot autowire static directly (instead we do it by the below
	// method
	private static LocationRepository locRepo;

	/*
	 * Grabs the MessageRepository singleton from the Spring Application Context.
	 * This works because of the @Controller annotation on this class and because
	 * the variable is declared as static. There are other ways to set this.
	 * However, this approach is easiest.
	 */
	@Autowired
	public void setLocationRepository(LocationRepository repo) {
		locRepo = repo; // we are setting the static variable
	}

	@Autowired
	public void setUserRepository(UserRepository repo) {
		usrRepo = repo; // we are setting the static variable
	}

	// Store all socket session and their corresponding userId.
	private static Map<Session, Integer> sessionUseridMap = new Hashtable<>();
	private static Map<Integer, Session> useridSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(GpsSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") int userId) throws IOException {

		logger.info("Entered into Open");

		// store connecting user information
		sessionUseridMap.put(session, userId);
		useridSessionMap.put(userId, session);

		// Send chat history to the newly connected user
		sendMessageToParticularUser(userId, getAllLocations());

		// broadcast that new user joined
		//broadcast(message);
	}

	@OnMessage
	public void onMessage(Session session, float longitude, float latitude) throws IOException {

		// Handle new messages
		logger.info("Entered into GPS: Got Location:" + longitude + ", " + latitude);
		int userId = sessionUseridMap.get(session);
		Location loc = locRepo.findByUserId(userId);

		// Direct message to a user using the format "@userid <message>"
		if (!(longitude == 0 && latitude == 0)) {
			broadcast(loc);
			locRepo.save(loc);
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		// remove the user connection information
		int userId = sessionUseridMap.get(session);
		sessionUseridMap.remove(session);
		useridSessionMap.remove(userId);

		// broadcase that the user disconnected
		//String message = userId + " disconnected";
		//broadcast(message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}

	private void sendMessageToParticularUser(int userId, String message) {
		try {
			useridSessionMap.get(userId).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	private void broadcast(Location location) {
		ObjectMapper mapper = new ObjectMapper();
		sessionUseridMap.forEach((session, userId) -> {
			try {
				session.getBasicRemote().sendText(mapper.writeValueAsString(location));
			} catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}

		});

	}

	// Gets the Chat history from the repository
	private String getAllLocations() {
		ObjectMapper mapper = new ObjectMapper();
		List<Location> locations = locRepo.findAll();

		// convert the list to a string
		String locString = "[";
		if (locations != null && locations.size() != 0) {
			for (Location location : locations) {
				try {
					locString = locString + mapper.writeValueAsString(location) + ",";
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		locString = locString.substring(0, locString.length() - 1)+"]";

		return locString;
	}

} // end of Class

package lazertag.websocket.chat;

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

import lazertag.users.User.UserRepository;

@Controller // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{userId}") // this is Websocket url
public class ChatSocket {

	@Autowired
	UserRepository userRepository;
	
	// cannot autowire static directly (instead we do it by the below
	// method
	private static MessageRepository msgRepo;

	/*
	 * Grabs the MessageRepository singleton from the Spring Application Context.
	 * This works because of the @Controller annotation on this class and because
	 * the variable is declared as static. There are other ways to set this.
	 * However, this approach is easiest.
	 */
	@Autowired
	public void setMessageRepository(MessageRepository repo) {
		msgRepo = repo; // we are setting the static variable
	}

	// Store all socket session and their corresponding userId.
	private static Map<Session, Integer> sessionUseridMap = new Hashtable<>();
	private static Map<Integer, Session> useridSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") int userId) throws IOException {

		logger.info("Entered into Open");

		// store connecting user information
		sessionUseridMap.put(session, userId);
		useridSessionMap.put(userId, session);

		// Send chat history to the newly connected user
		sendMessageToPArticularUser(userId, getChatHistory());

		// broadcast that new user joined
		String message = "User:" + userRepository.findById(userId).getName() + " has Joined the Chat";
		broadcast(message);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {

		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		int userId = sessionUseridMap.get(session);

		// Direct message to a user using the format "@username <message>"
		if (message.startsWith("@")) {
			String destUsername = message.split(" ")[0].substring(1);

			// send the message to the sender and receiver
			sendMessageToPArticularUser(userRepository.findByName(destUsername).getId(),"[DM] " + userRepository.findById(userId).getName() + ": " + message);
			sendMessageToPArticularUser(userId, "[DM] " + userRepository.findById(userId).getName() + ": " + message);

		} else { // broadcast
			broadcast(userId + ": " + message);
		}

		// Saving chat history to repository
		msgRepo.save(new Message(userId, message));
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

		// remove the user connection information
		int userId = sessionUseridMap.get(session);
		sessionUseridMap.remove(session);
		useridSessionMap.remove(userId);

		// broadcase that the user disconnected
		String message = userId + " disconnected";
		broadcast(message);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}

	private void sendMessageToPArticularUser(int userId, String message) {
		try {
			useridSessionMap.get(userId).getBasicRemote().sendText(message);
		} catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}

	private void broadcast(String message) {
		sessionUseridMap.forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}

		});

	}

	// Gets the Chat history from the repository
	private String getChatHistory() {
		List<Message> messages = msgRepo.findAll();

		// convert the list to a string
		StringBuilder sb = new StringBuilder();
		if (messages != null && messages.size() != 0) {
			for (Message message : messages) {
				sb.append(userRepository.findById(message.getUserId()).getName() + ": " + message.getContent() + "\n");
			}
		}
		return sb.toString();
	}

} // end of Class

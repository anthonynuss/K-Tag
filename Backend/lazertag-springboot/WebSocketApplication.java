package lazertag.websocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"lazertag.users", "lazertag.websocket"})
public class WebSocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApplication.class, args);
	}

}

package test;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import lazertag.users.Friends.Friends;
import lazertag.users.Friends.FriendsRepository;
import lazertag.users.Team.Team;
import lazertag.users.User.User;
import lazertag.users.User.UserRepository;

import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Rule;

public class MockitoTests {

	@Mock
	private User user = new User();

	@Mock
	private Friends friend = new Friends();

	@Mock
	private FriendsRepository friendsRepository;

	// Tests the all non-team User classes
	@Test
	public void Test1() {

		user.setId(25);
		user.setName("Daniel");
		user.setPassword("pass123");
		user.setWins(3);
		user.addWin();
		user.setLosses(5);
		user.addLosses();
		user.setTags(12);
		user.addTag();
		user.setKnockouts(45);
		user.addKnockouts();
		user.setLatitude(25);
		user.setLongitude(124);

		// User user = mock(User.class);
		/*
		 * when(user.getId()).thenReturn(25); when(user.getName()).thenReturn("Daniel");
		 * when(user.getPassword()).thenReturn("pass123");
		 * when(user.getWins()).thenReturn(4); when(user.getLosses()).thenReturn(6);
		 * when(user.getTags()).thenReturn(13);
		 * when(user.getKnockouts()).thenReturn(46);
		 * when(user.getLatitude()).thenReturn(25.0);
		 * when(user.getLongitude()).thenReturn(124.0);
		 */

		int resultid = user.getId();
		Assert.assertEquals(25, resultid);
		String result = user.getName();
		Assert.assertEquals("Daniel", result);
		result = user.getPassword();
		Assert.assertEquals("pass123", result);
		resultid = user.getWins();
		Assert.assertEquals(4, resultid);
		resultid = user.getLosses();
		Assert.assertEquals(6, resultid);
		resultid = user.getTags();
		Assert.assertEquals(13, resultid);
		resultid = user.getKnockouts();
		Assert.assertEquals(46, resultid);
		double resultd = user.getLatitude();
		Assert.assertEquals(25.0, resultd, 0.01);
		resultd = user.getLongitude();
		Assert.assertEquals(124.0, resultd, 0.01);

	}

	// Tests the friend adding function
	@Test
	public void Test2() {

		UserRepository userRepository = mock(UserRepository.class);

		User user = mock(User.class);
		when(user.getId()).thenReturn(25);
		when(user.getName()).thenReturn("Daniel");
		when(user.getPassword()).thenReturn("pass123");
		when(user.getWins()).thenReturn(4);
		when(user.getLosses()).thenReturn(6);

		User user2 = mock(User.class);
		when(user2.getId()).thenReturn(68);
		when(user2.getName()).thenReturn("Chris");
		when(user2.getPassword()).thenReturn("456pass");
		when(user2.getWins()).thenReturn(3);
		when(user2.getLosses()).thenReturn(8);

		when(userRepository.findById(25)).thenReturn(user);
		when(userRepository.findById(68)).thenReturn(user2);

		Friends fr1 = new Friends(user, user2);
		// Friends fr2 = new Friends(user2, user);
		String result = userRepository.findById(fr1.getPerson()).getName() + " and "
				+ userRepository.findById(fr1.getFriend()).getName() + " are friends";
		Assert.assertEquals("Daniel and Chris are friends", result);
	}

	// Tests the team creation function
	@Test
	public void Test3() {

		UserRepository userRepository = mock(UserRepository.class);

		User user = mock(User.class);
		when(user.getId()).thenReturn(25);
		when(user.getName()).thenReturn("Daniel");
		when(user.getPassword()).thenReturn("pass123");
		when(user.getWins()).thenReturn(4);
		when(user.getLosses()).thenReturn(6);

		User user2 = mock(User.class);
		when(user2.getId()).thenReturn(68);
		when(user2.getName()).thenReturn("Chris");
		when(user2.getPassword()).thenReturn("456pass");
		when(user2.getWins()).thenReturn(3);
		when(user2.getLosses()).thenReturn(8);

		when(userRepository.findById(25)).thenReturn(user);
		when(userRepository.findById(68)).thenReturn(user2);

		Team team1 = new Team("The Electrons", 25);
		team1.addTeammate(user2);

		String result = team1.getName() + " have a team captain: "
				+ userRepository.findById(team1.getCaptain()).getName() + " and has a team member: "
				+ team1.getTeammates().get(0).getName();
		Assert.assertEquals("The Electrons have a team captain: Daniel and has a team member: Chris", result);
	}
	
	//Tests modifying the team
	@Test
	public void Test4() {
		
		UserRepository userRepository = mock(UserRepository.class);

		User user = mock(User.class);
		when(user.getId()).thenReturn(25);
		when(user.getName()).thenReturn("Daniel");
		when(user.getPassword()).thenReturn("pass123");
		when(user.getWins()).thenReturn(4);
		when(user.getLosses()).thenReturn(6);

		User user2 = mock(User.class);
		when(user2.getId()).thenReturn(68);
		when(user2.getName()).thenReturn("Chris");
		when(user2.getPassword()).thenReturn("456pass");
		when(user2.getWins()).thenReturn(3);
		when(user2.getLosses()).thenReturn(8);

		when(userRepository.findById(25)).thenReturn(user);
		when(userRepository.findById(68)).thenReturn(user2);

		Team team1 = new Team("The Electrons", 25);
		team1.addTeammate(user2);
		
		String result = team1.getName() + " have a team captain: "
				+ userRepository.findById(team1.getCaptain()).getName() + " and has a team member: "
				+ team1.getTeammates().get(0).getName();
		Assert.assertEquals("The Electrons have a team captain: Daniel and has a team member: Chris", result);
		
		
		team1.setCaptain(68);
		team1.setWins(5);
		team1.setName("Good Team");
		team1.removeTeammate(user2);

		String result2 = team1.getName() + " has a team captain: "
				+ userRepository.findById(team1.getCaptain()).getName();
		Assert.assertEquals("Good Team has a team captain: Chris", result2);
	}
}

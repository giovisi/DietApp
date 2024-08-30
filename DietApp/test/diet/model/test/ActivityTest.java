package diet.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import diet.model.Activity;

class ActivityTest {
	
	@Test
	void testOK_Creation() {
		var activity = new Activity(10, 12, 1, 0, 1);
		assertEquals(activity.getRestHours(), 10);
		assertEquals(activity.getVeryLightActivityHours(), 12);
		assertEquals(activity.getLightActivityHours(), 1);
		assertEquals(activity.getModerateActivityHours(), 0);
		assertEquals(activity.getIntenseActivityHours(), 1);
	}
	
	@Test
	void testKO_MoreHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(10, 11, 1, 2, 1));
	}
	@Test
	void testKO_LessHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(10, 11, 1, 0, 1));
	}
	@Test
	void testKO_InvalidRestHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(-1, 22, 1, 1, 1));
		assertThrows(IllegalArgumentException.class, () -> new Activity(25, -4, 1, 1, 1));
	}
	@Test
	void testKO_InvalidVeryLightActivityHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(22, -1, 1, 1, 1));
		assertThrows(IllegalArgumentException.class, () -> new Activity(1, 25, -4, 1, 1));
	}
	@Test
	void testKO_InvalidLightActivityHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(22, 1, -1, 1, 1));
		assertThrows(IllegalArgumentException.class, () -> new Activity(1, 1, 25, -4, 1));
	}
	@Test
	void testKO_InvalidModerateActivityHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(22, 1, 1, -1, 1));
		assertThrows(IllegalArgumentException.class, () -> new Activity(1, 1, 1, 25, -4));
	}
	@Test
	void testKO_InvalidintenseActivityHours() {
		assertThrows(IllegalArgumentException.class, () -> new Activity(22, 1, 1, 1, -1));
	}
}
package diet.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import diet.model.Client;
import diet.model.Sex;

class ClientTest {
	
	@Test
	void testOK_CreationMale() {
		var client = new Client("John", "Doe", Sex.MALE, 69, 169.5, 169);
		assertEquals("John Doe", client.getFullName());
		assertEquals(Sex.MALE, client.getSex());
		assertEquals(69, client.getAge());
		assertEquals(169.5, client.getWeight());
		assertEquals(169, client.getHeight());
	}
	
	@Test
	void testOK_CreationFemale() {
		var client = new Client("Jane", "Doe", Sex.FEMALE, 69, 169.5, 169);
		assertEquals("Jane Doe", client.getFullName());
		assertEquals(Sex.FEMALE, client.getSex());
		assertEquals(69, client.getAge());
		assertEquals(169.5, client.getWeight());
		assertEquals(169, client.getHeight());
	}
	
	@Test
	void testKO_NullName() {
		assertThrows(IllegalArgumentException.class, () -> new Client(null, "Doe", Sex.MALE, 69, 169.5, 169));
	}
	@Test
	void testKO_EmptyName() {
		assertThrows(IllegalArgumentException.class, () -> new Client("", "Doe", Sex.MALE, 69, 169.5, 169));
	}
	@Test
	void testKO_NullSurame() {
		assertThrows(IllegalArgumentException.class, () -> new Client("John", null, Sex.MALE, 69, 169.5, 169));
	}
	@Test
	void testKO_EmptySurame() {
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "", Sex.MALE, 69, 169.5, 169));
	}
	@Test
	void testKO_NullSex() {
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", null, 69, 169.5, 169));
	}
	@Test
	void testKO_InvalidAge() {
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", Sex.MALE, -1, 169.5, 169));
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", Sex.MALE, 0, 169.5, 169));
	}
	@Test
	void testKO_InvalidWeight() {
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", Sex.MALE, 69, -1, 169));
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", Sex.MALE, 69, 0, 169));
	}
	@Test
	void testKO_InvalidHeight() {
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", Sex.MALE, 69, 169.5, -1));
		assertThrows(IllegalArgumentException.class, () -> new Client("John", "Doe", Sex.MALE, 69, 169.5, 0));
	}
}
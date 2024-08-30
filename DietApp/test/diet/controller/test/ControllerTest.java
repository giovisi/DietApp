package diet.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import diet.controller.Controller;
import diet.controller.ControllerNotReadyException;
import diet.model.Activity;
import diet.model.Client;
import diet.model.DietType;
import diet.model.Sex;

class ControllerTest {
	Client client = new Client("John", "Doe", Sex.MALE, 23, 76, 175);
	Activity activity = new Activity(10, 12, 1, 0, 1);
	Controller controller = new Controller();
	
	
	@Test
	void testOK_CalculateCalories() {
		controller.setClient(client);
		controller.setActivity(activity);
		
		assertEquals(controller.CalculateBasicCalories(), 1831.8071);
		assertEquals(controller.CalculateTotalCalories(), 2862.19859375);
		assertEquals(controller.CalculateActivityCalories(), 1030.3914937499999);
	}
	
	@Test
	void testKO_MissingClient() {
		assertThrows(ControllerNotReadyException.class, () -> controller.CalculateBasicCalories());
		assertThrows(ControllerNotReadyException.class, () -> controller.CalculateTotalCalories());
		assertThrows(ControllerNotReadyException.class, () -> controller.CalculateActivityCalories());
	}
	@Test
	void testKO_MissingActivity() {
		controller.setClient(client);
		
		assertEquals(Math.round(controller.CalculateBasicCalories()), 1832);
		assertThrows(ControllerNotReadyException.class, () -> controller.CalculateTotalCalories());
		assertThrows(ControllerNotReadyException.class, () -> controller.CalculateActivityCalories());
	}
	
	@Test
	void testKO_SetDietNotReady() {
		assertThrows(ControllerNotReadyException.class, () -> controller.setDiet(0, 0, null));
	}
	@Test
	void testKO_SetDietInvalidParameters() {
		controller.setClient(client);
		controller.setActivity(activity);
		
		assertThrows(IllegalArgumentException.class, () -> controller.setDiet(0, 0, null));
	}
	
	@Test
	void testOK_SetDietGetProtein() {
		controller.setClient(client);
		controller.setActivity(activity);
		controller.setDiet(2, 25, DietType.HYPOCALORIC);

		assertEquals(controller.getProtein().grams, 152);
		assertEquals(controller.getProtein().kcal, 608);
		assertEquals(controller.getProtein().percentage, 0.21242411387094198);
	}
	@Test
	void testKO_SetDietGetProtein() {
		controller.setClient(client);
		controller.setActivity(activity);
		
		assertThrows(ControllerNotReadyException.class, () -> controller.getProtein());
	}
	
	@Test
	void testOK_SetDietGetFat() {
		controller.setClient(client);
		controller.setActivity(activity);
		controller.setDiet(2, 25, DietType.HYPOCALORIC);
		
		assertEquals(controller.getFat().grams, 71.55496484375);
		assertEquals(controller.getFat().kcal, 643.99468359375);
		assertEquals(controller.getFat().percentage, 0.25);
	}
	@Test
	void testKO_SetDietGetFat() {
		controller.setClient(client);
		controller.setActivity(activity);
		
		assertThrows(ControllerNotReadyException.class, () -> controller.getFat());
	}

	@Test
	void testOK_SetDietGetCarbohydrate() {
		controller.setClient(client);
		controller.setActivity(activity);
		controller.setDiet(2, 25, DietType.HYPOCALORIC);
		
		assertEquals(controller.getCarbohydrate().grams, 346.1960126953125);
		assertEquals(controller.getCarbohydrate().kcal, 1384.78405078125);
		assertEquals(controller.getCarbohydrate().percentage, 0.537575886129058);
	}
	@Test
	void testKO_SetDietGetCarbohydrate() {
		controller.setClient(client);
		controller.setActivity(activity);
		
		assertThrows(ControllerNotReadyException.class, () -> controller.getCarbohydrate());
	}
}
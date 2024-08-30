package diet.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import diet.controller.HarrisBenedictBMRCalculator;
import diet.controller.MyTotalCaloriesCalculator;
import diet.model.Activity;
import diet.model.Client;
import diet.model.Sex;

class TotalCaloriesCalculatorTest {
	Client clientM = new Client("John", "Doe", Sex.MALE, 23, 76, 175);
	Client clientF = new Client("John", "Doe", Sex.FEMALE, 22, 56, 165);
	Activity activity = new Activity(10, 12, 1, 0, 1);
	MyTotalCaloriesCalculator calculator = new MyTotalCaloriesCalculator(new HarrisBenedictBMRCalculator());
	
	@Test
	void testOK_Male() {		
		assertEquals(calculator.CalculateTotalCalories(clientM, activity), 2862.19859375);
	}
	@Test
	void testOK_Female() {		
		assertEquals(calculator.CalculateTotalCalories(clientF, activity), 2176.5104687499997);
	}
	
	@Test
	void testKO_NullClient() {		
		assertThrows(IllegalArgumentException.class, () -> calculator.CalculateTotalCalories(null, activity));
	}
	@Test
	void testKO_NullActivity() {		
		assertThrows(IllegalArgumentException.class, () -> calculator.CalculateTotalCalories(clientM, null));
	}
	
	
	@Test
	void testOK_BasicMale() {	
		assertEquals(calculator.CalculateBasicCalories(clientM), 1831.8071);
	}
	@Test
	void testOK_BasicFemale() {		
		assertEquals(calculator.CalculateBasicCalories(clientF), 1392.9667);
	}

	@Test
	void testKO_BasicNullClient() {		
		assertThrows(IllegalArgumentException.class, () -> calculator.CalculateBasicCalories(null));
	}
	
	
	@Test
	void testOK_ActivityMale() {
		assertEquals(calculator.CalculateActivityCalories(clientM, activity), 1030.3914937499999);
	}
	@Test
	void testOK_ActivityFemale() {
		assertEquals(calculator.CalculateActivityCalories(clientF, activity), 783.5437687499998);
	}
	
	@Test
	void testKO_ActivityNullClient() {
		assertThrows(IllegalArgumentException.class, () -> calculator.CalculateActivityCalories(null, activity));
	}
	@Test
	void testOK_ActivityNullActivity() {
		assertThrows(IllegalArgumentException.class, () -> calculator.CalculateActivityCalories(clientM, null));
	}
}
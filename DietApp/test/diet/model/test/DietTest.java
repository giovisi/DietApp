package diet.model.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import diet.controller.HarrisBenedictBMRCalculator;
import diet.controller.MyTotalCaloriesCalculator;
import diet.model.Activity;
import diet.model.Client;
import diet.model.Diet;
import diet.model.Sex;
import diet.model.DietType;

class DietTest {
	Client client = new Client("John", "Doe", Sex.MALE, 23, 76, 175);
	Activity activity = new Activity(10, 12, 1, 0, 1);
	MyTotalCaloriesCalculator calculator = new MyTotalCaloriesCalculator(new HarrisBenedictBMRCalculator());
	Diet diet = new Diet(client, activity, 2.0, 25.0, calculator.CalculateTotalCalories(client, activity), DietType.HYPOCALORIC);

	@Test
	void testOK() {
		assertEquals(diet.getClient(), client);
		assertEquals(diet.getActivity(), activity);
		assertEquals(diet.getProteinKiloRatio(), 2);
		assertEquals(diet.getFatPercentage(), 25);
		assertEquals(diet.getType(), DietType.HYPOCALORIC);
		assertEquals(diet.getDiet(), calculator.CalculateTotalCalories(client, activity) * 0.9);

	}

	@Test
	void testKO_NullClient() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(null, activity, 2.0, 25.0, calculator.CalculateTotalCalories(client, activity), DietType.HYPOCALORIC));
	}

	@Test
	void testKO_NullActivity() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(client, null, 2.0, 25.0, calculator.CalculateTotalCalories(client, activity), DietType.HYPOCALORIC));
	}

	@Test
	void testKO_InvalidPKRatio1() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(client, activity, 0, 25.0, calculator.CalculateTotalCalories(client, activity), DietType.HYPOCALORIC));
	}

	@Test
	void testKO_InvalidFatPercentage1() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(client, activity, 2.0, 0, calculator.CalculateTotalCalories(client, activity), DietType.HYPOCALORIC));
	}

	@Test
	void testKO_InvalidFatPercentage2() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(client, activity, 2.0, 100, calculator.CalculateTotalCalories(client, activity), DietType.HYPOCALORIC));
	}

	@Test
	void testKO_InvalidDiet() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(client, activity, 2.0, 25.0, -1, DietType.HYPOCALORIC));
	}

	@Test
	void testKO_NullDietType() {
		assertThrows(IllegalArgumentException.class, () -> new Diet(client, activity, 2.0, 25.0, calculator.CalculateTotalCalories(client, activity), null));
	}
}
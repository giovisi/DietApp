package diet.controller.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import diet.controller.HarrisBenedictBMRCalculator;
import diet.model.Client;
import diet.model.Sex;

class HarrisBenedictBMRCalculatorTest {
	
	HarrisBenedictBMRCalculator calculator = new HarrisBenedictBMRCalculator();
	
	@Test
	void testOK_Male() {
		var client = new Client("John", "Doe", Sex.MALE, 23, 76, 175);
		
		assertEquals(calculator.Calculate(client), 1831.8071);
	}
	@Test
	void testOK_Female() {
		var client = new Client("Jane", "Doe", Sex.FEMALE, 22, 56, 165);
		
		assertEquals(calculator.Calculate(client), 1392.9667);
	}
	
	@Test
	void testKO_NullClient() {		
		assertThrows(IllegalArgumentException.class, () -> calculator.Calculate(null));
	}
}
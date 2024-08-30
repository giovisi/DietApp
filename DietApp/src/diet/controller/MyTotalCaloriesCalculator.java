package diet.controller;

import diet.model.Activity;
import diet.model.Client;

public class MyTotalCaloriesCalculator implements TotalCaloriesCalculator {
	
	private BMRCalculator calculator;
	
	public MyTotalCaloriesCalculator(BMRCalculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public double CalculateTotalCalories(Client client, Activity activity)
		throws IllegalArgumentException {
		if (calculator == null) throw new IllegalArgumentException("Total calories calculator is null!");
		if (client == null) throw new IllegalArgumentException("Client is null!");
		if (activity == null) throw new IllegalArgumentException("Activity is null!");
		return calculator.Calculate(client) / 24 * (activity.getRestHours() * 1 + activity.getVeryLightActivityHours() * 1.5 + activity.getLightActivityHours() * 2.5 + 
				activity.getModerateActivityHours() * 5 + activity.getIntenseActivityHours() * 7);
	}	
	
	@Override
	public double CalculateBasicCalories(Client client) throws IllegalArgumentException {
		return calculator.Calculate(client);
	}
	
	@Override
	public double CalculateActivityCalories(Client client, Activity activity) throws IllegalArgumentException {
		return CalculateTotalCalories(client, activity) - CalculateBasicCalories(client);
	}
}
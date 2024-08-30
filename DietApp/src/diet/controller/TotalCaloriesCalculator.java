package diet.controller;

import diet.model.Activity;
import diet.model.Client;

public interface TotalCaloriesCalculator {
	public double CalculateTotalCalories(Client client, Activity activity)throws IllegalArgumentException;
	public double CalculateBasicCalories(Client client) throws IllegalArgumentException;
	public double CalculateActivityCalories(Client client, Activity activity) throws IllegalArgumentException;
}
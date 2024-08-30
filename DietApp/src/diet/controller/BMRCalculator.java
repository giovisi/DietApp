package diet.controller;

import diet.model.Client;

public interface BMRCalculator {
	public double Calculate(Client client) throws IllegalArgumentException;
}
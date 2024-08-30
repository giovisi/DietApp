package diet.controller;

import diet.model.Client;
import diet.model.Sex;

public class HarrisBenedictBMRCalculator implements BMRCalculator {
	
	@Override
	public double Calculate(Client client) {
		if (client == null) throw new IllegalArgumentException("Invalid client!");
		return (client.getSex() == Sex.MALE) ? 66.4730 + (13.7516 * client.getWeight()) + (5.0033 * client.getHeight()) - (6.7550 * client.getAge())
				: 655.0955 + (9.5634 * client.getWeight()) + (1.8496 * client.getHeight()) - (4.6756 * client.getAge());
	}
}
package diet.model;

public enum DietType {
	HYPOCALORIC(0.9), NORMOCALORIC(1), HYPERCALORIC(1.1);
	
	private final double multiplier;
	
	DietType(double multiplier) {
		this.multiplier = multiplier;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
}
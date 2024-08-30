package diet.model;

public enum Calories {
	PROTEIN(4), CARBOHYDRATE(4), FAT(9);
	
	private final int cal;
	
	Calories(int cal) {
		this.cal = cal;
	}
	
	public double calorieValue() {
		return cal;
	}
}
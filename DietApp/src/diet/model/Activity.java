package diet.model;

public class Activity {
	private int restHours, veryLightActivityHours, lightActivityHours, moderateActivityHours, intenseActivityHours;

	public Activity(int restHours, int veryLightActivityHours, int lightActivityHours, int moderateActivityHours,
			int intenseActivityHours) throws IllegalArgumentException {
		assignValues(restHours, veryLightActivityHours, lightActivityHours, moderateActivityHours, intenseActivityHours);
		checkArgumentsAndThrowException();
	}

	private void assignValues(int restHours, int veryLightActivityHours, int lightActivityHours, int moderateActivityHours,
							  int intenseActivityHours) {
		this.restHours = restHours;
		this.veryLightActivityHours = veryLightActivityHours;
		this.lightActivityHours = lightActivityHours;
		this.moderateActivityHours = moderateActivityHours;
		this.intenseActivityHours = intenseActivityHours;
	}
	private void checkArgumentsAndThrowException() throws IllegalArgumentException {
		checkValues();
		checkTotalAmountAndThrowException();
	}
	private void checkValues() {
		checkRestHoursAndThrowException();
		checkVeryLightActivityHoursAndThrowException();
		checkLightActivityHoursAndThrowException();
		checkModerateActivityHoursAndThrowException();
		checkIntenseActivityHoursAndThrowException();
	}
	private void checkRestHoursAndThrowException() {
		if (restHours < 0 || restHours > 24) throw new IllegalArgumentException("Invalid rest hours!");
	}
	private void checkVeryLightActivityHoursAndThrowException() {
		if (veryLightActivityHours < 0 || veryLightActivityHours > 24) throw new IllegalArgumentException("Invalid very light activity hours!");
	}
	private void checkLightActivityHoursAndThrowException() {
		if (lightActivityHours < 0 || lightActivityHours > 24) throw new IllegalArgumentException("Invalid light activity hours!");
	}
	private void checkModerateActivityHoursAndThrowException() {
		if (moderateActivityHours < 0 || moderateActivityHours > 24) throw new IllegalArgumentException("Invalid moderate activity hours!");
	}
	private void checkIntenseActivityHoursAndThrowException() {
		if (intenseActivityHours < 0 || intenseActivityHours > 24) throw new IllegalArgumentException("Invalid intense activity hours!");
	}
	private void checkTotalAmountAndThrowException() {
		if (restHours + veryLightActivityHours + lightActivityHours + moderateActivityHours + intenseActivityHours != 24)
			throw new IllegalArgumentException("Invalid amount of hours: the total must be 24!");
	}


	public int getRestHours() {
		return restHours;
	}
	public int getVeryLightActivityHours() {
		return veryLightActivityHours;
	}
	public int getLightActivityHours() {
		return lightActivityHours;
	}
	public int getModerateActivityHours() {
		return moderateActivityHours;
	}
	public int getIntenseActivityHours() {
		return intenseActivityHours;
	}
}
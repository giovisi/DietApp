package diet.model;

public enum Sex {
	MALE("Male"), FEMALE("Female");

	private final String sex;

	Sex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return sex;
	}


}
package diet.model;

import java.util.Objects;

public class Client {
	private String name, surname;
	private Sex sex;
	private int age, height;
	private double weight;
	
	public Client(String name, String surname, Sex sex, int age, double weight, int height) throws IllegalArgumentException {
		assignValues(name, surname, sex, age, weight, height);
		checkArgumentsAndThrowException();
	}
	private void assignValues(String name, String surname, Sex sex, int age, double weight, int height) {
		this.name = name;
		this.surname = surname;
		this.sex = sex;
		this.age = age;
		this.weight = weight;
		this.height = height;
	}
	private void checkArgumentsAndThrowException() throws IllegalArgumentException {
		checkNameAndSurnameAndThrowException();
		checkSexAndThrowException();
		checkAgeAndThrowException();
		checkWeightAndThrowException();
		checkHeightAndThrowException();
	}
	private void checkNameAndSurnameAndThrowException() {
		if (name == null || surname == null || name.isEmpty() || surname.isEmpty()) throw new IllegalArgumentException("Invalid name or surname!");
	}
	private void checkSexAndThrowException() {
		if (sex == null) throw new IllegalArgumentException("Invalid sex!");
	}
	private void checkAgeAndThrowException() {
		if (age <= 0) throw new IllegalArgumentException("Invalid age!");
	}
	private void checkWeightAndThrowException() {
		if (weight <= 0) throw new IllegalArgumentException("Invalid weight!");
	}
	private void checkHeightAndThrowException() {
		if (height <= 0) throw new IllegalArgumentException("Invalid height!");
	}

	public String getFullName() {
		return name + " " + surname;
	}
	public Sex getSex() {
		return sex;
	}
	public int getAge() {
		return age;
	}
	public double getWeight() { return weight; }
	public int getHeight() { return height; }

	@Override
	public int hashCode() {
		return Objects.hash(age, height, name, sex, surname, weight);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return age == other.age && height == other.height
				&& Objects.equals(name, other.name) && sex == other.sex
				&& Objects.equals(surname, other.surname)
				&& Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
	}
}
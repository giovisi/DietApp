package diet.model;

public class Diet {
	private Client client;
	private Activity activity;
	private double proteinKiloRatio, fatPercentage, basicDiet;
	private DietType type;
	
	public Diet (Client client, Activity activity, double proteinKiloRatio, double fatPercentage, double basicDiet, DietType type) throws IllegalArgumentException {
		assignValues(client, activity, proteinKiloRatio, fatPercentage, basicDiet,type);
		checkArgumentsAndThrowException();
	}
	private void assignValues(Client client, Activity activity, double proteinKiloRatio, double fatPercentage,
							  double basicDiet, DietType type) {
		this.client = client;
		this.activity = activity;
		this.proteinKiloRatio = proteinKiloRatio;
		this.fatPercentage = fatPercentage;
		this.basicDiet = basicDiet;
		this.type = type;
	}
	private void checkArgumentsAndThrowException() throws IllegalArgumentException {
		checkClientAndThrowException();
		checkActivityAndThrowException();
		checkProteinKiloRatioAndThrowException();
		checkFatPercentageAndThrowException();
		checkBasicDietAndThrowException();
		checkDietTypeAndThrowException();
	}
	private void checkClientAndThrowException() {
		if (client == null) throw new IllegalArgumentException("Client is null!");
	}
	private void checkActivityAndThrowException() {
		if (activity == null) throw new IllegalArgumentException("Activity is null!");
	}
	private void checkProteinKiloRatioAndThrowException() {
		if (proteinKiloRatio <= 0) throw new IllegalArgumentException("Invalid protein/kilograms ratio!");
	}
	private void checkFatPercentageAndThrowException() {
		if (fatPercentage <= 0 || fatPercentage >= 100) throw new IllegalArgumentException("Invalid fat percentage!");
	}
	private void checkBasicDietAndThrowException() {
		if (basicDiet <= 0) throw new IllegalArgumentException("Invalid basic diet!");
	}
	private void checkDietTypeAndThrowException() {
		if (type == null) throw new IllegalArgumentException("Type is null!");
	}

	
	public Client getClient() {
		return client;
	}
	public Activity getActivity() {
		return activity;
	}
	public double getProteinKiloRatio() {
		return proteinKiloRatio;
	}
	public double getFatPercentage() {
		return fatPercentage;
	}
	public double getDiet() {
		return basicDiet * type.getMultiplier();
	}
	public DietType getType() {
		return type;
	}
	
	
	
	
	
	
	
	
	
	
	
}
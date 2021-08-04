package jo.secondstep.database;

public class Region {
	private String name;
	private int numberOfEmployees;
	
	Region(String name, int numberOfEmployees){
		this.name = name;
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(int numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

}

package jo.secondstep.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Country {
	
	private String name;	
	private List<Department> departments = new ArrayList<Department>();

	private Map<String, List<Department>> countries = new HashMap<String, List<Department>>();

	public Map<String, List<Department>> getCountries() {
		return countries;
	}

	public void setCountries(Map<String,List<Department>> countries) {
		this.countries = countries;
	}
	
	void set(String country, Department department) {
		departments.add(department);
		countries.put(country, departments);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> department) {
		this.departments = department;
	}

}

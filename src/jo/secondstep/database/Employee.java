package jo.secondstep.database;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	
	private String name;
	private int year;
	private List<Child> childs = new ArrayList<Child>();
	private String region;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<Child> getChilds() {
		return childs;
	}
	public void setChilds(List<Child> childs) {
		this.childs = childs;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

}

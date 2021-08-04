package jo.secondstep.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HR {
	
	Department department = new Department();
	
	ResultSet databaseConnection(String sqlStatement) {
		String url = "jdbc:mysql://localhost:3306/hr";
		String username = "root";
		String password = "1234";
		ResultSet result = null;
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			
			Statement statement = connection.createStatement();
			result = statement.executeQuery(sqlStatement);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return result;
	}
	
// 1- job title has minimum salary greater than 5000	
	List<Job> getJobs() {
		String sqlStatement = "select job_title from jobs where min_salary > 5000";
		ResultSet result = databaseConnection(sqlStatement);
		List<Job> jobs = new ArrayList<Job>();
			try {
				while(result.next()) {
					Job job = new Job();
					job.setName(result.getString("job_title"));
					jobs.add(job);
					
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			

		
		return jobs;
		
	}
	
//  2- Total salary for all employees that work in a department

	Department deptSalaries(String department) {
		
		try {
			String sqlStatement = "select d.department_name, sum(e.salary) as salary from departments d inner join employees e on d.department_id = e.department_id group by d.department_name having d.department_name = \'" + department + "\';";
			ResultSet result = databaseConnection(sqlStatement);
			
			while(result.next()) {
				
				this.department.setName((result.getString("department_name")));
				this.department.setSalary(result.getDouble("salary"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return this.department;
		
		
	}
	
// 3- Total salary for each department in each country	
	
	Map<String, List<Department>> getCountries() {
		
		Map<String, List<Department>> countries = new HashMap<String, List<Department>>();
		try {
			String sqlStatement = "select sum(e.salary) total_salary, d.department_name, c.country_name country_name\r\n"
					+ "from departments d inner join employees e on d.department_id = e.department_id inner join locations l on l.location_id = d.location_id inner join countries c on c.country_id = l.country_id\r\n"
					+ "group by c.country_name, d.department_name;";

			ResultSet result = databaseConnection(sqlStatement);

			while(result.next()) {
				
				Department department = new Department();
				department.setName(result.getString("department_name"));
				department.setSalary(result.getDouble("total_salary"));
				String country = result.getString("country_name");
				if (!countries.containsKey(country)) { 
					countries.put(country, new ArrayList<Department>()); 
			    }
				countries.get(country).add(department);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return countries;
	}
	
// 4- Number of employees in each department	
	
	List<Department> departmentEmployees() {
		
		List<Department> departments = new ArrayList<Department>();
		
		
		try {
			String sqlStatement = "select count(e.employee_id) as Employees, d.department_name \r\n"
					+ "from employees e inner join departments d on e.department_id = d.department_id\r\n"
					+ "group by d.department_name;";

			ResultSet result = databaseConnection(sqlStatement);
			while(result.next()) {
				Department department = new Department();
				department.setName(result.getString("department_name"));
				department.setNumberOfEmployees(result.getInt("Employees"));
				departments.add(department);

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return departments;
	}
	
// 5- Number of employees that hired in each year	
	
	Map<Integer, Integer> years(){
		

		Map<Integer, Integer> years = new HashMap<Integer, Integer>();
		try {
			String sqlStatement = "select count(employee_id) as Employees, year(hire_date) hire_year from employees group by year(hire_date);";
			ResultSet result = databaseConnection(sqlStatement);
			
			while(result.next()) {

				years.put(result.getInt("hire_year"), result.getInt("Employees"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return years;
		
	}
	
// 6- Name/s of employee's children	
	
	Employee EmployeesChilds(String employeeName){
		
		List<Child> children = new ArrayList<Child>(); 
		Employee employee = new Employee();
		try {
			String sqlStatement = "select d.first_name as child_name, e.first_name from dependents d inner join employees e on d.employee_id = e.employee_id where e.first_name = \'" + employeeName + "\';";
			ResultSet result = databaseConnection(sqlStatement);;
			
			while(result.next()) {
				children.add(new Child(result.getString("child_name")));
				employee.setName(result.getString("first_name"));
			}
			employee.setChilds(children);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return employee;
	}
	
// 7- Average of salary for employees that hired between two years
	
	double salariesBetween(int firstYear, int lastYear) {
		
		double salaryAVG = 0;
		try {
			String sqlStatement = "select avg(salary) from employees where year(hire_date) between " + firstYear + " and " + lastYear;
			ResultSet result = databaseConnection(sqlStatement);
			
			while(result.next()) {
				salaryAVG = result.getDouble(1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		
		return salaryAVG;
	}
	
// 8-  Number of Employees in each region	
	
	List<Region> employeesInRegions(){

		List<Region> regions = new ArrayList<Region>();
		try {
			String sqlStatement = "select count(e.employee_id) as Employees, r.region_name\r\n"
					+ "from departments d inner join employees e on d.department_id = e.department_id inner join  locations l on l.location_id = d.location_id inner join  countries c on c.country_id = l.country_id right join regions r on c.region_id = r.region_id \r\n"
					+ "group by r.region_name\r\n"
					+ "order by count(e.employee_id) desc;";

			ResultSet result = databaseConnection(sqlStatement);
			
			while(result.next()) {
				
				regions.add(new Region(result.getString("region_name"), result.getInt("Employees")));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return regions;
	}
	
// 9- All employees depend on manager name	
	
	List<Employee> getEmployeesDependOnManager(String manager){

		List<Employee> employees = new ArrayList<Employee>();
		try {
			
			String sqlStatement = "select e1.employee_id, e1.first_name employee_name\r\n"
					+ "from employees e1 inner join employees e2 on e1.manager_id = e2.employee_id\r\n"
					+ "where e2.first_name = \'" + manager +"\';";

			ResultSet result = databaseConnection(sqlStatement);
			
			while(result.next()) {
				
				Employee employee = new Employee();
				employee.setName(result.getString("employee_name"));
				employees.add(employee);
			}
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return employees;
	}
	
// 10- Names of employees depends on postal code	
	
	List<Employee> getEmployeesDependOnPostalCode(String postalCode){

		List<Employee> employees = new ArrayList<Employee>();
		try {
			
			String sqlStatement = "select e.first_name \r\n"
					+ "from departments d inner join locations l on d.location_id = l.location_id inner join employees e on d.department_id = e.department_id\r\n"
					+ "where postal_code = '98199'; ";

			ResultSet result = databaseConnection(sqlStatement);
			
			while(result.next()) {
				
				Employee employee = new Employee();
				employee.setName(result.getString("first_name"));
				employees.add(employee);
			}
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return employees;
	}
	

	
	

}

package jo.secondstep.database;



public class Main {

	public static void main(String[] args) {
				
		HR hr = new HR();
		
		
/* #1	*/	for(Job job : hr.getJobs()) {
				System.out.println(job.getName());
			}
		
/* #2		System.out.println(hr.deptSalaries("IT").getName() + "  " + hr.deptSalaries("IT").getSalary());*/
		
/* #3	for(Department department : hr.getCountries().get("United States of America")) {
			System.out.println(department.getName() + " " + department.getSalary());
			}
*/
	
		
/* #4	for(Department department : hr.departmentEmployees()) {
			System.out.println(department.getName() + " " + department.getNumberOfEmployees());
		}
*/	
		
/* #5 	System.out.println(hr.years()); */	

/* #6	String employeeName = "Neena";		
		for(Child child : hr.EmployeesChilds(employeeName).getChilds()) {
			System.out.println(child.getName());
		}
*/	
/* #7		System.out.println(hr.salariesBetween(1990, 2000));*/
		
/* #8		for(Region region : hr.employeesInRegions()) {
			System.out.println(region.getName() + " " + region.getNumberOfEmployees());
	}
*/
	
/* #9		for(Employee employee : hr.getEmployeesDependOnManager("Neena")) {
			System.out.println(employee.getName());
		}
*/		
		
/* #10			for(Employee employee : hr.getEmployeesDependOnPostalCode("98199")) {
			System.out.println(employee.getName());
		}

*/
	


		
	}


}

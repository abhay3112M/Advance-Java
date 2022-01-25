package in.abhay.ems.tests;

import static in.abhay.utils.testing.CustomAssert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.abhay.ems.Employee;

public class EmployeeTest {

	Employee employee;
	int id=1;
	String name="Abhay";
	double salary=50000;
	
	@Before
	public void arrange() {
		employee=new Employee(id,name,salary);
	}
	
	@Test
	public void employeeHasASalary() {
		assertEquals(salary, employee.getSalary(),0);
	}

	@Test
	public void employeeInfoIncludesEmployeeName() {
		assertContainsSubstring(employee.info(), name);
	}
	
}

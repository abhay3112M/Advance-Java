package in.abhay.ems.tests;

import static in.abhay.utils.testing.CustomAssert.assertContainsSubstring;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import in.abhay.ems.Manager;

public class ManagerTest {

	Manager manager;
	int id=1;
	String name="Abhay";
	double salary=50000;
	
	@Before
	public void arrange() {
		manager=new Manager(id,name,salary);
	}
	
	@Test
	public void managerHasASalary() {
		assertEquals(salary, manager.getSalary(),0);
	}

	@Test
	public void managerInfoIncludesEmployeeName() {
		assertContainsSubstring(manager.info(), name);
	}
	
	@Test
	public void managerHasAName() {
		assertEquals(name, manager.getName());
	}
//	
}









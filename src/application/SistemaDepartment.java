package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class SistemaDepartment {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		/** TEST 01 */
		Department department = departmentDao.findById(3);
		System.out.println("====== Test 1: findById =======");
		System.out.println(department);

		department.setId(2);
		List<Department> list01 = departmentDao.findAll();
		System.out.println();
		System.out.println("====== Test 2: findAll =======");
		for (Department d: list01) {
			System.out.println(d);
		}

		System.out.println();
		System.out.println("====== Test 3: Insert =======");
		Department department02 = new Department(null, "Food");
		departmentDao.insert(department02);
		System.out.println("Inserted! New id = " + department02.getId());

		System.out.println();
		System.out.println("====== Test 4: Update =======");
		department02.setName("Marketing");
		departmentDao.update(department02);
		System.out.println("Update completed!");

		Scanner input = new Scanner(System.in);
		System.out.println();
		System.out.println("====== Test 5: Delete =======");
		System.out.println("Enter id for delete test: ");
		int id = input.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Deleted completed!");
		input.close();

		
	}

}

package application;

import java.sql.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Sistema02 {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
		/** TEST 01 */
		Seller seller = sellerDao.findById(3);
		System.out.println("====== Test 1: findById =======");
		System.out.println(seller);

		Department department = new Department();
		department.setId(2);
		List<Seller> list01 = sellerDao.findByDepartment(department);
		System.out.println();
		System.out.println("====== Test 2: findByDepartment =======");
		for (Seller s : list01) {
			System.out.println(s);
		}

		list01 = sellerDao.findAll();
		System.out.println();
		System.out.println("====== Test 3: findAll =======");
		for (Seller s : list01) {
			System.out.println(s);
		}

		System.out.println();
		System.out.println("====== Test 4: Insert =======");
		Seller seller01 = new Seller(12, "Alicinha Leite", "alicinha@gmail.com",Date.valueOf("2021-01-07"), 15500.0, new Department(2, null));
		sellerDao.insert(seller01);
		System.out.println("Inserted! New id = " + seller01.getId());
	}

}

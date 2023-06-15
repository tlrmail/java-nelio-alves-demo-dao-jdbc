package application;

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
	}

}

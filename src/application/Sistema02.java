package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Sistema02 {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();
		/** TEST 01*/
		Seller seller = sellerDao.findById(3);
		System.out.println("====== Test 1: findById =======");
		System.out.println(seller);

	}

}

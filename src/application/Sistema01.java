package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Sistema01 {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "Books");
		System.out.println(obj);
		
		Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 1100.0, obj);
		System.out.println(seller);
		

	}

}

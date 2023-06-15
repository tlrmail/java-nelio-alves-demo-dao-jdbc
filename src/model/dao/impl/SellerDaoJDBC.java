package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection connection = null;
			
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(Seller Seller) {
		
	}

	@Override
	public void update(Seller Seller) {
		
	}

	@Override
	public void deleteById(Integer id) {
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Seller seller = null;
		try {
			String sql = "SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id \r\n"
					+ "WHERE seller.Id = ?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			rs = statement.executeQuery();
			if(rs.next()) {
				Department department = new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
				Integer id_seller = rs.getInt("Id");
				String name = rs.getString("Name");
				String email = rs.getString("Email");
				Date birthDate = rs.getDate("BirthDate");
				Double baseSalary = rs.getDouble("BaseSalary");
				seller = new Seller(id_seller, name, email, birthDate, baseSalary, department);
			}
				
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		
		
		return seller;
	}

	@Override
	public List<Seller> findAll() {
		return null;
	}

}

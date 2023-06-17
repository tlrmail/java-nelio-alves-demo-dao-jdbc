package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void insert(Seller seller) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		try {
			
			ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}else {
					throw new DbException("Unexpected error! No rows affected!");
				}
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
		}
		
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
				Department department = instatiateDepartment(rs);
				seller = instatiateSeller(rs, department);
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
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Seller> list = new ArrayList<>();
		try {
			String sql = "SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, department.getId());
			rs = statement.executeQuery();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instatiateSeller(rs, dep);
				list.add(seller);
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		
		return list;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Seller> list = new ArrayList<>();
		try {
			String sql = "SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instatiateSeller(rs, dep);
				list.add(seller);
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally{
			DB.closeStatement(statement);
			DB.closeResultSet(rs);
		}
		
		return list;
	}

	private Seller instatiateSeller(ResultSet rs, Department department) throws SQLException {
		Integer id_seller = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("Email");
		Date birthDate = rs.getDate("BirthDate");
		Double baseSalary = rs.getDouble("BaseSalary");
		return new Seller(id_seller, name, email, birthDate, baseSalary, department);
	}

	private Department instatiateDepartment(ResultSet rs) throws SQLException{
		return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
	}

}

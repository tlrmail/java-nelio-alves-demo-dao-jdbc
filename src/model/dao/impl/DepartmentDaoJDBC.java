package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection connection = null;
	
	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void insert(Department department) {
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			String sql = "INSERT INTO DEPARTMENT(name) VALUES(?)";
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, department.getName());
			int rowsAffected = preparedStatement.executeUpdate();
			if(rowsAffected > 0) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}else {
					throw new DbException("Unexpected error! No rows affected!");
				}
			}
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public void update(Department department) {

		PreparedStatement preparedStatement = null;
		
		try {
			String sql = "UPDATE DEPARTMENT SET name = ? WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, department.getName());
			preparedStatement.setInt(2, department.getId());
			preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement preparedStatement = null;
		
		try {
			String sql = "DELETE FROM DEPARTMENT WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			int rows = preparedStatement.executeUpdate();
			if(rows == 0) {
				throw new DbException("Esse id não existe.");
			}
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		
		Department department = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT * FROM Department WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				department = new Department(rs.getInt(1), rs.getString(2));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

		return department;
	}

	@Override
	public List<Department> findAll() {
		List<Department> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "SELECT * FROM Department";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Department(rs.getInt(1), rs.getString(2)));
			}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
		return list;
	}

}

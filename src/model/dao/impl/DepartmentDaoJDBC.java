package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void insert(Department obj) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String insert = "INSERT INTO department " +
                "(Name) " +
                "VALUES " +
                "(?)";

        try {

            st = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());

            int rowsAffected = st.executeUpdate();
            rs = st.getGeneratedKeys();

            if (rowsAffected > 0) {

                if (rs.next()) {

                    System.out.println("Rows affected: " + rowsAffected);
                    obj.setId(rs.getInt(1));

                }

            } else {
                throw new DbException("Error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public void update(Department obj) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String update = "UPDATE department " +
                "set Name = ? " +
                "where id = ? limit 1";


        try {

            st = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setInt(2,obj.getId());

            int rowsAffected = st.executeUpdate();
            rs = st.getGeneratedKeys();

            if (rowsAffected > 0) {

                if (rs.next()) {

                    System.out.println("Rows affected: " + rowsAffected);
                    obj.setId(rs.getInt(1));

                }

            } else {
                throw new DbException("Error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public void deleteById(Integer id) {


        PreparedStatement st = null;
        String delete =  "DELETE FROM department WHERE Id = ? limit 1";

        try {

            st = conn.prepareStatement(delete);
            st.setInt(1,id);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Success! " + rowsAffected + " rows affected!");
            } else {
                throw new DbException("Error in delete! No Rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query =  "SELECT * FROM department WHERE Id = ?";

        try {

            st = conn.prepareStatement(query);
            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()) {

                return instantiateDepartment(rs);

            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Department> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query = "SELECT * FROM department";

        try {

            st = conn.prepareStatement(query);
            rs = st.executeQuery();

            List<Department> departments = new ArrayList<>();

            while (rs.next()) {
                departments.add(instantiateDepartment(rs))      ;
            }

            return departments;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {

        int depId = rs.getInt("Id");
        String depName = rs.getString("Name");

        return new Department(depId,depName);

    }

}

package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String update = "INSERT INTO seller " +
                        "(Name,Email,BirthDate,BaseSalary,DepartmentId) " +
                        "VALUES " +
                        "(?,?,?,?,?)";

        try {

            st = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());

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
    public void update(Seller obj) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String update = "UPDATE seller " +
                        "set Name = ?, " +
                        "Email = ?, " +
                        "BirthDate = ?, " +
                        "BaseSalary = ?, " +
                        "DepartmentId = ? " +
                        "where id = ? limit 1";


        try {

            st = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

            st.setString(1,obj.getName());
            st.setString(2,obj.getEmail());
            st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5,obj.getDepartment().getId());
            st.setInt(6,obj.getId());

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
        String query =  "DELETE FROM seller WHERE Id = ? limit 1";

        try {

            st = conn.prepareStatement(query);
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
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query =  "SELECT seller.*,department.Name as DepName "   +
                        "FROM seller INNER JOIN department "            +
                        "ON seller.DepartmentId = department.Id "       +
                        "WHERE seller.Id = ?";

        try {

            st = conn.prepareStatement(query);
            st.setInt(1,id);
            rs = st.executeQuery();

            if (rs.next()) {

                Department dep = instantiateDepartment(rs);
                return instantiateSeller(rs,dep);

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
    public List<Seller> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query =  "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "ORDER BY Name";

        try {

            st = conn.prepareStatement(query);
            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }

                Seller seller = instantiateSeller(rs,dep);
                sellers.add(seller);

            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query =  "SELECT seller.*,department.Name as DepName " +
                        "FROM seller INNER JOIN department " +
                        "ON seller.DepartmentId = department.Id " +
                        "WHERE DepartmentId = ? " +
                        "ORDER BY Name";

        try {

            st = conn.prepareStatement(query);
            st.setInt(1,department.getId());
            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"),dep);
                }

                Seller seller = instantiateSeller(rs,dep);
                sellers.add(seller);

            }

            return sellers;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {

        int depId = rs.getInt("DepartmentId");
        String depName = rs.getString("DepName");

        return new Department(depId,depName);

    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException{

        int sellerId = rs.getInt("Id");
        String name = rs.getString("Name");
        String email = rs.getString("Email");
        Date birthDate = rs.getDate("BirthDate");
        double baseSalary = rs.getDouble("BaseSalary");

        return new Seller(sellerId,name,email,birthDate,baseSalary,dep);

    }




}

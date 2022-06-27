package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

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
        return null;
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

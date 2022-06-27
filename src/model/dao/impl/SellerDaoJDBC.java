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
import java.util.Date;
import java.util.List;

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

                int sellerId = rs.getInt("Id");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                Date birthDate = rs.getDate("BirthDate");
                double baseSalary = rs.getDouble("BaseSalary");

                int depId = rs.getInt("DepartmentId");
                String depName = rs.getString("DepName");

                Department dep = new Department(depId,depName);

                return new Seller(sellerId,name,email,birthDate,baseSalary,dep);

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

}

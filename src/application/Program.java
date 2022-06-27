package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) {


        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST1: seller findById ===");

        Seller seller = sellerDao.findById(3);
        System.out.println(seller.toString());
        System.out.println();

        System.out.println("=== TEST2: List<Seller> findByDepartmentId ===");

        Department dep = new Department(2,"Electronics");
        List<Seller> sellers = sellerDao.findByDepartment(dep);
        sellers.forEach(System.out::println);
        System.out.println();

        System.out.println("=== TEST3: List<Seller> findAll ===");

        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);
        System.out.println();



    }

}

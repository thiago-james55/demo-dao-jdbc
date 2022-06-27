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

        System.out.println("=== TEST4: Seller insert ===");

        Seller sellerInsert = new Seller(null,"Thiago Gonçalves", "tsyhwh55@gmail.com" , new Date(),2500.0, dep);
        sellerDao.insert(sellerInsert);
        System.out.println("Inserted! new id: " + sellerInsert.getId());
        System.out.println(sellerInsert.toString());
        System.out.println();

        System.out.println("=== TEST5: Seller update ===");

        Seller sellerUpdate = new Seller(8,"Thiago Gonçalves Souza", "tsyhwh55@gmail.com" , new Date(),2500.0, dep);
        sellerDao.update(sellerUpdate);
        System.out.println("Updated! id: " + sellerUpdate.getId());
        System.out.println(sellerUpdate.toString());
        System.out.println();

        System.out.println("=== TEST6: Seller delete ===");

        Seller sellerDelete = new Seller(8,"Thiago Gonçalves Souza", "tsyhwh55@gmail.com" , new Date(),2500.0, dep);
        sellerDao.deleteById(sellerDelete.getId());
        System.out.println("Deleted! Seller: " + sellerDelete.toString());
        System.out.println();

    }

}

package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
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


        System.out.println("================================");

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST1: department findById ===");

        Department department = departmentDao.findById(3);
        System.out.println(department.toString());
        System.out.println();

        System.out.println("=== TEST2: List<Department> findAll ===");

        List<Department> allDepartment = departmentDao.findAll();
        allDepartment.forEach(System.out::println);
        System.out.println();

        System.out.println("=== TEST3: department insert ===");

        Department departmentInsert = new Department(null, "Food");
        departmentDao.insert(departmentInsert);
        System.out.println("Inserted! new id: " + departmentInsert.getId());
        System.out.println(departmentInsert.toString());
        System.out.println();

        System.out.println("=== TEST4: department update ===");

        Department departmentUpdate = new Department(5, "Foood");
        departmentDao.update(departmentUpdate);
        System.out.println("Updated! id: " + departmentUpdate.getId());
        System.out.println(departmentUpdate.toString());
        System.out.println();

        System.out.println("=== TEST5: department delete ===");

        Department departmentDelete = new Department(5, "Food");
        departmentDao.deleteById(departmentDelete.getId());
        System.out.println("Deleted! Seller: " + departmentDelete.toString());
    }

}

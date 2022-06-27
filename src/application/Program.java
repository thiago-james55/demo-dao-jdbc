package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.util.Date;

public class Program {

    public static void main(String[] args) {

       SellerDao sellerDao = DaoFactory.createSellerDao();

       System.out.println("=== TEST1: seller findById ===");
       Seller seller = sellerDao.findById(3);

       System.out.println(seller.toString());

    }

}

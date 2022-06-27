package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entitites.Department;
import model.entitites.Seller;

import java.util.Date;

public class Program {

    public static void main(String[] args) {

       SellerDao sellerDao = DaoFactory.createSellerDao();

       Seller seller = sellerDao.findById(3);

        System.out.println(seller.toString());

    }

}

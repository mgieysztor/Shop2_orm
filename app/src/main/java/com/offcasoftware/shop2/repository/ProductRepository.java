package com.offcasoftware.shop2.repository;

import com.offcasoftware.shop2.AndroidApplication;
import com.offcasoftware.shop2.database.Database;
import com.offcasoftware.shop2.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maciej.pachciarek on 2017-02-18.
 */

public class ProductRepository implements ProductRepositoryInterface {

    private static ProductRepository mInstance = new ProductRepository();

    private final Map<Integer, Product> mProducts = new HashMap<>();

    private final Database mDatabase;


    // TODO: 2017-02-21 test
    private ProductRepository() {

        mDatabase = AndroidApplication.getDatabase();

        List<Product> products = new ArrayList<>();


        Product product1 = new Product(1, "dom 1", 1000, "dom1");
        Product product2 = new Product(2, "dom 2", 2000, "dom2");
        Product product3 = new Product(3, "dom 3", 3000, "dom3");

        mProducts.put(1, product1);
        mProducts.put(2, product2);
        mProducts.put(3, product3);


        products.add(product1);
        products.add(product2);
        products.add(product3);

        mDatabase.saveProducts(products);
    }

    public static ProductRepositoryInterface getInstance() {
        return mInstance;
    }

    @Override
    public List<Product> getProducts() {

        return mDatabase.getProducts();
    }

    @Override
    public Product getProduct(final int productId) {
        return mProducts.get(productId);
    }
}

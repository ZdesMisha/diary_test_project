package com.misha.application.service;

import com.misha.application.dao.ProductDao;
import com.misha.application.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by misha on 17.11.16.
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> getAll(int page) {
        return productDao.getAll(page);
    }

    public void saveAll(List<Product> list) {
        productDao.saveAll(list);
    }

}

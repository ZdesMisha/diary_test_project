package com.misha.application.dao;

import com.misha.application.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.misha.application.utils.PaginationUtils.*;

/**
 * Created by misha on 17.11.16.
 */
@Service
public class ProductDao {

    @Autowired
    private EntityManager entityManager;


    @Value("${batch.size}")
    private int batchSize;

    public List<Product> getAll(int pageNumber) {
        int startRecord = getStartRecord(pageNumber);
        return entityManager.createNamedQuery("Product.findAll", Product.class)
                .setFirstResult(startRecord)
                .setMaxResults(startRecord + PAGE_SIZE)
                .getResultList();
    }

    @Transactional
    public void saveAll(List<Product> list) {
        System.out.println("Trying to persist");
        int i = 0;
        for (Product product : list) {
            entityManager.persist(product);
            i++;
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}

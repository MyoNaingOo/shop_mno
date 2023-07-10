package com.mno.shop.repo;

import com.mno.shop.entity.Product;
import com.mno.shop.entity.Product_order;
import com.mno.shop.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Product_order_Repo extends JpaRepository<Product_order,Long> {


    List<Product_order> findAllByUser(User user, Sort sort);
    Optional<Product_order> findByProduct(Product product);

    List<Product_order> findAllByProduct(Product product,Sort sort);

    @Modifying
    @Transactional
    @Query(value = "UPDATE product_order p SET p.accept = ?2 WHERE p.id= ?1",nativeQuery = true)
    void changeAccept(Long id,boolean accept);


}

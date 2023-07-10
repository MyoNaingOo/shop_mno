package com.mno.shop.repo;

import com.mno.shop.entity.Product;
import com.mno.shop.entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByOwner(User owner);

    List<Product> findAllByName(String name);


    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM product ORDER BY id DESC LIMIT ?1,?2",nativeQuery = true)
    List<Product> getAllProducts(int start,int end);

    List<Product> findAllByOwner(User user, Sort sort);

    List<Product> findAllByOwner(User user, Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "UPDATE product p SET p.price_original= ?2,p.price_discount =?3,p.percent_discount=?4,p.created_at=?5,p.delete_at=?6 WHERE p.id= ?1",nativeQuery = true)
    void changPrice(Long id,int price_original,int price_discount,int percent_discount,LocalDate created_at,LocalDate delete_at );

    @Modifying
    @Transactional
    @Query(value = "UPDATE product p SET p.sold_out=?2 WHERE p.id= ?1",nativeQuery = true)
    void ChangeSold_out(Long id,Boolean sold_out);

//
//    private int price_original;
//    private int price_discount;
//    private int percent_discount;
//    private boolean sold_out;
//    private LocalDate created_at;
//    private LocalDate delete_at;

}

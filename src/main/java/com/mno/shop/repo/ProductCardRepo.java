package com.mno.shop.repo;


import com.mno.shop.entity.ProductCard;
import com.mno.shop.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCardRepo extends JpaRepository<ProductCard,Long> {

    List<ProductCard> findByUser(User user, Sort sort);
}

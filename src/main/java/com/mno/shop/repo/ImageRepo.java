package com.mno.shop.repo;

import com.mno.shop.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String name);


}

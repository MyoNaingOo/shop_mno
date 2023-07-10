package com.mno.shop.otb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp,Long> {

    Optional<Otp> findByOtp(int otp);

}

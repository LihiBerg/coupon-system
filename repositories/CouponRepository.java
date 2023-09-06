package com.lihicouponsystem.repositories;

import com.lihicouponsystem.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByUuid(UUID uuid);

    @Modifying
    @Query("delete from Coupon where uuid = :id")
    void deleteByUuid(UUID id);
}

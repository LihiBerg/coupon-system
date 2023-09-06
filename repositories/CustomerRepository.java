package com.lihicouponsystem.repositories;

import com.lihicouponsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailAndPassword(String email, String password);

    Optional<Customer> findByUuid(UUID uuid);

}

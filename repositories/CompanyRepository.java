package com.lihicouponsystem.repositories;

import com.lihicouponsystem.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmailAndPassword(String email, String password);

    Optional<Company> findByUuid(UUID uuid);
}

package com.desafio.audsat.repository;

import com.desafio.audsat.domain.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}

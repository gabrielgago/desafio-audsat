package com.desafio.audsat.repository;

import com.desafio.audsat.domain.CarDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDriverRepository extends JpaRepository<CarDriver, Long> {
}

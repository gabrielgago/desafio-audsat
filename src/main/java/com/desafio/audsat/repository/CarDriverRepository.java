package com.desafio.audsat.repository;

import com.desafio.audsat.domain.CarDriver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDriverRepository extends JpaRepository<CarDriver, Long> {
}

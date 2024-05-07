package com.desafio.audsat.repository;

import com.desafio.audsat.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}

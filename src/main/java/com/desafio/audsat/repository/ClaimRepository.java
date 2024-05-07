package com.desafio.audsat.repository;

import com.desafio.audsat.domain.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}

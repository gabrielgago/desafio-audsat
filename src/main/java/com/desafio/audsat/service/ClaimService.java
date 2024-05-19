package com.desafio.audsat.service;

import com.desafio.audsat.domain.Claim;
import com.desafio.audsat.exception.AudsatException;
import com.desafio.audsat.repository.ClaimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

    /**
     * Search the claim by id
     *
     * @param claimId Claim ID to be fetched
     * @return Saved Claim
     */
    @Transactional(readOnly = true)
    public Claim findById(Long claimId) {
        log.info("Searching claim with id: {}", claimId);
        return claimRepository.findById(claimId).orElseThrow(() -> new AudsatException(MessageFormat.format("Claim id {0} not found", claimId)));
    }

    /**
     * Added a new claim inside database for future queries
     *
     * @param claim Claim by create
     * @return Saved Claim
     */
    @Transactional
    public Claim save(Claim claim) {
        log.info("Creating claim: {}", claim);
        return claimRepository.save(claim);
    }

    /**
     * Find all claims saved inside database
     *
     * @return List by saved claims
     */
    @Transactional(readOnly = true)
    public List<Claim> findAllClaims() {
        return claimRepository.findAll();
    }

    /**
     * Delete claim by id
     *
     * @param id Claim id used to delete resource
     */
    @Transactional
    public void deleteClaim(Long id) {
        log.info("Deleting claim with id: {}", id);
        claimRepository.deleteById(id);
    }

    /**
     * Update saved claim
     *
     * @param id    Claim id to update
     * @param claim Claim with changes
     * @return Updated Claim
     */
    @Transactional
    public Claim updateClaim(Long id,
                             Claim claim) {
        claim.setId(id);
        return claimRepository.save(claim);
    }
}

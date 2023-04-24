package com.poc.comm.repository;

import com.poc.comm.models.Quota;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuotaRepository extends MongoRepository<Quota, Integer> {
    Optional<Quota> findByTenantIdAndDealerIdAndModuleIdAndType(int tid, int did, int mid, String type);
}

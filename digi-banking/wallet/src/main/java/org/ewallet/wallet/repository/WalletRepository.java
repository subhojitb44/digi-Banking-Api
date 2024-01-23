package org.ewallet.wallet.repository;

import org.ewallet.wallet.entity.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByUuid(String uuid);
    Optional<Wallet> findByOwnerReference(String ownerReference);
}
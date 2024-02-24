package org.authentication.repositories;

import org.authentication.entities.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by subho
 * Date: 1/29/2024
 */
public interface SequenceRepository extends MongoRepository<Sequence, String> {
    Sequence findByName(String name); // Find a sequence by its username
}

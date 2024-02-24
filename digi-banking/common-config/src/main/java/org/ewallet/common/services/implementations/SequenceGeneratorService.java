package org.ewallet.common.services.implementations;

import lombok.extern.slf4j.Slf4j;
import org.ewallet.common.entities.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SequenceGeneratorService {
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) throws DataAccessException {
        try {
            Query query = new Query(Criteria.where("_id").is(seqName));
            Update update = new Update().inc("seq", 1);
            FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
            Sequence sequence = mongoOperations.findAndModify(query, update, options, Sequence.class);
            return sequence != null ? sequence.getSeq() : 1;
        } catch (DataAccessException ex) {
            // Handle data access errors
           log.info("Exceptions Occurred : {}",ex.getCause());
            throw ex;
        }
    }
}

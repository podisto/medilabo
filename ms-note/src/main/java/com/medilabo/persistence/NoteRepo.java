package com.medilabo.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends MongoRepository<Note, String> {
}

package com.fspoitmo.fspoitmo.Entities.Repositories;


import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject, String> {

    List<Subject> findByName(String name);

    Optional<Subject> findById(String id);

    Subject findBySubjectUniversityId(Integer id);
}

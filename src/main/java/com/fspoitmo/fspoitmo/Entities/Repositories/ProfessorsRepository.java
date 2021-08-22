package com.fspoitmo.fspoitmo.Entities.Repositories;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfessorsRepository extends CrudRepository<Professor, String>,
        JpaSpecificationExecutor<Professor> {

    Professor findByProfessorUniversityId(Integer id);

    Optional<Professor> findById(String id);

    List<Professor> getAllBy();

    Page<Professor> findAll(Specification<Professor> spc, Pageable pageable);

    Set<Professor> findByName(String name);

    List<Professor> findAll(Specification<Professor> spc);
}

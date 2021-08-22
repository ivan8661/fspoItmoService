package com.fspoitmo.fspoitmo.Entities.Repositories;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PupilGroupRepository extends CrudRepository<PupilGroup, String> {


    List<PupilGroup> getAllBy();

    PupilGroup findByUniversityGroupId(Integer id);

    Optional<PupilGroup> getById(String id);

    PupilGroup findPupilGroupByName(String name);

    Page<PupilGroup> findAll(Specification<PupilGroup> spc, Pageable pageable);

    List<PupilGroup> findAll(Specification<PupilGroup> spc);



}

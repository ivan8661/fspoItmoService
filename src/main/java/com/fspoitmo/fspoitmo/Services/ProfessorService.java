package com.fspoitmo.fspoitmo.Services;


import GetGraphQL.QueryParametersBuilder;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Professor;
import com.fspoitmo.fspoitmo.Entities.Repositories.ProfessorsRepository;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProfessorService {


    private final ProfessorsRepository professorsRepository;

    @Autowired
    public ProfessorService(ProfessorsRepository professorsRepository) {
        this.professorsRepository = professorsRepository;
    }

    public List<Professor> getProfessors(Map<String, String> params) throws NoSuchFieldException {

        QueryParametersBuilder<Professor> queryBuilder = new QueryParametersBuilder<>(params, Professor.class);
        Specification<Professor> spc = queryBuilder.getSpecification(null);
        Pageable pageable = queryBuilder.getPage();
        return professorsRepository.findAll(spc, pageable).getContent();
    }

    public Professor getProfessor(String id) throws UserException {
      Optional<Professor> professor = professorsRepository.findById(id);
      if(professor.isPresent()) {
          return professor.get();
      } else {
          throw new UserException(404, "not_found", "professor doesn't exist", "");
      }
    }
}

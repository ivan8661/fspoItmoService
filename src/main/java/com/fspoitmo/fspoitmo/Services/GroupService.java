package com.fspoitmo.fspoitmo.Services;


import GetGraphQL.QueryParametersBuilder;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import com.fspoitmo.fspoitmo.Entities.Repositories.PupilGroupRepository;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private PupilGroupRepository pupilGroupRepository;


    public List<PupilGroup> getGroups(Map<String, String> params) throws NoSuchFieldException {

        QueryParametersBuilder<PupilGroup> queryBuilder = new QueryParametersBuilder<>(params, PupilGroup.class);
        Specification<PupilGroup> spc = queryBuilder.getSpecification(null);
        Pageable pageable = queryBuilder.getPage();
        return pupilGroupRepository.findAll(spc, pageable).getContent();
    }

    public PupilGroup getGroup(String id) throws UserException {
        Optional<PupilGroup> group = pupilGroupRepository.findById(id);
        if(group.isPresent()) {
            return group.get();
        } else {
            throw new UserException(404, "not_found", "group doesn't exist", "");
        }
    }


}

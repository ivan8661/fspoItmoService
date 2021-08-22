package com.fspoitmo.fspoitmo.Entities.Repositories;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Lesson;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Professor;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, String> {


    List<Lesson> getAllBy();


    List<Lesson> getAllByGroups(PupilGroup pupilGroup);

    List<Lesson> getAllByProfessors(Professor professor);

}

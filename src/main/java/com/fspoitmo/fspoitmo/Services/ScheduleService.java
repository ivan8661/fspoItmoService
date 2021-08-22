package com.fspoitmo.fspoitmo.Services;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Lesson;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Professor;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Subject;
import com.fspoitmo.fspoitmo.Entities.Repositories.LessonRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.ProfessorsRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.PupilGroupRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.SubjectRepository;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScheduleService {

    private final LessonRepository lessonRepository;

    private final PupilGroupRepository pupilGroupRepository;

    private final ProfessorsRepository professorsRepository;

    private final SubjectRepository subjectRepository;

    @Autowired
    public ScheduleService(LessonRepository lessonRepository, PupilGroupRepository pupilGroupRepository,
                           ProfessorsRepository professorsRepository, SubjectRepository subjectRepository) {
        this.lessonRepository = lessonRepository;
        this.pupilGroupRepository = pupilGroupRepository;
        this.professorsRepository = professorsRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Lesson> getLessons(String scheduleUserId) throws UserException {

        Optional<PupilGroup> pupilGroup = pupilGroupRepository.findById(scheduleUserId);
        Optional<Professor> professor = professorsRepository.findById(scheduleUserId);

        if(pupilGroup.isPresent()){
            return lessonRepository.getAllByGroups(pupilGroup.get());
        }
        if(professor.isPresent()){
            return lessonRepository.getAllByProfessors(professor.get());
        }
        throw new UserException(404, "not_found", "ScheduleUser Doesn't exist!", "");
    }

    public Lesson getLesson(String lessonId) throws UserException {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if(lesson.isPresent()) {
            return lesson.get();
        } else {
            throw new UserException(404, "not_found", "Lesson doesn't exist!", " ");
        }
    }

    public Subject getSubject(String subjectId) throws UserException {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if(subject.isPresent()) {
            return subject.get();
        } else {
            throw new UserException(404, "not_found", "Subject doesn't exist!", " ");
        }
    }

    public List<Subject> getSubjects(Set<String> subjects) {
        List<Subject> subjectList = new ArrayList<>();
        for(String subjectId : subjects) {
            Optional<Subject> subjectOpt = subjectRepository.findById(subjectId);
            subjectOpt.ifPresent(subjectList::add);
        }
        return subjectList;
    }
}

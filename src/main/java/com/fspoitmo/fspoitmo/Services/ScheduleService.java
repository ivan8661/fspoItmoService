package com.fspoitmo.fspoitmo.Services;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.*;
import com.fspoitmo.fspoitmo.Entities.Repositories.LessonRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.ProfessorsRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.PupilGroupRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.SubjectRepository;
import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Exceptions.UserExceptionType;
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

    public ListAnswer<Lesson> getLessons(String scheduleUserId) throws UserException {

        Optional<PupilGroup> pupilGroup = pupilGroupRepository.findById(scheduleUserId);
        Optional<Professor> professor = professorsRepository.findById(scheduleUserId);

        if(pupilGroup.isPresent()){
            List<Lesson> lessons = lessonRepository.getAllByGroups(pupilGroup.get());
            return new ListAnswer(lessons, lessons.size());
        }
        if(professor.isPresent()){
            List<Lesson> lessons = lessonRepository.getAllByProfessors(professor.get());
            return new ListAnswer(lessons, lessons.size());
        }
        throw new UserException(UserExceptionType.OBJECT_NOT_FOUND);
    }

    public Lesson getLesson(String lessonId) throws UserException {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if(lesson.isPresent()) {
            return lesson.get();
        } else {
            throw new UserException(UserExceptionType.OBJECT_NOT_FOUND);
        }
    }

    public Subject getSubject(String subjectId) throws UserException {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if(subject.isPresent()) {
            return subject.get();
        } else {
            throw new UserException(UserExceptionType.OBJECT_NOT_FOUND);
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

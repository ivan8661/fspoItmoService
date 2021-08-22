package com.fspoitmo.fspoitmo.Services;


import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Lesson;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Professor;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.PupilGroup;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Subject;
import com.fspoitmo.fspoitmo.Entities.Repositories.LessonRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.ProfessorsRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.PupilGroupRepository;
import com.fspoitmo.fspoitmo.Entities.Repositories.SubjectRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.fspoitmo.fspoitmo.Services.FSPOITMOUtils.getFSPOEven;

@Service
public class ImportService {

    private final PupilGroupRepository pupilGroupRepository;

    private final ProfessorsRepository professorsRepository;

    private final LessonRepository lessonRepository;

    private final SubjectRepository subjectRepository;
    @Autowired
    public ImportService(PupilGroupRepository pupilGroupRepository,
                         ProfessorsRepository professorsRepository,
                         LessonRepository lessonRepository,
                         SubjectRepository subjectRepository) {
        this.pupilGroupRepository = pupilGroupRepository;
        this.professorsRepository = professorsRepository;
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
    }

    public void updateFSPOGroups() throws JSONException {
        String criterion = "{\"app_key\":\"b13f556af4ed3da2f8d484a617fee76d78be1166\"}";
        String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/groups?jsondata={criterion}", String.class, criterion);
        JSONObject list = new JSONObject(ans);
        for(int i = 0; i < list.getInt("count_c"); ++i){
            JSONArray courses = list.getJSONArray("courses");
            JSONArray groups = courses.getJSONObject(i).getJSONArray("groups");
            for(int j = 0; j < courses.getJSONObject(i).getInt("count_g"); ++j) {
                JSONObject group = groups.getJSONObject(j);
                pupilGroupRepository.save(new PupilGroup(DigestUtils.sha256Hex(group.getString("group_id")+"fspo_itmo_group"),
                        group.getString("name"), group.getString("group_id")));
            }
        }
    }

    public void updateFSPOTeachers() throws JSONException {
        String criterion = "{\"app_key\":\"b13f556af4ed3da2f8d484a617fee76d78be1166\"}";
        String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/teachers?jsondata={criterion}", String.class, criterion);
        JSONObject ansJSON = new JSONObject(ans);
        for(int i = 0; i < ansJSON.getInt("count_t"); ++i) {
            JSONObject teacher = ansJSON.getJSONArray("teachers").getJSONObject(i);
            professorsRepository.save(new Professor(DigestUtils.sha256Hex(teacher.getString("user_id")+"fspo_itmo_professors"),
                    teacher.getString("lastname") + " " + teacher.getString("firstname") + " " + teacher.get("middlename"),
                    teacher.getString("user_id")));
        }
    }

    public void updateFSPOSubjects() throws JSONException {
        List<PupilGroup> groups = pupilGroupRepository.getAllBy();
        for(PupilGroup group : groups) {
            String criterion = "{\"type\":\"group\", \"id\":\"" + group.getUniversityGroupId() + "\", \"week\":\"all\", \"app_key\":\"b13f556af4ed3da2f8d484a617fee76d78be1166\"}";
            String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/schedule?jsondata={criterion}", String.class, criterion);
            JSONObject answer = new JSONObject(ans);
            JSONArray weekDays = answer.getJSONArray("weekdays");   //массив weekdays
            for (int i = 0; i < answer.getInt("count_wd"); ++i) {
                JSONObject weekDay = weekDays.getJSONObject(i); //получение дня недели (к примеру понедельник)
                for (int j = 0; j < weekDay.getInt("count_p"); ++j) {
                    JSONObject period = weekDay.getJSONArray("periods").getJSONObject(j); //получение пары
                    for (int k = 0; k < period.getInt("count_s"); ++k) { //пробежка по 1 и 2 подгруппе
                        JSONObject lesson = period.getJSONArray("schedule").getJSONObject(k);
                        subjectRepository.save(new Subject(DigestUtils.sha256Hex(lesson.getString("name")+"fspo_itmo_subjects"), lesson.getString("name"), "228"));
                    }
                }
            }
        }
    }

    public void updateFSPOLessons() throws JSONException {
        List<PupilGroup> groups = pupilGroupRepository.getAllBy();
        groups.removeIf(x -> x.getName().contains("/"));
        System.out.println("список:" + groups);
        for(PupilGroup group : groups) {
            System.out.println("обходим группу: " + group.getName());
            String criterion = "{\"type\":\"group\", \"id\":\"" + group.getUniversityGroupId() + "\", \"week\":\"all\", \"app_key\":\"b13f556af4ed3da2f8d484a617fee76d78be1166\"}";
            String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/schedule?jsondata={criterion}", String.class, criterion);
            JSONObject answer = new JSONObject(ans);
            JSONArray weekDays = answer.getJSONArray("weekdays");   //массив weekdays
            for (int i = 0; i < answer.getInt("count_wd"); ++i) {
                JSONObject weekDay = weekDays.getJSONObject(i); //получение дня недели (к примеру понедельник)
                for (int j = 0; j < weekDay.getInt("count_p"); ++j) {
                    JSONObject period = weekDay.getJSONArray("periods").getJSONObject(j); //получение пары
                    for (int k = 0; k < period.getInt("count_s"); ++k) { //пробежка по 1 и 2 подгруппе
                        JSONObject lesson = period.getJSONArray("schedule").getJSONObject(k);
                        Integer numberLesson = (period.getInt("period") + 1);
                        String sNumberLesson = numberLesson.toString();
                        if(lesson.getInt("group_part") == 0){
                            if(getFSPOEven(lesson.optString("even"), lesson.optString("odd")) == null){
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "even", null);
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "odd", null);
                            } else {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  getFSPOEven(lesson.optString("even"), lesson.optString("odd")), null);
                            }
                        } else if(lesson.getInt("group_part") == 1){
                            if(getFSPOEven(lesson.optString("even"), lesson.optString("odd")) == null) {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "odd", "1 подгруппа");
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "even", "1 подгруппа");
                            } else {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  getFSPOEven(lesson.optString("even"), lesson.optString("odd")), "1 подгруппа");
                            }

                        } else if(lesson.getInt("group_part") == 2) {
                            if(getFSPOEven(lesson.optString("even"), lesson.optString("odd")) == null) {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "even", "2 подгруппа");
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "odd", "2 подгруппа");
                            } else {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  getFSPOEven(lesson.optString("even"), lesson.optString("odd")), "2 подгруппа");
                            }
                        }
                    }
                }
            }
        }
    }


    private void addGroupSave(PupilGroup pupilGroup, JSONObject weekDay, JSONObject period, JSONObject lesson, String sNumberLesson, String week, String type) throws JSONException {
        String nsp = lesson.optString("lastname") + " " + lesson.optString("firstname") + " " + lesson.optString("middlename");
        Lesson lesson1 = lessonRepository.save(new Lesson(DigestUtils.sha256Hex(lesson.getString("name") + nsp + lesson.getInt("group_part") + weekDay.getString("weekday") + period.getInt("period") + "ФСПО ИТМО" + week),
                period.optString("period_start"),
                period.optString("period_end"),
                Integer.parseInt(sNumberLesson),
                FSPOITMOUtils.getFSPOday(weekDay.optString("weekday")),
                lesson.optString("place"),
                "practice",
                new Subject(DigestUtils.sha256Hex(lesson.optString("name") + "ФСПО_ПРЕДМЕТЫ"), lesson.optString("name"), "228"),
                professorsRepository.findByName(nsp),
                Collections.singleton(pupilGroupRepository.findPupilGroupByName(pupilGroup.getName())),
                week));

    }




}
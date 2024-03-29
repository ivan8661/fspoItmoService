package com.fspoitmo.fspoitmo.Services;


import com.fspoitmo.fspoitmo.Constants;
import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.*;
import com.fspoitmo.fspoitmo.Entities.Repositories.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.fspoitmo.fspoitmo.Services.FSPOITMOUtils.getFSPOEven;

@Service
public class ImportService {

    private final PupilGroupRepository pupilGroupRepository;

    private final ProfessorsRepository professorsRepository;

    private final LessonRepository lessonRepository;

    private final SubjectRepository subjectRepository;

    private final ScheduleUpdateRepository scheduleUpdateRepository;


    @Autowired
    public ImportService(PupilGroupRepository pupilGroupRepository,
                         ProfessorsRepository professorsRepository,
                         LessonRepository lessonRepository,
                         SubjectRepository subjectRepository,
                         ScheduleUpdateRepository scheduleUpdateRepository ) {
        this.pupilGroupRepository = pupilGroupRepository;
        this.professorsRepository = professorsRepository;
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
        this.scheduleUpdateRepository = scheduleUpdateRepository;
    }

    @Value("${ifspo.import.appkey}")
    private String appKey;

    public void updateFSPOGroups() throws JSONException {
        String criterion = "{\"app_key\":\""+appKey+"\"}";
        String groupId = "";
        String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/groups?jsondata={criterion}", String.class, criterion);
        JSONObject list = new JSONObject(ans);
        for(int i = 0; i < list.getInt("count_c"); ++i){
            JSONArray courses = list.getJSONArray("courses");
            JSONArray groups = courses.getJSONObject(i).getJSONArray("groups");
            for(int j = 0; j < courses.getJSONObject(i).getInt("count_g"); ++j) {
                JSONObject group = groups.getJSONObject(j);
                groupId = group.getString("group_id");
                pupilGroupRepository.save(new PupilGroup(DigestUtils.sha256Hex(groupId+"fspo_itmo_group"),
                        group.getString("name"), group.getString("group_id")));
            }
        }

        updateCurrentWeek(groupId);
    }

    public void updateCurrentWeek(String groupId) throws JSONException  {
        String criterion = "{\"type\":\"group\", \"id\":\"" + groupId + "\", \"week\":\"now\", \"app_key\":\""+appKey+"\"}";
        String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/schedule?jsondata={criterion}", String.class, criterion);
        JSONObject ansJSON = new JSONObject(ans);

        ScheduleUpdate scheduleUpdate = new ScheduleUpdate();
        String week = ansJSON.getString("week");

        scheduleUpdate.setName(Constants.schedSyncDateConst);
        scheduleUpdate.setWeek(week);
        scheduleUpdate.setSyncTime(System.currentTimeMillis()/1000);

        scheduleUpdateRepository.save(scheduleUpdate);
    }

    public void updateFSPOTeachers() throws JSONException {
        String criterion = "{\"app_key\":\""+appKey+"\"}";
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
            String criterion = "{\"type\":\"group\", \"id\":\"" + group.getUniversityGroupId() + "\", \"week\":\"all\", \"app_key\":\""+appKey+"\"}";
            String ans = new RestTemplate().getForObject("https://ifspo.ifmo.ru/api/schedule?jsondata={criterion}", String.class, criterion);
            JSONObject answer = new JSONObject(ans);
            JSONArray weekDays = answer.getJSONArray("weekdays");   //массив weekdays
            for (int i = 0; i < answer.getInt("count_wd"); ++i) {
                JSONObject weekDay = weekDays.getJSONObject(i); //получение дня недели (к примеру понедельник)
                for (int j = 0; j < weekDay.getInt("count_p"); ++j) {
                    JSONObject period = weekDay.getJSONArray("periods").getJSONObject(j); //получение пары
                    for (int k = 0; k < period.getInt("count_s"); ++k) { //пробежка по 1 и 2 подгруппе
                        JSONObject lesson = period.getJSONArray("schedule").getJSONObject(k);

                        subjectRepository.save(new Subject(DigestUtils.sha256Hex(lesson.getString("name")+"fspo_itmo_subjects"), lesson.getString("name")));
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
            String criterion = "{\"type\":\"group\", \"id\":\"" + group.getUniversityGroupId() + "\", \"week\":\"all\", \"app_key\":\""+appKey+"\"}";
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
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "odd", "1 подгруппа " + group.getName());
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "even", "1 подгруппа " + group.getName());
                            } else {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  getFSPOEven(lesson.optString("even"), lesson.optString("odd")), "1 подгруппа " + group.getName());
                            }

                        } else if(lesson.getInt("group_part") == 2) {
                            if(getFSPOEven(lesson.optString("even"), lesson.optString("odd")) == null) {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "even", "2 подгруппа " + group.getName());
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  "odd", "2 подгруппа " + group.getName());
                            } else {
                                addGroupSave(group, weekDay, period, lesson, sNumberLesson,  getFSPOEven(lesson.optString("even"), lesson.optString("odd")), "2 подгруппа " + group.getName());
                            }
                        }
                    }
                }
            }
        }
    }


    private void addGroupSave(PupilGroup pupilGroup, JSONObject weekDay, JSONObject period, JSONObject lessonJson, String sNumberLesson, String week, String type) throws JSONException {
        String nsp = lessonJson.optString("lastname") + " " + lessonJson.optString("firstname") + " " + lessonJson.optString("middlename");
        String id = DigestUtils.sha256Hex(nsp + weekDay.getString("weekday") + period.getInt("period") + "ФСПО ИТМО" + week);

        Lesson lesson = lessonRepository.findById(id).orElse(new Lesson());

        lesson.setId(id);
        lesson.setStartTime(period.optString("period_start"));
        lesson.setEndTime(period.optString("period_end"));
        lesson.setLessonNum(Integer.parseInt(sNumberLesson));
        lesson.setDay(FSPOITMOUtils.getFSPOday(weekDay.optString("weekday")));
        if( lesson.getRooms() == null ){
            lesson.setRooms(lessonJson.optString("place"));
        }
        else if (lesson.getRooms().isEmpty()) {
            lesson.setRooms(lessonJson.optString("place"));
        }
        else if (!lesson.getRooms().contains(lessonJson.optString("place"))){
            lesson.setRooms(lesson.getRooms() + ", " + lessonJson.optString("place"));
        }
        lesson.setSubject(new Subject(DigestUtils.sha256Hex(lessonJson.getString("name")+"fspo_itmo_subjects"), lessonJson.optString("name")));

        if (lessonJson.getString("name") == null) {
            lesson.setType("practice");
        } else if (lessonJson.getString("name").toLowerCase().contains("прак")) {
            lesson.setType("practice");
        } else if (lessonJson.getString("name").toLowerCase().contains("курс")) {
            lesson.setType("course");
        } else if (lessonJson.getString("name").toLowerCase().contains("курс")) {
            lesson.setType("practice");
        }

        Set<Professor> professors = lesson.getProfessors();
        if (professors == null ) professors = new HashSet<>();
        for( Professor p : professorsRepository.findByName(nsp) ) {
            professors.add(p);
        }
        lesson.setProfessors(professors);

        Set<PupilGroup> groups = lesson.getGroups();
        if (groups == null ) groups = new HashSet<>();
        PupilGroup group = pupilGroupRepository.findPupilGroupByName(pupilGroup.getName());
        if(group != null) {
            groups.add(group);
        }
        lesson.setGroups(groups);
        lesson.setWeek(week);

        lessonRepository.save(lesson);
    }
}

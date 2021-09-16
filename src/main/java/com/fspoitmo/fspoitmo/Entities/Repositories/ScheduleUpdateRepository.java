package com.fspoitmo.fspoitmo.Entities.Repositories;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.ScheduleUpdate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleUpdateRepository extends CrudRepository<ScheduleUpdate, String> {


    ScheduleUpdate findByName(String name);

}
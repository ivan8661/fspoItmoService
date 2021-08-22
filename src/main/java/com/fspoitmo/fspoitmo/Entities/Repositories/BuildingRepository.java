package com.fspoitmo.fspoitmo.Entities.Repositories;

import com.fspoitmo.fspoitmo.Entities.DatabaseEntities.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Integer> {


}

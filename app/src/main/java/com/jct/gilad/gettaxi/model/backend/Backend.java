package com.jct.gilad.gettaxi.model.backend;

import com.jct.gilad.gettaxi.model.datasource.FireBase_DbManager;
import com.jct.gilad.gettaxi.model.entities.Drive;

public interface Backend {
    void addDrive(Drive drive, FireBase_DbManager.Action<Long> action);
}

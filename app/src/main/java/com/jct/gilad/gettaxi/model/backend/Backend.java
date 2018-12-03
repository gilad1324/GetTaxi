package com.jct.gilad.gettaxi.model.backend;

import android.content.ContentValues;

import com.jct.gilad.gettaxi.model.entities.Drive;

import java.util.List;

public interface Backend {
    long addDrive(Drive drive);
    boolean removeDrive(long id);
    boolean updateDrive(long id, ContentValues values);
    List<Drive> getDrives();
}

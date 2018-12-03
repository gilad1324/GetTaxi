package com.jct.gilad.gettaxi.model.datasource;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jct.gilad.gettaxi.model.backend.Backend;
import com.jct.gilad.gettaxi.model.entities.Drive;

import java.util.ArrayList;
import java.util.List;

public class FireBase_DbManager implements Backend {
    @Override
    public long addDrive(Drive drive) {
        return 0;
    }

    @Override
    public boolean removeDrive(long id) {
        return false;
    }

    @Override
    public boolean updateDrive(long id, ContentValues values) {
        return false;
    }

    @Override
    public List<Drive> getDrives() {
        return null;
    }

    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }
    static DatabaseReference DriveRef;
    static List<Drive> DriveList;

    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DriveRef = database.getReference("drives");
        DriveList = new ArrayList<>();
    }
    public static void addDrive(final Drive drive, final Action<Long> action) {
        if (true) {//bdika schrih lasot


            //leoozif bdika im action icole
            // add Drive
            addDriveToFirebase(drive, action);
        }
    }
    private static void addDriveToFirebase(final Drive drive, final Action<Long> action) {
        String key = Long.toString(drive.getClientPhoneNumber());
        DriveRef.child(key).setValue(drive).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(drive.getClientPhoneNumber());
                action.onProgress("upload drive data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload drive data", 100);

            }
        });
    }



}

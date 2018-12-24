package com.jct.gilad.gettaxi.model.backend;

import com.jct.gilad.gettaxi.model.datasource.FireBase_DbManager;

public final class BackendFactorySingelton {
    private static Backend instance = null;
    public static Backend  getBackend()
    {
        if(instance == null)
        {
            instance = new FireBase_DbManager();
        }
        return instance;
    }
}

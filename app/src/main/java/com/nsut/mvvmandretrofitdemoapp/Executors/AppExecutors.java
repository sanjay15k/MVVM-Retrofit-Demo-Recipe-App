package com.nsut.mvvmandretrofitdemoapp.Executors;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class AppExecutors {

    private static AppExecutors instance;
    private ScheduledThreadPoolExecutor poolExecutor;

    public static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }

    private AppExecutors(){
        poolExecutor = new ScheduledThreadPoolExecutor(3);
    }

    public ScheduledThreadPoolExecutor getExecutor(){
        return poolExecutor;
    }

}

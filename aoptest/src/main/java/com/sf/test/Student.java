package com.sf.test;

import android.util.Log;

import com.sf.test.aop.AopTime;

/**
 * Created by G8876 on 2017/9/1.
 */

public class Student {
    private final String TAG=Student.class.getSimpleName();
    private int mId;
    private String  mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = mName;
    }

    @AopTime
    public void printInfo(String mode, int param2, String param3) {
        Log.e(TAG, " mode: " + mode);
//        throw new NullPointerException("this is null point test");
    }

    @AopTime
    public void printName(){
        Log.e(TAG,"name: "+mName);
    }
}

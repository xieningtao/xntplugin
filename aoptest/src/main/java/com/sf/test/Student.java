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

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @AopTime
    public void printInfo(String mode){
        Log.e(TAG,"name: "+mName+" id: "+mId+" mode: "+mode);
    }

    @AopTime
    public void printName(){
        Log.e(TAG,"name: "+mName);
    }
}

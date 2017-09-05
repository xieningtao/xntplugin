package com.sf.test.aspectjx;

import android.util.Log;

/**
 * Created by G8876 on 2017/9/5.
 */

public class AspectJxBean {
    private final String TAG=getClass().getSimpleName();
    private String name;
    private int id;
    private boolean ok;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void printBeanInfo(String prefix){
        Log.d(TAG,"name: "+name+" prefix: "+prefix);
    }
}

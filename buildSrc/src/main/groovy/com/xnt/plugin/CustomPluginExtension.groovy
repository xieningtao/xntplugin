package com.xnt.plugin;

/**
 * Created by NetEase on 2017/6/6 0006.
 */

public class CustomPluginExtension {

    private String customName;
    private String id;

    private Student student;

    Student getStudent() {
        return student
    }

    void setStudent(Student student) {
        this.student = student
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomName() {
        return customName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

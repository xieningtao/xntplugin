package com.xnt.plugin.aop

import org.gradle.api.Plugin
import org.gradle.api.Project

public class AopPlugin implements Plugin<Project>{
    @Override
    public void apply(Project project) {
       project.android.registerTransform(new MyTransform(project));
        println("==================================")

    }
}

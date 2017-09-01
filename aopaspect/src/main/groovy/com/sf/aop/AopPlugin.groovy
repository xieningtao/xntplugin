package com.sf.aop;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public class AopPlugin implements Plugin<Project>{
    @Override
    public void apply(Project project) {
        Logger log=project.getLogger();

        log.debug("start to waven class");
        System.out.println("==============================")
    }
}
